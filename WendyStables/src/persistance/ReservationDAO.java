package persistance;

import entity.Receipt;
import entity.Reservation;
import exception.ReservationException;
import javafx.collections.ObservableList;

public interface ReservationDAO {

    public Reservation create(Reservation r) throws ReservationException;
    public ObservableList<Reservation> find(Reservation r);
    public ObservableList<Reservation> findActiveBox(Reservation r);
    public ObservableList<Reservation> findCustomer(Reservation r);
    public void update(Reservation r, Receipt rt);
    public void delete(Reservation r);

}
