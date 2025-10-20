package si.savron.enarocanje.hub.models;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.util.UUID;

@MappedSuperclass
public class BaseEntity {
    @Id
    @UuidGenerator
    private UUID id;

    private Instant insertTs;

    private Instant updateTs;

    @PrePersist
    public void initialize(){
        insertTs = Instant.now();
        updateTs = Instant.now();
    }

    @PreUpdate
    public void onUpdate() {
        updateTs = Instant.now();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Instant getInsertTs() {
        return insertTs;
    }

    public void setInsertTs(Instant insertTs) {
        this.insertTs = insertTs;
    }

    public Instant getUpdateTs() {
        return updateTs;
    }

    public void setUpdateTs(Instant updateTs) {
        this.updateTs = updateTs;
    }
}
