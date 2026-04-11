<div align="center">

<br />

```
██████╗ ███████╗ ██████╗███████╗███╗   ██╗████████╗██████╗  █████╗ ██████╗  ██████╗ ██╗  ██╗
██╔══██╗██╔════╝██╔════╝██╔════╝████╗  ██║╚══██╔══╝██╔══██╗██╔══██╗██╔══██╗██╔═══██╗╚██╗██╔╝
██║  ██║█████╗  ██║     █████╗  ██╔██╗ ██║   ██║   ██████╔╝███████║██████╔╝██║   ██║ ╚███╔╝ 
██║  ██║██╔══╝  ██║     ██╔══╝  ██║╚██╗██║   ██║   ██╔══██╗██╔══██║██╔══██╗██║   ██║ ██╔██╗ 
██████╔╝███████╗╚██████╗███████╗██║ ╚████║   ██║   ██║  ██║██║  ██║██████╔╝╚██████╔╝██╔╝ ██╗
╚═════╝ ╚══════╝ ╚═════╝╚══════╝╚═╝  ╚═══╝   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═╝╚═════╝  ╚═════╝ ╚═╝  ╚═╝
```

### *A verifiable, intelligent document vault where AI understands your files and blockchain proves you owned them first.*

<br />

[![Ethereum](https://img.shields.io/badge/Ethereum-Sepolia_Testnet-3C3C3D?style=for-the-badge&logo=ethereum&logoColor=white)](https://sepolia.etherscan.io)
[![IPFS](https://img.shields.io/badge/Storage-IPFS_via_Pinata-65C2CB?style=for-the-badge&logo=ipfs&logoColor=white)](https://pinata.cloud)
[![React](https://img.shields.io/badge/Frontend-React_+_ethers.js-61DAFB?style=for-the-badge&logo=react&logoColor=black)](https://react.dev)
[![Spring Boot](https://img.shields.io/badge/Backend-Spring_Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Python](https://img.shields.io/badge/AI_Service-Python_FastAPI-3776AB?style=for-the-badge&logo=python&logoColor=white)](https://fastapi.tiangolo.com)
[![MySQL](https://img.shields.io/badge/Database-MySQL_on_Aiven-4479A1?style=for-the-badge&logo=mysql&logoColor=white)](https://aiven.io)
[![License](https://img.shields.io/badge/License-MIT-yellow?style=for-the-badge)](LICENSE)

</div>

---

## The Problem with Traditional File Storage

| Problem | Google Drive / Dropbox | DecentraBox |
|---|---|---|
| File deletion | Central server can delete your files | IPFS — no single server exists to delete from |
| Access logs | Can be edited or fabricated | Immutable on-chain audit trail |
| Ownership proof | None | Cryptographically signed, on-chain, forever |
| Intelligence | None — just storage | AI understands content AND blockchain history |
| Legal vulnerability | Can be subpoenaed or shut down | Decentralized — no single point of control |

---

## How It Works

DecentraBox is three battle-tested technologies composing into one coherent product:

```
┌─────────────────────────────────────────────────────────────────┐
│                        User (React + MetaMask)                  │
└────────────────────────────┬────────────────────────────────────┘
                             │
                             ▼
┌─────────────────────────────────────────────────────────────────┐
│                  Spring Boot Backend (Orchestrator)             │
│                    JWT Auth · Web3j · REST APIs                 │
└──────────┬──────────────────┬──────────────────┬───────────────┘
           │                  │                  │
           ▼                  ▼                  ▼
┌─────────────────┐  ┌────────────────┐  ┌───────────────────────┐
│   IPFS (Pinata) │  │  MySQL (Aiven) │  │ Solidity Smart        │
│                 │  │                │  │ Contract on Sepolia   │
│  Private file   │  │  File metadata │  │                       │
│  storage        │  │  User records  │  │  Ownership registry   │
│  CID-addressed  │  │  Access grants │  │  Access control       │
│                 │  │                │  │  Immutable audit log  │
└─────────────────┘  └────────────────┘  └───────────────────────┘
                             │
                             ▼
┌─────────────────────────────────────────────────────────────────┐
│                    Python AI Service (FastAPI)                  │
│         Auto-tagging · Summarization · PII Detection           │
│         Cross-file RAG · Blockchain Audit Intelligence         │
└─────────────────────────────────────────────────────────────────┘
```

---

## Core User Flow

```
1. Register + link MetaMask wallet
   └─ Signs a one-time challenge → proves wallet ownership (no gas, free)

2. Upload a file
   ├─ AI scans for PII → warns before anything is written permanently
   ├─ File stored privately on IPFS → gets a CID
   ├─ Ownership registered on-chain: fileOwners[CID] = wallet address
   └─ AI generates: summary, tags, category

3. Share & control access
   ├─ Grant wallet access with optional expiry
   ├─ Smart contract enforces: canAccess(cid, wallet)
   └─ Every access event logged on-chain permanently

4. Query with AI
   └─ "Which of my files were accessed by someone other than me?"
      AI reasons over BOTH file content AND blockchain history simultaneously
```

---

## The 3 AI Features That Set It Apart

### 🔍 Cross-File Blockchain RAG

Standard file search tells you where a file is. This tells you what happened to it:

> *"Which of my contracts have been accessed by someone other than me, and what do those contracts say about payment terms?"*

The AI reasons over **file content and blockchain access history simultaneously**. This is only possible because the access history is immutable and trustworthy — not a database we could edit.

---

### 🛡️ PII Shield

Before a file is written permanently to IPFS, the AI scans for:

- Aadhaar & PAN numbers
- Social Security Numbers
- Phone numbers & bank accounts
- Email addresses

If sensitive data is detected, the user sees a warning *before* the upload is committed. Permanent storage deserves a permanent decision.

---

### 📊 Blockchain Audit Trail Intelligence

Raw blockchain logs are unreadable. DecentraBox narrates them:

**Raw log:**
```
0xDEF456 — ACCESSED — 1711234567
0xDEF456 — ACCESSED — 1711234592
...
```

**AI narration:**
> *"Wallet 0xDEF456 accessed `contract.pdf` 11 times in under 2 hours on March 24th. This pattern is consistent with automated scraping. Consider revoking access."*

---

## Smart Contract — `DecentraBoxRegistry`

Deployed on the Ethereum Sepolia Testnet.

```solidity
// Register file ownership — permanent, tamper-proof
registerFile(string cid, address owner)

// Grant time-bound access to a wallet
grantAccess(string cid, address wallet, uint expiryDays)

// Revoke access instantly
revokeAccess(string cid, address wallet)

// Record an access event — immutable audit trail entry
recordAccess(string cid, address accessor)

// Read-only access check — called before every file view
canAccess(string cid, address wallet) returns (bool)

// Full event log — AI reads this for intelligence features
getFileHistory(string cid) returns (FileEvent[])
```

Every action emits a `FileEvent`:

```solidity
event FileEvent(
    string  cid,
    address actor,
    string  action,
    uint256 timestamp
);
```

All events are permanent, tamper-proof, and publicly verifiable on Etherscan.

---

## Security Model

Access to any file requires **all three layers simultaneously:**

```
Layer 1 — Pinata (File Level)
  File is private — the CID alone returns 403 Forbidden
  No signed URL, no file

Layer 2 — Blockchain (Access Level)
  Backend calls canAccess(cid, wallet) before generating any URL
  Smart contract is the single source of truth for access rights

Layer 3 — JWT (API Level)
  All endpoints require a valid JWT
  Must be authenticated before any blockchain check is even attempted

Valid JWT  +  canAccess() == true  +  Pinata signed URL  =  File access
```

---

## Wallet Integration

MetaMask is used for **one thing only**: proving wallet ownership at registration (one-time signature, no gas fee, completely free). After that:

- The backend server wallet handles **all** on-chain transactions
- The backend wallet pays **all** gas fees (Sepolia ETH)
- Users never interact with MetaMask again
- Full blockchain benefits — zero blockchain friction

---

## Tech Stack

| Layer | Technology |
|---|---|
| **Frontend** | React, ethers.js, MetaMask |
| **Backend** | Spring Boot (Java), Web3j, JWT |
| **Storage** | IPFS via Pinata (private files) |
| **Database** | MySQL on Aiven Cloud |
| **Blockchain** | Solidity on Ethereum Sepolia via Infura |
| **AI Service** | Python FastAPI, Langchain |
| **Auth** | JWT + Google / GitHub OAuth2 |
| **Wallet** | MetaMask (signature verification) |

---

## API Reference

**22 endpoints across 5 domains:**

| Domain | Endpoints |
|---|---|
| **Auth** | `POST /register` · `POST /login` · `GET /oauth/google` · `GET /oauth/github` |
| **Wallet** | `POST /wallet/challenge` · `POST /wallet/link` · `DELETE /wallet/unlink` |
| **Files** | `POST /files/upload` · `GET /files` · `GET /files/:id` · `GET /files/:id/url` · `DELETE /files/:id` |
| **Access** | `POST /access/grant` · `DELETE /access/revoke` · `GET /access/list` · `GET /access/history/:cid` |
| **AI** | `POST /ai/query` · `GET /ai/analysis/:id` · `GET /ai/chain-analysis/:cid` |
| **Admin** | `GET /admin/users` · `GET /admin/files` · `POST /admin/promote` · `GET /admin/stats` |

---

## Getting Started

### Prerequisites

- Node.js 18+
- Java 17+
- Python 3.10+
- MySQL 8+
- MetaMask browser extension
- Pinata account (IPFS)
- Infura account (Ethereum RPC)

### 1. Clone the Repository

```bash
git clone https://github.com/Akshat-Rastogi-007/DecentraBox
cd decentrabox
```

### 2. Backend — Spring Boot

```bash
cd backend

# Configure environment
cp src/main/resources/application.example.yml src/main/resources/application.yml
# Fill in: MySQL URL, Pinata API keys, Infura RPC URL, server wallet private key

# Run
./mvnw spring-boot:run
```

### 3. AI Service — Python FastAPI

```bash
cd ai-service

python -m venv venv
source venv/bin/activate        # Windows: venv\Scripts\activate

pip install -r requirements.txt

# Configure environment
cp .env.example .env
# Fill in: AI API key, backend service URL

uvicorn main:app --reload --port 8001
```

### 4. Frontend — React

```bash
cd frontend

npm install

# Configure environment
cp .env.example .env
# Fill in: backend URL, deployed contract address

npm run dev
```

### 5. Smart Contract — Deploy to Sepolia

```bash
cd contracts

npm install
npx hardhat compile
npx hardhat run scripts/deploy.js --network sepolia
```

Copy the deployed contract address into your backend and frontend `.env` files.

---

## Environment Variables

### Backend (`application.yml`)

```yaml
spring.datasource.url:          jdbc:mysql://<aiven-host>/<db>
spring.datasource.username:     <user>
spring.datasource.password:     <password>

pinata.api.key:                 <pinata-key>
pinata.api.secret:              <pinata-secret>

infura.rpc.url:                 https://sepolia.infura.io/v3/<project-id>
blockchain.server.wallet.key:   <private-key>
blockchain.contract.address:    <deployed-contract-address>

jwt.secret:                     <secret>
ai.service.url:                 http://localhost:8001
```

### AI Service (`.env`)

```env
AI_API_KEY=<your-api-key>
BACKEND_URL=http://localhost:8080
```

### Frontend (`.env`)

```env
VITE_BACKEND_URL=http://localhost:8080
VITE_CONTRACT_ADDRESS=<deployed-contract-address>
```

---

## How It Compares to Filecoin / Arweave

Filecoin and Arweave are **infrastructure layers** — the equivalent of Amazon S3. DecentraBox is a **user-facing product** built on top of that infrastructure — the equivalent of Dropbox. 

The distinction that matters: DecentraBox adds AI that understands file content **and** the entire blockchain lifecycle of that file. No other product does this combination.

---

## Frequently Asked Questions

**Why not just use Google Drive?**  
Google can delete your files, modify access logs, and be compelled to comply with legal orders. DecentraBox gives you tamper-proof storage and cryptographic proof of ownership. No central authority controls your files.

**Isn't IPFS public — is there a privacy risk?**  
Files on Pinata are private. A CID alone returns `403 Forbidden`. Only our backend generates signed URLs, and only after the smart contract confirms access rights via `canAccess()`.

**Is blockchain actually necessary here?**  
Yes. The blockchain audit trail is what makes the AI intelligence *trustworthy*. Without it, access logs are just entries in a database we could modify. With it, the history is cryptographically immutable — the AI's analysis is grounded in unfalsifiable data.

**Does the user need to pay gas fees?**  
No. The backend server wallet handles all on-chain transactions and pays all gas fees. Users interact only with MetaMask once at registration to sign an ownership proof — no cost, no transaction.

---

## Project Structure

```
decentrabox/
├── frontend/               # React app — ethers.js, MetaMask integration
│   ├── src/
│   │   ├── components/
│   │   ├── pages/
│   │   └── utils/
│   └── package.json
│
├── backend/                # Spring Boot orchestrator
│   └── src/main/java/
│       ├── auth/           # JWT + OAuth2
│       ├── blockchain/     # Web3j + contract interaction
│       ├── files/          # Upload, IPFS, metadata
│       ├── access/         # Grant, revoke, history
│       └── ai/             # AI service client
│
├── ai-service/             # Python FastAPI
│   ├── routes/
│   │   ├── rag.py          # Cross-file RAG
│   │   ├── analysis.py     # File tagging & summary
│   │   ├── pii.py          # PII detection
│   │   └── chain.py        # Audit trail intelligence
│   └── main.py
│
└── contracts/              # Solidity
    ├── DecentraBoxRegistry.sol
    └── scripts/
        └── deploy.js
```

---

## License

This project is licensed under the MIT License. See [LICENSE](LICENSE) for details.

---

<div align="center">

Built with IPFS · Ethereum · Python · Spring Boot · React

</div>
