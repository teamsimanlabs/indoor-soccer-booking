package com.indoor.controller;

import com.indoor.dao.ReservationDao;
import com.indoor.exception.DaoException;
import com.indoor.model.Reservation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationDao reservationDao;

    public ReservationController(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    @GetMapping
    public List<Reservation> getReservations() {
        return reservationDao.getReservations();
    }

    @GetMapping("/{reservationId}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable int reservationId) {
        Reservation reservation = reservationDao.getReservationById(reservationId);
        if (reservation == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation not found.");
        }
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Reservation createReservation(@Valid @RequestBody Reservation newReservation) {
        Timestamp startTime = newReservation.getStartTime();
        Timestamp endTime = newReservation.getEndTime();
        int fieldId = newReservation.getFieldId();
        if (reservationDao.isFieldBooked(fieldId, startTime, endTime)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Field is already booked for the specified time.");
        }

        try {
            return reservationDao.createReservation(newReservation);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Reservation creation failed.");
        }
    }

    @PutMapping("/{reservationId}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable int reservationId, @Valid @RequestBody Reservation updatedReservation) {
        updatedReservation.setReservationId(reservationId);
        if (reservationDao.updateReservation(updatedReservation)) {
            return new ResponseEntity<>(updatedReservation, HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Reservation update failed.");
        }
    }

    @DeleteMapping("/{reservationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReservation(@PathVariable int reservationId) {
        if (!reservationDao.deleteReservation(reservationId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation not found.");
        }
    }
}
