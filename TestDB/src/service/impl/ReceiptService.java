package service.impl;

import entity.Receipt;
import javafx.collections.ObservableList;

import java.util.List;

public interface ReceiptService extends DAO {

    public void create(Receipt r);
    public ObservableList<Receipt> find();
    public void update(Receipt r);
    public void delete(Receipt r);

}
