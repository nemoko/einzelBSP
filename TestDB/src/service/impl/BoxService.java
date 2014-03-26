package service.impl;

import entity.Box;
import entity.Reservation;
import javafx.collections.ObservableList;

import java.util.List;

public interface BoxService extends DAO {

    public void create(Box b);
    public ObservableList<Box> find();
    public void update(Box b);
    public void reserve(Reservation r);
    public void delete(Box b);

}
