package service;

import entity.Reservation;
import exception.ReservationException;
import javafx.collections.ObservableList;
import org.apache.log4j.Logger;
import persistance.ReservationDAOImpl;

public class ReservationServiceImpl implements ReservationService {

    private static final Logger logger = Logger.getLogger(ReservationServiceImpl.class);
    private static ReservationService service;

    public static ReservationService initialize() {
        if( service == null) service = new ReservationServiceImpl();
        return service;
    }
    @Override
    public void create(Reservation r) throws ReservationException {
        ReservationDAOImpl.initialize().create(r);
    }

    @Override
    public ObservableList<Reservation> find(Reservation r) {
        return null;
    }

    @Override
    public void update(Reservation r) {

    }

    @Override
    public void delete(Reservation r) {

    }
}
