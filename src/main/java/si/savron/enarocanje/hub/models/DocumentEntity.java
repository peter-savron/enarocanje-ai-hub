package si.savron.enarocanje.hub.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.apache.commons.io.FilenameUtils;

@Entity(name = "documents")
public class DocumentEntity extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "source", nullable = false)
    private String source;
    @Column(name = "stored")
    private boolean stored;
    @Column(name="processed")
    private boolean processed;
    @Column(name ="stored_vdb")
    private boolean storedInVectorDB;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "narocilo_id", nullable = false)
    @JsonIgnore // TODO remove once we dont return entities enymore
    private NarociloEntity narocilo;

    // TODO store both raw and processed in s3 same folder in same entity

    @Transient
    public String getS3Path() {
        return String.join("/", narocilo.getId().toString(), getId().toString(), name);
    }

    @Transient
    public String getProcessedS3Path() {
        return String.join("/", narocilo.getId().toString(), getId().toString(), FilenameUtils.getName(name).concat(".txt"));
    }

    @Transient
    public String getExtension() {
        return FilenameUtils.getExtension(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public NarociloEntity getNarocilo() {
        return narocilo;
    }

    public void setNarocilo(NarociloEntity narocilo) {
        this.narocilo = narocilo;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public boolean isStored() {
        return stored;
    }

    public void setStored(boolean stored) {
        this.stored = stored;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    public boolean isStoredInVectorDB() {
        return storedInVectorDB;
    }

    public void setStoredInVectorDB(boolean storedInVectorDB) {
        this.storedInVectorDB = storedInVectorDB;
    }
}
