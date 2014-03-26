package service.impl;

import entity.Box;

import java.util.List;

public interface BoxService {

    public Box create(Box b);
    public List<Box> find(Box b);
    public void update(Box b);
    public void delete(Box b);

}
