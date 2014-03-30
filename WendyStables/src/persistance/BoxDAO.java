package persistance;

import entity.Box;
import entity.BoxReservation;
import exception.BoxException;
import javafx.collections.ObservableList;

public interface BoxDAO {

    public Box create(Box b) throws BoxException;
    public ObservableList<Box> findBox(Box b) throws BoxException;
    public ObservableList<BoxReservation> find(BoxReservation br) throws BoxException; //find by example
    public void update(Box b) throws BoxException;
    public void delete(Box b);

}
