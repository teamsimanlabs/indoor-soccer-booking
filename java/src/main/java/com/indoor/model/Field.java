package com.indoor.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

public class Field {

    private int fieldId;
    private String name;
    private String description;
    private String location;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String images; // JSON representation
    private String status;
    private String maintenanceLogs; // JSON representation
    private Timestamp createdAt;

    public Field() { }

    public Field(int fieldId, String name, String description, String location,
                 BigDecimal latitude, BigDecimal longitude, String images,
                 String status, String maintenanceLogs, Timestamp createdAt) {
        this.fieldId = fieldId;
        this.name = name;
        this.description = description;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.images = images;
        this.status = status;
        this.maintenanceLogs = maintenanceLogs;
        this.createdAt = createdAt;
    }

    public int getFieldId() {
        return fieldId;
    }

    public void setFieldId(int fieldId) {
        this.fieldId = fieldId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMaintenanceLogs() {
        return maintenanceLogs;
    }

    public void setMaintenanceLogs(String maintenanceLogs) {
        this.maintenanceLogs = maintenanceLogs;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Field field = (Field) o;
        return fieldId == field.fieldId &&
                Objects.equals(name, field.name) &&
                Objects.equals(description, field.description) &&
                Objects.equals(location, field.location) &&
                Objects.equals(latitude, field.latitude) &&
                Objects.equals(longitude, field.longitude) &&
                Objects.equals(images, field.images) &&
                Objects.equals(status, field.status) &&
                Objects.equals(maintenanceLogs, field.maintenanceLogs) &&
                Objects.equals(createdAt, field.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fieldId, name, description, location, latitude, longitude, images, status, maintenanceLogs, createdAt);
    }

    @Override
    public String toString() {
        return "Field{" +
                "fieldId=" + fieldId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", images='" + images + '\'' +
                ", status='" + status + '\'' +
                ", maintenanceLogs='" + maintenanceLogs + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
