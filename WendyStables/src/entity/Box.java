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

//    @Override
//    public boolean equals(Object obj) {
//
//        if (obj == null)
//            return false;
//        if (getClass() != obj.getClass())
//            return false;
//
//        Box other = (Box) obj;
//        if (dailyRate == null) {
//            if (other.dailyRate != null)
//                return false;
//        } else if (!dailyRate.equals(other.dailyRate))
//            return false;
//        if (deleted == null) {
//            if (other.deleted != null)
//                return false;
//        } else if (!deleted.equals(other.deleted))
//            return false;
//        if (floor == null) {
//            if (other.floor != null)
//                return false;
//        } else if (!floor.equals(other.floor))
//            return false;
//        if (id == null) {
//            if (other.id != null)
//                return false;
//        } else if (!id.equals(other.id))
//            return false;
//        if (outside == null) {
//            if (other.outside != null)
//                return false;
//        } else if (!outside.equals(other.outside))
//            return false;
//        if (picURL == null) {
//            if (other.picURL != null)
//                return false;
//        } else if (!picURL.equals(other.picURL))
//            return false;
//        if (size == null) {
//            if (other.size != null)
//                return false;
//        } else if (!size.equals(other.size))
//            return false;
//        if (window == null) {
//            if (other.window != null)
//                return false;
//        } else if (!window.equals(other.window))
//            return false;
//        return true;
//    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Box box = (Box) o;

        if (dailyRate != null ? !dailyRate.equals(box.dailyRate) : box.dailyRate != null) return false;
        if (deleted != null ? !deleted.equals(box.deleted) : box.deleted != null) return false;
        if (floor != null ? !floor.equals(box.floor) : box.floor != null) return false;
        if (id != null ? !id.equals(box.id) : box.id != null) return false;
        if (outside != null ? !outside.equals(box.outside) : box.outside != null) return false;
        if (picURL != null ? !picURL.equals(box.picURL) : box.picURL != null) return false;
        if (size != null ? !size.equals(box.size) : box.size != null) return false;
        if (window != null ? !window.equals(box.window) : box.window != null) return false;

        return true;
    }
}
