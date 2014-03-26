package service;

import entity.Box;
import entity.BoxReservation;
import entity.Reservation;
import exception.BoxException;
import javafx.collections.ObservableList;

public interface BoxService {

    public void create(Box b) throws BoxException;
    public ObservableList<Box> findBox(Box b); //find by example
    public ObservableList<BoxReservation> find(BoxReservation b); //find by example
    public void update(Box b);
    public void delete(Box b);
}