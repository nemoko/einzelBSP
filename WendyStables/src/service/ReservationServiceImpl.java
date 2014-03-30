package service;

import entity.Receipt;
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
        if(r == null) {
            return null;
        } else {
            return ReservationDAOImpl.initialize().find(r);
        }
    }

    public boolean findActiveBox(Reservation r) {
        if(r == null) {
            return false;
        } else {
            ObservableList<Reservation> olist = ReservationDAOImpl.initialize().findActiveBox(r);
            return (olist.size() == 0);
        }
    }

    @Override
    public ObservableList<Reservation> findCustomer(Reservation r) {
        if(r == null) {
            return null;
        } else {
            return ReservationDAOImpl.initialize().findCustomer(r);
        }
    }


    @Override
    public void update(Reservation r, Receipt rt) {
        if(r == null || rt == null) {
            return;
        } else {
            ReservationDAOImpl.initialize().update(r,rt);
        }
    }

    @Override
    public void delete(Reservation r) {

    }
}
