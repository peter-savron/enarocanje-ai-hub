package si.savron.enarocanje.hub.persistence.models.common;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.util.UUID;

@MappedSuperclass
public class BaseEntity {
    @Id
    @UuidGenerator
    public UUID id;
    @Version
    public Integer version;
    public Instant createdAt;
    public Instant updatedAt;

    @PrePersist
    private void prePersist(){
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }
    @PreUpdate
    private void preUpdate(){
        this.updatedAt = Instant.now();
    }
}
