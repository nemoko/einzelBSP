package service.impl;

import entity.Box;
import entity.Reservation;
import javafx.collections.ObservableList;

import java.util.List;

public interface ReservationService extends DAO {

    public void create(Reservation r);
    public ObservableList<Reservation> find();
    public void update(Reservation r);
    public void delete(Reservation r);

}
