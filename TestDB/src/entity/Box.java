package entity;

import javafx.beans.property.SimpleStringProperty;

public class Box {

    private Integer id;
    private int reservationID;
    private SimpleStringProperty horseName;
    private int dailyRate;
    private SimpleStringProperty picURL;
    private int size;
    private SimpleStringProperty floor;
    private boolean window;
    private boolean outside;
    private boolean deleted;

    public Box() {
        this.floor = new SimpleStringProperty("");
        this.horseName = new SimpleStringProperty("");
        this.picURL = new SimpleStringProperty("");
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReservationID() {
        return reservationID;
    }

    public void setReservationID(int reservationID) {
        this.reservationID = reservationID;
    }

    public String getHorseName() {
        return horseName.get();
    }

    public void setHorseName(String horseName) {
        this.horseName = new SimpleStringProperty(horseName);
    }

    public int getDailyRate() {
        return dailyRate;
    }

    public void setDailyRate(int dailyRate) {
        this.dailyRate = dailyRate;
    }

    public String getPicURL() {
        return picURL.get();
    }

    public void setPicURL(String picURL) {
        this.picURL =  new SimpleStringProperty(picURL);
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String whatFloor() {
        return floor.get();
    }

    public void setFloor(String floor) {
        this.floor =  new SimpleStringProperty(floor);
    }

    public boolean isWindow() {
        return window;
    }

    public void setWindow(boolean window) {
        this.window = window;
    }

    public boolean isOutside() {
        return outside;
    }

    public void setOutside(boolean outside) {
        this.outside = outside;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "Box{" +
                "id=" + id +
                ", reservationID=" + reservationID +
                ", horseName='" + horseName.get() + '\'' +
                ", dailyRate=" + dailyRate +
                ", picURL='" + picURL.get() + '\'' +
                ", size=" + size +
                ", floor='" + floor.get() + '\'' +
                ", window=" + window +
                ", outside=" + outside +
                ", deleted=" + deleted +
                '}';
    }
}
