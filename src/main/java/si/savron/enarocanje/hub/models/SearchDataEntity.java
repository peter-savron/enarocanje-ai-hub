package si.savron.enarocanje.hub.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.Instant;

@Entity
public class SearchDataEntity extends BaseEntity {
    @Enumerated(EnumType.STRING)
    private SourceType type; // TODO public, private ...
    @Column(name = "last_search")
    private Instant lastSearch; // TODO check timezones!! (of search source) if it uses localized zones should be adapted
    @Column(name= "timezone")
    private String timezone;
}
