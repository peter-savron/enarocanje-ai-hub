package si.savron.enarocanje.hub.dtos.enarocila;

import java.util.List;

public class SifCodelistEntry {
    private Integer id;
    private String label;
    private List<SifCodelistEntry> children;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<SifCodelistEntry> getChildren() {
        return children;
    }

    public void setChildren(List<SifCodelistEntry> children) {
        this.children = children;
    }
}
