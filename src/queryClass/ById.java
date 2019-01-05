package queryClass;

import javafx.beans.property.SimpleStringProperty;

public class ById {
    private final SimpleStringProperty name;
    private final SimpleStringProperty institude;
    private final SimpleStringProperty _class;
    private final SimpleStringProperty room;

    public ById(String name, String institude, String _class, String room) {
        this.name = new SimpleStringProperty(name);
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

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
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
