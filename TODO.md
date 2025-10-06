# eNarocanje AI hub

Cilj je pridobiti podatke iz enarocanja in jih nuditi preko modernega, naprednega servica, 
ki z uporabo vektorizacije, AI agentov, MCP protokola in vektorskih DB 
nudi bolj kakovosten pregled v razpise.

## docker deployment (DONE)

Services:
- milvus (DONE)
- minio (DONE)
- postgres (DONE)

## models

### obrazec

duplicate data to db, hash json to verify updates, transform data to merge them toghether:
obrazec merge with sif obrazec - read both as JSONobject and save as a single entity
store other razpis data

### documentation

map data in object storage to db with some metadata

## data fetching

Pridobivanje podatkov iz portala https://www.enarocanje.si.

### obrazci (json)

Mapiraj podatke v podatkovno bazo in jih pripravi za shranjevanje, integriraj code list, 
preveri tehtnost noSQL DB-ja. Podatki iz `/api/sifObrazec/sifObrazecGet?idObrazec=948086`

### dokumantacija (datoteke)

Pridobi datoteke iz danih linkov, kot prvo samo iz 
`/api/datoteka/get?id=NDA0MDQ5O1JhenBpc25hIGRva3VtZW50YWNpamEuemlw`
preberi tip datoteke iz Content-Disposition headerja in vsebino pahandlaj.

#### Unzipping

Ustvari util za unzippanje in branje dokumentov

#### Documant parsing to machine readable code (Apache Tika)

Z Apache Tika preberi in pretvori pridobljene dokumente.

## data storage

### VectorDb

Store chunks with vectorized data

#### Text vectorization

Integrate with embedding service

### Scalar db

noSQL or SQL? store data for common scalar search with filters, mapping to object storage

### Object db

Integration with minio/S3