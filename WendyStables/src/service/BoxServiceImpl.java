package service;

import entity.Box;
import exception.BoxException;
import javafx.collections.ObservableList;
import org.apache.log4j.Logger;
import persistance.BoxDAOImpl;

public class BoxServiceImpl implements BoxService {

    private static final Logger logger = Logger.getLogger(BoxServiceImpl.class);
    private static BoxService service;

    public static BoxService initialize() {
        if( service == null) service = new BoxServiceImpl();
        return service;
    }

    @Override
    public void create(Box b) throws BoxException {
        logger.info("create received by service layer");

        if(b == null) {
            throw new BoxException("box is null");
        } else {
            BoxDAOImpl.initialize().create(b);
        }
    }

    @Override
    public ObservableList<Box> find(Box b) {

        if(b == null) {
            //throw exception -> BOX not passed
            return null;
        } else {
            return BoxDAOImpl.initialize().find(b);
        }
    }

    @Override
    public void update(Box b) {
        if(b == null) {

        } else {
            BoxDAOImpl.initialize().update(b);
        }
    }

    @Override
    public void delete(Box b) {
        if(b == null) {

        } else {
            BoxDAOImpl.initialize().delete(b);
        }

    }
}
