package service;

import entity.Receipt;
import entity.Reservation;
import javafx.collections.ObservableList;

import java.util.Set;

public interface ReceiptService {

    public void create(Set<Reservation> sr);
    public ObservableList<Receipt> find(Receipt r);
    public void update(Receipt r);
    public void delete(Receipt r);
}