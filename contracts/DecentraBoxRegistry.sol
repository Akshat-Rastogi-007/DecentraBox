// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

contract DecentraBoxRegistry {

    // ── Structs ───────────────────────────────────────

    struct AccessGrant {
        address grantedTo;
        uint256 grantedAt;
        uint256 expiresAt;    // 0 = never expires
        bool active;
    }

    // ── Events ────────────────────────────────────────

    event FileEvent(
        string indexed cid,
        address indexed actor,
        string action,
        uint256 timestamp
    );

    event AccessGranted(
        string indexed cid,
        address indexed owner,
        address indexed grantedTo,
        uint256 expiresAt
    );

    event AccessRevoked(
        string indexed cid,
        address indexed owner,
        address indexed revokedFrom
    );

    // ── State Variables ───────────────────────────────

    // CID → owner wallet
    mapping(string => address) private fileOwners;

    // CID → list of access grants
    mapping(string => AccessGrant[]) private accessGrants;

    // CID → total access count
    mapping(string => uint256) private accessCount;

    // CID → exists flag
    mapping(string => bool) private fileExists;

    // ── Modifiers ─────────────────────────────────────

    modifier onlyFileOwner(string memory cid) {
        require(
            fileOwners[cid] == msg.sender,
            "DecentraBox: caller is not the file owner"
        );
        _;
    }

    modifier fileRegistered(string memory cid) {
        require(
            fileExists[cid],
            "DecentraBox: file not registered"
        );
        _;
    }

    // ── Write Functions ───────────────────────────────

    /**
     * Register file ownership on blockchain.
     * Called by backend after IPFS upload.
     * Owner is the USER's wallet — not msg.sender.
     *
     * @param cid   IPFS content identifier
     * @param owner user's wallet address (real owner)
     */
    function registerFile(
        string memory cid,
        address owner          // ← FIX: user wallet
    ) external {
        require(
            !fileExists[cid],
            "DecentraBox: file already registered"
        );
        require(
            bytes(cid).length > 0,
            "DecentraBox: CID cannot be empty"
        );
        require(
            owner != address(0),
            "DecentraBox: invalid owner address"
        );

        fileOwners[cid] = owner;   // ← store USER wallet
        fileExists[cid] = true;

        emit FileEvent(
            cid,
            owner,             // ← emit USER wallet
            "UPLOADED",
            block.timestamp
        );
    }

    /**
     * Grant access to a wallet address.
     * Only file owner can call this.
     * expiryDays = 0 means never expires.
     */
    function grantAccess(
        string memory cid,
        address to,
        uint256 expiryDays
    )
        external
        fileRegistered(cid)
    {
        require(
            fileOwners[cid] == to ||
            msg.sender == fileOwners[cid],
            "DecentraBox: caller is not the file owner"
        );
        require(
            fileOwners[cid] == msg.sender,
            "DecentraBox: caller is not the file owner"
        );
        require(
            to != address(0),
            "DecentraBox: invalid wallet address"
        );
        require(
            to != fileOwners[cid],
            "DecentraBox: owner already has access"
        );

        uint256 expiresAt = expiryDays > 0
            ? block.timestamp + (expiryDays * 1 days)
            : 0;

        accessGrants[cid].push(AccessGrant({
            grantedTo: to,
            grantedAt: block.timestamp,
            expiresAt: expiresAt,
            active: true
        }));

        emit AccessGranted(
            cid,
            msg.sender,
            to,
            expiresAt
        );

        emit FileEvent(
            cid,
            msg.sender,
            "ACCESS_GRANTED",
            block.timestamp
        );
    }

    /**
     * Revoke access from a wallet address.
     * Only file owner can call this.
     */
    function revokeAccess(
        string memory cid,
        address from
    )
        external
        fileRegistered(cid)
    {
        require(
            fileOwners[cid] == msg.sender,
            "DecentraBox: caller is not the file owner"
        );
        require(
            from != address(0),
            "DecentraBox: invalid wallet address"
        );

        bool found = false;

        for (uint256 i = 0;
             i < accessGrants[cid].length;
             i++) {
            if (accessGrants[cid][i].grantedTo == from
                && accessGrants[cid][i].active) {
                accessGrants[cid][i].active = false;
                found = true;
            }
        }

        require(
            found,
            "DecentraBox: no active grant found"
        );

        emit AccessRevoked(
            cid,
            msg.sender,
            from
        );

        emit FileEvent(
            cid,
            msg.sender,
            "ACCESS_REVOKED",
            block.timestamp
        );
    }

    /**
     * Record that someone accessed a file.
     * Called async by backend after signed URL generated.
     * Accessor is the USER's wallet — not msg.sender.
     *
     * @param cid      IPFS content identifier
     * @param accessor user's wallet who accessed file
     */
    function recordAccess(
        string memory cid,
        address accessor       // ← FIX: user wallet
    )
        external
        fileRegistered(cid)
    {
        accessCount[cid]++;

        emit FileEvent(
            cid,
            accessor,          // ← emit USER wallet
            "ACCESSED",
            block.timestamp
        );
    }

    /**
     * Transfer ownership to another wallet.
     */
    function transferOwnership(
        string memory cid,
        address newOwner
    )
        external
        fileRegistered(cid)
        onlyFileOwner(cid)
    {
        require(
            newOwner != address(0),
            "DecentraBox: invalid new owner"
        );
        require(
            newOwner != msg.sender,
            "DecentraBox: already the owner"
        );

        address oldOwner = fileOwners[cid];
        fileOwners[cid] = newOwner;

        emit FileEvent(
            cid,
            oldOwner,
            "OWNERSHIP_TRANSFERRED",
            block.timestamp
        );
    }

    // ── Read Functions (free, no gas) ─────────────────

    /**
     * Check if a wallet can access a file.
     * Owner always has access.
     * Others need active non-expired grant.
     */
    function canAccess(
        string memory cid,
        address who
    )
        external
        view
        returns (bool)
    {
        if (!fileExists[cid]) return false;

        // owner always has access
        if (fileOwners[cid] == who) return true;

        // check access grants
        for (uint256 i = 0;
             i < accessGrants[cid].length;
             i++) {
            AccessGrant memory grant =
                accessGrants[cid][i];

            if (grant.grantedTo == who
                && grant.active == true
                && (grant.expiresAt == 0
                    || block.timestamp
                        < grant.expiresAt)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Get owner wallet of a file.
     */
    function getFileOwner(
        string memory cid
    )
        external
        view
        returns (address)
    {
        return fileOwners[cid];
    }

    /**
     * Get all access grants for a file.
     */
    function getAccessGrants(
        string memory cid
    )
        external
        view
        fileRegistered(cid)
        returns (AccessGrant[] memory)
    {
        return accessGrants[cid];
    }

    /**
     * Get total access count for a file.
     */
    function getAccessCount(
        string memory cid
    )
        external
        view
        returns (uint256)
    {
        return accessCount[cid];
    }

    /**
     * Check if file is registered.
     */
    function isFileRegistered(
        string memory cid
    )
        external
        view
        returns (bool)
    {
        return fileExists[cid];
    }
}