package entity;

public class Box {

    private int id;
    private int reservationID;
    private String horseName; //SimpleStringProperty horseName = new SimpleStringProperty("");
    private int dailyRate;
    private String picURL; //SimpleStringProperty
    private int size;
    private String floor; //SimpleStringProperty
    private boolean window;
    private boolean outside;
    private boolean deleted;

    public int getId() {
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
        return horseName;
    } // return horseName.get();

    public void setHorseName(String horseName) {
        this.horseName = horseName;
    } // this.horseName = new SimpleStringProperty(horseName);

    public int getDailyRate() {
        return dailyRate;
    }

    public void setDailyRate(int dailyRate) {
        this.dailyRate = dailyRate;
    }

    public String getPicURL() {
        return picURL;
    }

    public void setPicURL(String picURL) {
        this.picURL = picURL;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String whatFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
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
                ", horseName='" + horseName + '\'' +
                ", dailyRate=" + dailyRate +
                ", picURL='" + picURL + '\'' +
                ", size=" + size +
                ", floor='" + floor + '\'' +
                ", window=" + window +
                ", outside=" + outside +
                ", deleted=" + deleted +
                '}';
    }
}
