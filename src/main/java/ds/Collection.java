package ds;

import java.util.ArrayList;
import java.util.List;

public class Collection {
    private int id;
    private String name;
    private String description;
    private List<Exhibit> exhibits;

    public Collection(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.exhibits = new ArrayList<>();
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Exhibit> getExhibits() {
        return exhibits;
    }

    public void setExhibits(List<Exhibit> exhibits) {
        this.exhibits = exhibits;
    }

    // Add and remove Exhibit from the Collection
    public void addExhibit(Exhibit exhibit) {
        exhibits.add(exhibit);
    }

    public void removeExhibit(Exhibit exhibit) {
        exhibits.remove(exhibit);
    }

    @Override
    public String toString() {
        return "Collection{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", exhibits=" + exhibits +
                '}';
    }
}
