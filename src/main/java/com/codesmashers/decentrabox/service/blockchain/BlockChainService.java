package com.codesmashers.decentrabox.service.blockchain;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.abi.EventEncoder;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

import com.codesmashers.decentrabox.contract.DecentraBoxRegistry;
import com.codesmashers.decentrabox.model.dto.AccessGrantData;
import com.codesmashers.decentrabox.model.dto.FileEventData;

@Service
public class BlockChainService {

        private final Web3j web3j;
        private final Credentials credentials;
        private final DecentraBoxRegistry decentraBoxRegistry;

        Logger logger = LoggerFactory.getLogger(BlockChainService.class);

        private final String contractAddress;

        // ── Config values as fields ───────────────────────
        private final String rpcUrl;
        private final String privateKey;

        // ── Constructor ───────────────────────────────────
        public BlockChainService(
                        @Value("${blockchain.rpc-url}") String rpcUrl,
                        @Value("${blockchain.contract-address}") String contractAddress,
                        @Value("${blockchain.private-key}") String privateKey) {

                // store in fields first
                this.rpcUrl = rpcUrl;
                this.contractAddress = contractAddress;
                this.privateKey = privateKey;

                // then use fields to build everything
                this.web3j = buildWeb3j();
                this.credentials = buildCredentials();
                this.decentraBoxRegistry = loadContract();

                logger.info("BlockChainService initialized." +
                                " Contract: {}", this.contractAddress);
        }

        private Web3j buildWeb3j() {
                logger.info("Connecting to Sepolia. " +
                                "RPC: {}", rpcUrl);
                return Web3j.build(
                                new HttpService(this.rpcUrl));
        }

        private Credentials buildCredentials() {
                logger.info("Loading backend wallet " +
                                "credentials...");
                return Credentials.create(this.privateKey);
        }

        private DecentraBoxRegistry loadContract() {
                logger.info("Loading contract at: {}",
                                this.contractAddress);
                return DecentraBoxRegistry.load(
                                this.contractAddress,
                                this.web3j,
                                this.credentials,
                                new DefaultGasProvider());
        }

        public String registerFile(String cid, String ownerId) {

                try {

                        logger.info("Registering file on blockchain." +
                                        " CID: {}", cid);
                        TransactionReceipt receipt = decentraBoxRegistry.registerFile(cid, ownerId).send();

                        String txHash = receipt.getBlockHash();

                        logger.info("File registered. " +
                                        "CID: {}, txHash: {}", cid, txHash);

                        return txHash;
                } catch (Exception e) {
                        logger.error("registerFile failed. " +
                                        "CID: {}, error: {}",
                                        cid, e.getMessage());
                        throw new RuntimeException(
                                        "Blockchain registration failed: "
                                                        + e.getMessage());
                }
        }

        public String grantAccess(
                        String cid,
                        String walletAddress,
                        int expiryDays) {
                try {
                        logger.info("Granting access. CID: {}, " +
                                        "wallet: {}, days: {}",
                                        cid, walletAddress, expiryDays);

                        var receipt = decentraBoxRegistry
                                        .grantAccess(
                                                        cid,
                                                        walletAddress,
                                                        BigInteger.valueOf(expiryDays))
                                        .send();

                        String txHash = receipt
                                        .getTransactionHash();

                        logger.info("Access granted. " +
                                        "CID: {}, txHash: {}",
                                        cid, txHash);

                        return txHash;

                } catch (Exception e) {
                        logger.error("grantAccess failed. " +
                                        "CID: {}, error: {}",
                                        cid, e.getMessage());
                        throw new RuntimeException(
                                        "Grant access failed: "
                                                        + e.getMessage());
                }
        }

        public String revokeAccess(
                        String cid,
                        String walletAddress) {
                try {
                        logger.info("Revoking access. CID: {}, " +
                                        "wallet: {}", cid, walletAddress);

                        var receipt = decentraBoxRegistry
                                        .revokeAccess(cid, walletAddress)
                                        .send();

                        String txHash = receipt
                                        .getTransactionHash();

                        logger.info("Access revoked. " +
                                        "CID: {}, txHash: {}",
                                        cid, txHash);

                        return txHash;

                } catch (Exception e) {
                        logger.error("revokeAccess failed. " +
                                        "CID: {}, error: {}",
                                        cid, e.getMessage());
                        throw new RuntimeException(
                                        "Revoke access failed: "
                                                        + e.getMessage());
                }
        }

        public void recordAccess(
                        String cid,
                        String walletAddress) {

                // fire and forget — never block main thread
                CompletableFuture.runAsync(() -> {
                        try {
                                logger.info("Recording access. " +
                                                "CID: {}", cid);

                                decentraBoxRegistry.recordAccess(
                                                cid, walletAddress).send();

                                logger.info("Access recorded. " +
                                                "CID: {}", cid);

                        } catch (Exception e) {
                                // never throw — just log warning
                                logger.warn("recordAccess failed " +
                                                "(non-critical). CID: {}, " +
                                                "error: {}",
                                                cid, e.getMessage());
                        }
                });
        }

