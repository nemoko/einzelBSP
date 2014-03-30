package entity;

import javafx.beans.property.SimpleStringProperty;

import java.sql.Date;

public class Reservation {

    protected Integer id;
    protected Integer boxID;
    protected Integer dailyCharge;
    protected Integer receiptID;
    protected SimpleStringProperty customerName;
    protected SimpleStringProperty horseName;
    protected Date start;
    protected Date end;
    protected Boolean payed;

    public Reservation() {
        this.customerName = new SimpleStringProperty("");
        this.horseName = new SimpleStringProperty("");
    }

    public Boolean getPayed() {
        return payed;
    }

    public void setPayed(Boolean payed) {
        this.payed = payed;
    }

    public Integer getReceiptID() {
        return receiptID;
    }

    public void setReceiptID(Integer receiptID) {
        this.receiptID = receiptID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBoxID() {
        return boxID;
    }

    public void setBoxID(Integer boxID) {
        this.boxID = boxID;
    }

    public Integer getDailyCharge() {
        return dailyCharge;
    }

    public void setDailyCharge(Integer dailyCharge) {
        this.dailyCharge = dailyCharge;
    }

    public String getCustomerName() {
        return customerName.get();
    }

    public void setCustomerName(String customerName) {
        this.customerName = new SimpleStringProperty(customerName);
    }

    public String getHorseName() {
        return horseName.get();
    }

    public void setHorseName(String horseName) {
        this.horseName = new SimpleStringProperty(horseName);
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
