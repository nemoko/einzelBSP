package service;

import entity.Receipt;
import javafx.collections.ObservableList;
import org.apache.log4j.Logger;

public class ReceiptServiceImpl implements ReceiptService {

    private static final Logger logger = Logger.getLogger(ReceiptServiceImpl.class);
    private static ReceiptService service;

    public static ReceiptService initialize() {
        if( service == null) service = new ReceiptServiceImpl();
        return service;
    }

    @Override
    public void create(Receipt r) {

    }

    @Override
    public ObservableList<Receipt> find(Receipt r) {
        return null;
    }

    @Override
    public void update(Receipt r) {

    }

    @Override
    public void delete(Receipt r) {

    }
}
