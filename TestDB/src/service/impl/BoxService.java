package service.impl;

import entity.Box;

import java.sql.SQLException;
import java.util.List;

public interface BoxService {

    public void create(Box b);
    public List<Box> find(Box b);
    public void update(Box b);
    public void delete(Box b);

}
