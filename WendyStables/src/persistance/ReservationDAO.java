package persistance;

import entity.BoxReservation;
import entity.Reservation;
import exception.ReservationException;
import javafx.collections.ObservableList;

public interface ReservationDAO {

    public void create(Reservation r) throws ReservationException;
    public ObservableList<Reservation> find(Reservation r);
    public ObservableList<Reservation> findCustomer(Reservation r);
    public void update(Reservation r);
    public void delete(Reservation r);

}
