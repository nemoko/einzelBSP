package entity;

import javafx.beans.property.SimpleStringProperty;

import java.sql.Date;

public class BoxReservation extends Box {

    private SimpleStringProperty horseName;
    private Date start;
    private Date end;

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

    public void setEnd(Date end) {
        this.end = end;
    }
}
