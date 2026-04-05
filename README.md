# 🚀 DecentraBox

> **Decentralized AI-Powered File Intelligence System**  
A secure, tamper-proof file vault where storage meets blockchain trust and AI intelligence.

---

## 🧠 Overview

DecentraBox is a next-generation file storage system that combines:

- **Decentralized storage (IPFS)**
- **Immutable audit logs (Blockchain)**
- **AI-powered insights (GPT-based)**

Unlike traditional platforms like Google Drive or Dropbox, DecentraBox enables users to **store, verify, track, and intelligently analyze files and their entire lifecycle**.

---

## 🔥 Key Features

### 🔐 Decentralized Storage
- Files are stored on **IPFS**
- Each file gets a unique **CID (Content Identifier)**
- No dependency on a single server

---

### ⛓️ Blockchain-Based Audit Trail
Every file action is recorded on blockchain:

- `UPLOADED`
- `ACCESSED`
- `ACCESS_GRANTED`
- `ACCESS_REVOKED`
- `POLICY_CHANGED`
- `OWNERSHIP_TRANSFERRED`

✅ Immutable  
✅ Tamper-proof  
✅ Publicly verifiable  

---

### 🧠 AI-Powered Insights (Optional)
- Generate:
  - File summaries
  - Smart tags
- AI is **opt-in only**
- No file is processed without user consent

---

### 🧨 Cross-File Blockchain RAG (Core Innovation)

Ask questions like:

- “Which files were accessed by others last week?”
- “Which sensitive files were shared externally?”
- “Summarize all activity on my documents”

The system combines:
- File content (IPFS)
- Blockchain event history
- Metadata (PostgreSQL)

---

## 🏗️ Architecture

Frontend (React)
↓
Spring Boot Backend (Java)
↓

IPFS (Pinata) Blockchain PostgreSQL AI Service
(File Storage) (Web3j) (Metadata) (Python + GPT)


---

## ⚙️ Tech Stack

| Layer        | Technology |
|-------------|-----------|
| Frontend    | React.js |
| Backend     | Spring Boot (Java) |
| Blockchain  | Solidity + Web3j + Sepolia |
| Storage     | IPFS (via Pinata) |
| Database    | PostgreSQL |
| AI Service  | Python + OpenAI API |

---

## 📂 Core Flow

1. User uploads file via frontend  
2. Backend uploads file to IPFS → gets CID  
3. CID is recorded on blockchain  
4. Metadata stored in PostgreSQL  
5. (Optional) AI generates summary & tags  
6. File is displayed in dashboard  

---

## 🔐 Privacy & Control

- AI is **completely optional**
- Users can disable AI processing per file
- All access is:
  - explicit
  - transparent
  - recorded on blockchain

---

## 💥 Why DecentraBox?

| Feature              | Traditional Storage | DecentraBox |
|--------------------|-------------------|-------------|
| Storage Type        | Centralized        | Decentralized (IPFS) |
| Access Logs         | Editable           | Immutable (Blockchain) |
| Transparency        | Limited            | Full |
| AI Processing       | Hidden             | User-controlled |
| Intelligence        | Basic              | Cross-file reasoning |

---

## 🧪 Demo Highlights

- Upload file → get CID  
- CID recorded on blockchain  
- Toggle AI ON/OFF  
- Query system using natural language  

---

## 🚀 Getting Started

### Backend (Spring Boot)

```bash
git clone https://github.com/Akshat-Rastogi-007/decentrabox.git
cd backend
./mvnw spring-boot:run

```

## Contributors
  - Akshat Rastogi
  - Yashvi
  - Tanu Somani

