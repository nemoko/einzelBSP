package entity;

import javafx.beans.property.SimpleStringProperty;

public class Box {

    private Integer id;
    private Integer reservationID;
    private SimpleStringProperty horseName;
    private Integer dailyRate;
    private SimpleStringProperty picURL;
    private Integer size;
    private SimpleStringProperty floor;
    private Boolean window;
    private Boolean outside;
    private Boolean deleted;

    public Box() {
        this.floor = new SimpleStringProperty("");
        this.horseName = new SimpleStringProperty("");
        this.picURL = new SimpleStringProperty("");
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getReservationID() {
        return reservationID;
    }

    public void setReservationID(Integer reservationID) {
        this.reservationID = reservationID;
    }

    public String getHorseName() {
        return horseName.get();
    }

    public void setHorseName(String horseName) {
        this.horseName = new SimpleStringProperty(horseName);
    }

    public Integer getDailyRate() {
        return dailyRate;
    }

    public void setDailyRate(Integer dailyRate) {
        this.dailyRate = dailyRate;
    }

    public String getPicURL() {
        return picURL.get();
    }

    public void setPicURL(String picURL) {
        this.picURL =  new SimpleStringProperty(picURL);
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getFloor() {
        return floor.get();
    }

    public void setFloor(String floor) {
        this.floor =  new SimpleStringProperty(floor);
    }

    public Boolean isWindow() {
        return window;
    }

    public void setWindow(Boolean window) {
        this.window = window;
    }

    public Boolean isOutside() {
        return outside;
    }

    public void setOutside(Boolean outside) {
        this.outside = outside;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
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
