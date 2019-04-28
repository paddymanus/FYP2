package com.example.paddy.fyp.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Bodyweight {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "weight")
    private float weight;

    @ColumnInfo(name = "timestamp")
    private String timestamp;

    public Bodyweight(float weight, String timestamp) {
        this.weight = weight;
        this.timestamp = timestamp;
    }

    @Ignore
    public Bodyweight() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Bodyweight{" +
                "id=" + id +
                ", weight=" + weight +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
