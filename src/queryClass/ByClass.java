package queryClass;

import javafx.beans.property.SimpleStringProperty;

public class ByClass {

    private final SimpleStringProperty name;
    private final SimpleStringProperty id;
    private final SimpleStringProperty room;

    public ByClass(String name, String id, String room) {
        this.name = new SimpleStringProperty(name);
        this.id = new SimpleStringProperty(id);
        this.room = new SimpleStringProperty(room);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getId() {
        return id.get();
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getRoom() {
        return room.get();
    }

    public SimpleStringProperty roomProperty() {
        return room;
    }

    public void setRoom(String room) {
        this.room.set(room);
    }
}
