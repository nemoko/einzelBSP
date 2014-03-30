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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Box box = (Box) o;

        if (!dailyRate.equals(box.dailyRate)) return false;
        if (!deleted.equals(box.deleted)) return false;
        if (!floor.equals(box.floor)) return false;
        if (!id.equals(box.id)) return false;
        if (!outside.equals(box.outside)) return false;
        if (picURL != null ? !picURL.equals(box.picURL) : box.picURL != null) return false;
        if (!size.equals(box.size)) return false;
        if (!window.equals(box.window)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + dailyRate.hashCode();
        result = 31 * result + (picURL != null ? picURL.hashCode() : 0);
        result = 31 * result + size.hashCode();
        result = 31 * result + floor.hashCode();
        result = 31 * result + window.hashCode();
        result = 31 * result + outside.hashCode();
        result = 31 * result + deleted.hashCode();
        return result;
    }
}
