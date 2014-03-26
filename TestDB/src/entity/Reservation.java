package entity;

import java.sql.Date;

public class Reservation {

    private int id;
    private int boxID;
    private int dailyCharge;
    private String customerName;
    private String horseName;
    private Date start;
    private Date end;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBoxID() {
        return boxID;
    }

    public void setBoxID(int boxID) {
        this.boxID = boxID;
    }

    public int getDailyCharge() {
        return dailyCharge;
    }

    public void setDailyCharge(int dailyCharge) {
        this.dailyCharge = dailyCharge;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getHorseName() {
        return horseName;
    }

    public void setHorseName(String horseName) {
        this.horseName = horseName;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date until) {
        this.end = until;
    }
}
