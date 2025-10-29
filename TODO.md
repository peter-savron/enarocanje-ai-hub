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

map data in object storage to db with some metadata (DONE)
extract zip (DONE)
read filename, (DONE)
file content  with apache-tika (DONE)
guess encoding with juniversalchardet (smaller, faster) or ICU4J (heavier, more robust, more recent) (DONE)

## data fetching

Pridobivanje podatkov iz portala https://www.enarocanje.si. (PARTIALLY_DONE)
Pridobi link do dokumentacije iz data objecta, should be trivialno (DONE)

### obrazci (json)

Mapiraj podatke v podatkovno bazo in jih pripravi za shranjevanje, integriraj code list, (DONE) 
preveri tehtnost noSQL DB-ja. Podatki iz `/api/sifObrazec/sifObrazecGet?idObrazec=948086` (DONE)
Pridobi urlje, mappaj vazne podatke v metadata (DONE) 

### dokumantacija (datoteke)

Pridobi datoteke iz danih linkov, kot prvo samo iz (DONE)
`/api/datoteka/get?id=NDA0MDQ5O1JhenBpc25hIGRva3VtZW50YWNpamEuemlw`
preberi tip datoteke iz Content-Disposition headerja in vsebino pahandlaj.(DONE)
Dodaj pametne metapodatke (DONE)

#### Unzipping

Ustvari util za unzippanje in branje dokumentov (DONE)

#### Documant parsing to machine readable code (Apache Tika)

Z Apache Tika preberi in pretvori pridobljene dokumente. (DONE)

## data storage

### VectorDb

Store chunks with vectorized data
Schema:
- id
- Document/Data id
- text
- owner (Partition key)

#### Vector db initialization

From smart kms project.

### Text vectorization

Chunk text (DONE)
Integrate with embedding service (OpenAI embeddings small)

### MCP for LLM

Fetch data from vector DB
Connect data via source id to scalar entities
Have ability to fetch whole document if necessary
Endpoint for querying Source, Documents/Data, SourceGroup
Can search also in scalar db via important fields to find sources

### Scalar db (LATER)

noSQL or SQL? store data for common scalar search with filters, mapping to object storage (DONE)
auto generate schemas, then liquibase (DROPPED)
create quick sql db for testing purposes (DONE)
switch to NoSQL and give option to create schema for each source and custom searches

### Object db (DONE)

Integration with minio/S3 (DONE)

## Microservices

Separate data fetching from external sites with document processing (decode and transform to Text) (DONE)

### Common data models (IN_PROGRESS)

Create a common library with common models

### Common data fetching
- allow to configure arbitrary data source of many types (local file or web url):
  - json
    - mark important fields and add description to them
  - zip
  - byte files
  - xml
  - markdown
  - code
- give possibilty to congigure search parameters (body, query and path param) and rules for fetching
- follow up search from first query (like in enarocila which goes query -> narocilo -> documents)

## Common schemas

### Active updating (directly update data via call to AI hub not viceversa (hub searches data))
Give possibilty to add and update data actively instead of passively

### SourceGroup/SourceConfig
Highest in hierarchy, it is used to create rules via which you fetch data. You fetch:
Type of endpoints:
- STRUCTURED_DATA:
  - JSON
  - XML
  - YAML
- UNSTRUCTURED_DATA:
  - ZIP
  - PDF
  - PLAINTEXT
Unstructured data is not so interesting, if used as root can only fetch one elemnt and update it in case of changes.
Structured data divides in Query and Fetch: from Query you get a list of elements you can fetch, from Fetch u get a source
and can be configured to additionally perform child fetches (like enarocilo -> documetation) you can also have child query for query.
Important thing is mapping should be configurable - so that user can decide which mapping to use. Fetching is defined as Flow:
(ex. Query []-> Query -> Fetch -> (Fetch, Fetch -> Fetch)
Importantly you have a root fetch from which all other Fetches can come from in a tree like structure. Reason for this is
that you now have one external id for a Source and can then from this id check everything.
If from one object you can't get another then they are not connected and you should configure another root.
A result of a fetch can be another Source if needed and will have new external id and Source object.
Child data objects of Source go into Data entities (describe the data, containt important data) end are stored in objectDB (as plaintext or byte docs, dump of all data).


### Updatable sources
- external id for sources to check if they are already present in db
- hash data to know if it changed

## Message queue for file processing (not urgent)

process file to text by queue

## NLP text splitters (advanced)
```xml
<dependency>
    <groupId>org.apache.opennlp</groupId>
    <artifactId>opennlp-tools</artifactId>
    <version>2.3.1</version>
</dependency>
<dependency>
    <groupId>edu.stanford.nlp</groupId>
    <artifactId>stanford-corenlp</artifactId>
    <version>4.5.6</version> 
</dependency>
<dependency>
    <groupId>edu.stanford.nlp</groupId>
    <artifactId>stanford-corenlp</artifactId>
    <version>4.5.6</version>
    <classifier>models</classifier>
</dependency>
```