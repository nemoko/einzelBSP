package persistance;

import entity.Receipt;
import javafx.collections.ObservableList;

public interface ReceiptDAO {

    public Receipt create(Receipt r);
    public ObservableList<Receipt> find(Receipt r);
    public void update(Receipt r);
    public void delete(Receipt r);

}