        public String transferOwnership(
                        String cid,
                        String newOwnerWallet) {
                try {
                        logger.info("Transferring ownership. " +
                                        "CID: {}, newOwner: {}",
                                        cid, newOwnerWallet);

                        var receipt = decentraBoxRegistry
                                        .transferOwnership(
                                                        cid, newOwnerWallet)
                                        .send();

                        String txHash = receipt
                                        .getTransactionHash();

                        logger.info("Ownership transferred. " +
                                        "CID: {}, txHash: {}",
                                        cid, txHash);

                        return txHash;

                } catch (Exception e) {
                        logger.error("transferOwnership failed. " +
                                        "CID: {}, error: {}",
                                        cid, e.getMessage());
                        throw new RuntimeException(
                                        "Transfer ownership failed: "
                                                        + e.getMessage());
                }
        }

        public boolean canAccess(
                        String cid,
                        String walletAddress) {
                try {
                        Boolean result = decentraBoxRegistry.canAccess(cid, walletAddress).send();

                        return result != null && result;

                } catch (Exception e) {
                        // safe default — deny access on error
                        logger.warn("canAccess failed. " +
                                        "CID: {}, error: {}",
                                        cid, e.getMessage());
                        return false;
                }
        }

        public String getFileOwner(String cid) {
                try {
                        return decentraBoxRegistry.getFileOwner(cid).send();

                } catch (Exception e) {
                        logger.warn("getFileOwner failed. " +
                                        "CID: {}, error: {}",
                                        cid, e.getMessage());
                        return null;
                }
        }

        public List<AccessGrantData> getAccessGrants(
                        String cid) {
                try {
                        // get raw tuple list from contract
                        List<?> rawGrants = decentraBoxRegistry
                                        .getAccessGrants(cid)
                                        .send();

                        // map to AccessGrantData DTOs
                        List<AccessGrantData> grants = new ArrayList<>();

                        for (Object raw : rawGrants) {
                                // Web3j returns tuples as typed objects
                                // generated wrapper handles the mapping
                                DecentraBoxRegistry.AccessGrant grant = (DecentraBoxRegistry.AccessGrant) raw;

                                AccessGrantData data = new AccessGrantData();
                                data.setGrantedTo(grant.grantedTo);
                                data.setGrantedAt(
                                                grant.grantedAt.longValue());
                                data.setExpiresAt(
                                                grant.expiresAt.longValue());
                                data.setActive(grant.active);

                                grants.add(data);
                        }

                        return grants;

                } catch (Exception e) {
                        logger.warn("getAccessGrants failed. " +
                                        "CID: {}, error: {}",
                                        cid, e.getMessage());
                        return new ArrayList<>();
                }
        }

        public List<FileEventData> getFileHistory(
                        String cid) {
                try {
                        EthFilter filter = new EthFilter(
                                        DefaultBlockParameterName.EARLIEST,
                                        DefaultBlockParameterName.LATEST,
                                        contractAddress);

                        // add topic so we only get FileEvent logs
                        filter.addSingleTopic(
                                        EventEncoder.encode(
                                                        DecentraBoxRegistry.FILEEVENT_EVENT));

                        // use flowable — NOT getFileEventEvents
                        List<FileEventData> result = new ArrayList<>();

                        decentraBoxRegistry.fileEventEventFlowable(filter)
                                        .blockingForEach(e -> {

                                                // cid is byte[] — convert to String
                                                String eventCid = new String(
                                                                e.cid,
                                                                java.nio.charset.StandardCharsets.UTF_8);

                                                // filter by cid
                                                if (eventCid.equals(cid)) {
                                                        FileEventData data = new FileEventData();
                                                        data.setCid(eventCid);
                                                        data.setAction(e.action);
                                                        data.setActor(e.actor);
                                                        data.setTimestamp(
                                                                        e.timestamp.longValue());
                                                        data.setTxHash(
                                                                        e.log.getTransactionHash());
                                                        result.add(data);
                                                }
                                        });

                        logger.info("Found {} events for CID: {}",
                                        result.size(), cid);

                        return result;

                } catch (Exception e) {
                        logger.warn("getFileHistory failed. " +
                                        "CID: {}, error: {}",
                                        cid, e.getMessage());
                        return new ArrayList<>();
                }
        }

        public boolean isFileRegistered(String cid) {
                try {
                        Boolean result = decentraBoxRegistry
                                        .isFileRegistered(cid)
                                        .send();

                        return result != null && result;

                } catch (Exception e) {
                        logger.warn("isFileRegistered failed. " +
                                        "CID: {}, error: {}",
                                        cid, e.getMessage());
                        return false;
                }
        }

        public long getAccessCount(String cid) {
                try {
                        BigInteger count = decentraBoxRegistry
                                        .getAccessCount(cid)
                                        .send();

                        return count != null
                                        ? count.longValue()
                                        : 0L;

                } catch (Exception e) {
                        logger.warn("getAccessCount failed. " +
                                        "CID: {}, error: {}",
                                        cid, e.getMessage());
                        return 0L;
                }
        }
}
