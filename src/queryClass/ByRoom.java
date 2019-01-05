package queryClass;

import javafx.beans.property.SimpleStringProperty;

public class ByRoom {

    private final SimpleStringProperty name;
    private final SimpleStringProperty id;
    private final SimpleStringProperty institude;
    private final SimpleStringProperty _class;
    private final SimpleStringProperty bed;

    public ByRoom(String name, String id, String institude, String _class, String bed) {
        this.name = new SimpleStringProperty(name);
        this.id = new SimpleStringProperty(id);
        this.institude = new SimpleStringProperty(institude);
        this._class = new SimpleStringProperty(_class);
        this.bed = new SimpleStringProperty(bed);
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

    public String getBed() {
        return bed.get();
    }

    public SimpleStringProperty bedProperty() {
        return bed;
    }

    public void setBed(String bed) {
        this.bed.set(bed);
    }
}
