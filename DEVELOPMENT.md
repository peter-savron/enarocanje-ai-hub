# Versions
## 1.0.0
### Goal
- One source integrated
- Integration hardcoded (REST clients still used)
- LLM can query data and access specific source or entity via MCP
- SQL database with minimal schema
- No auth required
- Cronjob to fetch data
- Object storage for documents
- Conversion to plaintext for LLM (unoptimezed)
- Public repository of data
- Reading of archives and common text formats (byte and plaintext)

## 1.1.0
- Migrate to NoSQL db (mongo)
- Stop using rest clients, use common library that returns response
- Separate fetch and process logic.

## 1.2.0
- Add users: private (restricted) and public sources
