package service;

import entity.Box;
import entity.BoxReservation;
import entity.Reservation;
import exception.BoxException;
import javafx.collections.ObservableList;

public interface BoxService {

    public Box create(Box b) throws BoxException;
    public ObservableList<Box> findBox(Box b) throws BoxException; //find by example
    public ObservableList<BoxReservation> find(BoxReservation b) throws BoxException; //find by example
    public void update(Box b) throws BoxException;
    public void delete(Box b);
}