package persistance;

import entity.Box;
import entity.BoxReservation;
import exception.BoxException;
import javafx.collections.ObservableList;

public interface BoxDAO {

    public void create(Box b) throws BoxException;
    public ObservableList<Box> findBox(Box b);
    public ObservableList<BoxReservation> find(BoxReservation br); //find by example
    public void update(Box b);
    public void delete(Box b);

}
