package com.example.paddy.fyp.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Measurement {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "weight")
    private float measurement;

    @ColumnInfo(name = "bodypart")
    private String bodypart;

    @ColumnInfo(name = "timestamp")
    private String timestamp;

    public Measurement(float measurement, String bodypart, String timestamp) {
        this.measurement = measurement;
        this.bodypart = bodypart;
        this.timestamp = timestamp;
    }

    @Ignore
    public Measurement() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getMeasurement() {
        return measurement;
    }

    public void setMeasurement(float measurement) {
        this.measurement = measurement;
    }

    public String getBodypart() {
        return bodypart;
    }

    public void setBodypart(String bodypart) {
        this.bodypart = bodypart;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }


    @Override
    public String toString() {
        return "Measurement{" +
                "id=" + id +
                ", measurement=" + measurement +
                ", bodypart='" + bodypart + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
