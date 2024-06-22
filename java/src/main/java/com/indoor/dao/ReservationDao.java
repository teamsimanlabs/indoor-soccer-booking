package com.indoor.dao;

import com.indoor.exception.DaoException;
import com.indoor.model.Reservation;
import java.sql.Timestamp;
import java.util.List;

public interface ReservationDao {
    List<Reservation> getReservations();
    Reservation getReservationById(int reservationId);
    Reservation createReservation(Reservation reservation) throws DaoException;
    boolean updateReservation(Reservation reservation);
    boolean deleteReservation(int reservationId);
    boolean isFieldBooked(int fieldId, Timestamp startTime, Timestamp endTime);
}
