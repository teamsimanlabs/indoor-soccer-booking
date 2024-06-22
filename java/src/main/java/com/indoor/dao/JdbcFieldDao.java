package com.indoor.dao;

import com.indoor.exception.DaoException;
import com.indoor.model.Field;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcFieldDao implements FieldDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcFieldDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Field> getFields() {
        List<Field> fields = new ArrayList<>();
        String sql = "SELECT field_id, name, description, location, latitude, longitude, images, status, maintenance_logs, created_at FROM fields";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                Field field = mapRowToField(results);
                fields.add(field);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return fields;
    }

    @Override
    public Field getFieldById(int fieldId) {
        Field field = null;
        String sql = "SELECT field_id, name, description, location, latitude, longitude, images, status, maintenance_logs, created_at FROM fields WHERE field_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, fieldId);
            if (results.next()) {
                field = mapRowToField(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return field;
    }

    @Override
    public Field createField(Field field) {
        Field newField = null;
        String sql = "INSERT INTO fields (name, description, location, latitude, longitude, images, status, maintenance_logs, created_at) " +
                "VALUES (?, ?, ?, ?, ?, CAST(? AS json), ?, CAST(? AS json), CURRENT_TIMESTAMP) RETURNING field_id";
        try {
            int newFieldId = jdbcTemplate.queryForObject(sql, int.class, field.getName(), field.getDescription(), field.getLocation(),
                    field.getLatitude(), field.getLongitude(), field.getImages(),
                    field.getStatus(), field.getMaintenanceLogs());
            newField = getFieldById(newFieldId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return newField;
    }

    @Override
    public boolean updateField(Field field) {
        String sql = "UPDATE fields SET name = ?, description = ?, location = ?, latitude = ?, longitude = ?, images = CAST(? AS json), status = ?, maintenance_logs = CAST(? AS json) WHERE field_id = ?";
        try {
            int rowsAffected = jdbcTemplate.update(sql, field.getName(), field.getDescription(), field.getLocation(),
                    field.getLatitude(), field.getLongitude(), field.getImages(),
                    field.getStatus(), field.getMaintenanceLogs(), field.getFieldId());
            return rowsAffected > 0;
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    @Override
    public boolean deleteField(int fieldId) {
        String sql = "DELETE FROM fields WHERE field_id = ?";
        try {
            int rowsAffected = jdbcTemplate.update(sql, fieldId);
            return rowsAffected > 0;
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    private Field mapRowToField(SqlRowSet rs) {
        Field field = new Field();
        field.setFieldId(rs.getInt("field_id"));
        field.setName(rs.getString("name"));
        field.setDescription(rs.getString("description"));
        field.setLocation(rs.getString("location"));
        field.setLatitude(rs.getBigDecimal("latitude"));
        field.setLongitude(rs.getBigDecimal("longitude"));
        field.setImages(rs.getString("images"));
        field.setStatus(rs.getString("status"));
        field.setMaintenanceLogs(rs.getString("maintenance_logs"));
        field.setCreatedAt(rs.getTimestamp("created_at"));
        return field;
    }
}
