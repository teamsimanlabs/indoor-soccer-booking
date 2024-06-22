package com.indoor.dao;

import com.indoor.dao.ReservationDao;
import com.indoor.exception.DaoException;
import com.indoor.model.Reservation;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class ReservationDaoImpl implements ReservationDao {

    private final JdbcTemplate jdbcTemplate;

    public ReservationDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Reservation> getReservations() {
        return jdbcTemplate.query("SELECT * FROM reservations", this::mapRowToReservation);
    }

    @Override
    public Reservation getReservationById(int reservationId) {
        return jdbcTemplate.queryForObject("SELECT * FROM reservations WHERE reservation_id = ?", this::mapRowToReservation, reservationId);
    }

    @Override
    public Reservation createReservation(Reservation reservation) throws DaoException {
        String sql = "INSERT INTO reservations (user_id, field_id, start_time, end_time, total_price, discount, special_requests, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?) RETURNING reservation_id";
        try {
            Integer id = jdbcTemplate.queryForObject(sql, Integer.class,
                    reservation.getUserId(),
                    reservation.getFieldId(),
                    reservation.getStartTime(),
                    reservation.getEndTime(),
                    reservation.getTotalPrice(),
                    reservation.getDiscount(),
                    reservation.getSpecialRequests(),
                    reservation.getStatus()
            );
            if (id != null) {
                reservation.setReservationId(id);
            }
            return reservation;
        } catch (Exception e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public boolean updateReservation(Reservation reservation) {
        String sql = "UPDATE reservations SET user_id = ?, field_id = ?, start_time = ?, end_time = ?, total_price = ?, discount = ?, special_requests = ?, status = ? WHERE reservation_id = ?";
        return jdbcTemplate.update(sql,
                reservation.getUserId(),
                reservation.getFieldId(),
                reservation.getStartTime(),
                reservation.getEndTime(),
                reservation.getTotalPrice(),
                reservation.getDiscount(),
                reservation.getSpecialRequests(),
                reservation.getStatus(),
                reservation.getReservationId()
        ) > 0;
    }

    @Override
    public boolean deleteReservation(int reservationId) {
        String sql = "DELETE FROM reservations WHERE reservation_id = ?";
        return jdbcTemplate.update(sql, reservationId) > 0;
    }

    @Override
    public boolean isFieldBooked(int fieldId, Timestamp startTime, Timestamp endTime) {
        String sql = "SELECT COUNT(*) FROM reservations WHERE field_id = ? AND status = 'booked' AND " +
                "(start_time < ? AND end_time > ?)";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, fieldId, endTime, startTime);
        return count != null && count > 0;
    }

    private Reservation mapRowToReservation(ResultSet rs, int rowNum) throws SQLException {
        return new Reservation(
                rs.getInt("reservation_id"),
                rs.getInt("user_id"),
                rs.getInt("field_id"),
                rs.getTimestamp("start_time"),
                rs.getTimestamp("end_time"),
                rs.getBigDecimal("total_price"),
                rs.getBigDecimal("discount"),
                rs.getString("special_requests"),
                rs.getString("status"),
                rs.getTimestamp("created_at")
        );
    }
}
