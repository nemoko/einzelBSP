package service;

import entity.Receipt;
import javafx.collections.ObservableList;

public interface ReceiptService {

    public void create(Receipt r);
    public ObservableList<Receipt> find(Receipt r);
    public void update(Receipt r);
    public void delete(Receipt r);
}