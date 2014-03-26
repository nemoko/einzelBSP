package service.impl;

import entity.Receipt;

import java.util.List;

public interface ReceiptService extends DAO {

    public void create(Receipt r);
    public List<Receipt> find(Receipt r);
    public void update(Receipt r);
    public void delete(Receipt r);

}
