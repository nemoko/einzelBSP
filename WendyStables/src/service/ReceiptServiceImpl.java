package service;

import entity.Receipt;
import entity.Reservation;
import javafx.collections.ObservableList;
import org.apache.log4j.Logger;
import persistance.ReceiptDAOImpl;

import java.util.Set;

public class ReceiptServiceImpl implements ReceiptService {

    private static final Logger logger = Logger.getLogger(ReceiptServiceImpl.class);
    private static ReceiptService service;

    public static ReceiptService initialize() {
        if( service == null) service = new ReceiptServiceImpl();
        return service;
    }

    @Override
    public void create(Set<Reservation> sr) {
        if(sr == null) return;
        else {
            int totalCharge = 0;

            for(Reservation r : sr) {
                int reservationCharge;
                reservationCharge = r.getDailyCharge();

                int miliToDays = 1000*3600*24;
                long days = (r.getEnd().getTime() - r.getStart().getTime()) / miliToDays;

                totalCharge += reservationCharge;
            }

            Receipt rc = new Receipt();
            rc.setTotalCharge(totalCharge);

            int receiptID = ReceiptDAOImpl.initialize().create(rc);
            rc.setId(receiptID);

            for(Reservation r : sr) {
                ReservationService res = new ReservationServiceImpl().initialize();
                res.update(r, rc);
            }
        }
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
