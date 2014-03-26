package entity;

import javafx.beans.property.SimpleStringProperty;

public class Box {

    protected Integer id;
    protected Integer dailyRate;
    protected SimpleStringProperty picURL;
    protected Integer size;
    protected SimpleStringProperty floor;
    protected Boolean window;
    protected Boolean outside;
    protected Boolean deleted;

    public Box() {
        this.floor = new SimpleStringProperty("");
        this.picURL = new SimpleStringProperty("");
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
