package entity;

import javafx.beans.property.SimpleStringProperty;

public class BoxReservation extends Box {

    private SimpleStringProperty horseName;

    public String getHorseName() {
        return horseName.get();
    }

    public void setHorseName(String horseName) {
        this.horseName = new SimpleStringProperty(horseName);
    }
}
