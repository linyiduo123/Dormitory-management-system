package queryClass;

import javafx.beans.property.SimpleStringProperty;

public class ByName {
    private final SimpleStringProperty id;
    private final SimpleStringProperty institude;
    private final SimpleStringProperty _class;
    private final SimpleStringProperty room;

    public ByName(String id, String institude, String _class, String room) {
        this.id = new SimpleStringProperty(id);
        this.institude = new SimpleStringProperty(institude);
        this._class = new SimpleStringProperty(_class);
        this.room = new SimpleStringProperty(room);
    }

    public String getInstitude() {
        return institude.get();
    }

    public SimpleStringProperty institudeProperty() {
        return institude;
    }

    public void setInstitude(String institude) {
        this.institude.set(institude);
    }

    public String get_class() {
        return _class.get();
    }

    public SimpleStringProperty _classProperty() {
        return _class;
    }

    public void set_class(String _class) {
        this._class.set(_class);
    }

    public String getId() {
        return id.get();
    }

    public SimpleStringProperty nameProperty() {
        return id;
    }

    public void setId(String name) {
        this.id.set(name);
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
