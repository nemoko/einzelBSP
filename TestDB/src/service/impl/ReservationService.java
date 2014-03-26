package service.impl;

import entity.Reservation;

import java.util.List;

public interface ReservationService extends DAO {

    public void create(Reservation r);
    public List<Reservation> find(Reservation r);
    public void update(Reservation r);
    public void delete(Reservation r);

}
