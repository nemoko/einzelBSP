package service.impl;

import java.util.List;
import org.apache.log4j.Logger;
import entity.Box;

public class BoxServiceImpl implements BoxService {

    private static final Logger logger = Logger.getLogger(BoxServiceImpl.class);

    @Override
    public Box create(Box b) {



        logger.info("New Box should be created in the DB");
        return null;
    }

    @Override
    public List<Box> find(Box b) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void update(Box b) {
        // TODO Auto-generated method stub
    }

    @Override
    public void delete(Box b) {
        // TODO Auto-generated method stub
    }
}
