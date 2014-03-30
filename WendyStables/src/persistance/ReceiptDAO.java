package persistance;

import entity.Receipt;
import entity.Reservation;
import javafx.collections.ObservableList;

import java.util.Set;

public interface ReceiptDAO {

    public int create(Receipt r);
    public ObservableList<Receipt> find(Receipt r);
    public void update(Receipt r);
    public void delete(Receipt r);

}
