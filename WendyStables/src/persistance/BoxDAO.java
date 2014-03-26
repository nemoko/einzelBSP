package persistance;

import entity.Box;
import javafx.collections.ObservableList;

public interface BoxDAO {

    public void create(Box b);
    public ObservableList<Box> find(Box b); //find by example
    public void update(Box b);
    public void delete(Box b);

}
