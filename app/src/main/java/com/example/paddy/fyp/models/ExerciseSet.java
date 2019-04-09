package com.example.paddy.fyp.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


@Entity
public class ExerciseSet {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private int id;

    @ColumnInfo(name = "exerciseName")
    private String name;

    @ColumnInfo(name = "workout_id")
    private int workoutID;

    @ColumnInfo(name = "parameters")
    private String parameters;

    @ColumnInfo(name = "setNo")
    private String number;

    @ColumnInfo(name = "weight")
    private int weight;

    @ColumnInfo(name = "reps")
    private int reps;

    @ColumnInfo(name = "volume")
    private int volume;

    @ColumnInfo(name = "1RM")
    private int onerepmax;


    public ExerciseSet(int id, String name, int workoutID, String parameters, String number, int weight, int reps, int volume, int onerepmax) {
        this.id = id;
        this.name = name;
        this.workoutID = workoutID;
        this.parameters = parameters;
        this.number = number;
        this.weight = weight;
        this.reps = reps;
        this.volume = volume;
        this.onerepmax = onerepmax;
    }

    @Ignore
    public ExerciseSet() {
    }

    public int getWorkoutID() {
        return workoutID;
    }

    public void setWorkoutID(int workoutID) {
        this.workoutID = workoutID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) { this.name = name; }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getWeight() { return weight; }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getOnerepmax() {
        return onerepmax;
    }

    public void setOnerepmax(int onerepmax) {
        this.onerepmax = onerepmax;
    }

    @Override
    public String toString() {
        return "ExerciseSet{" +
                "id=" + id +
                ", workoutID=" + workoutID +
                ", name='" + name + '\'' +
                ", parameters='" + parameters + '\'' +
                ", number='" + number + '\'' +
                ", weight=" + weight +
                ", reps=" + reps +
                ", volume=" + volume +
                ", onerepmax=" + onerepmax +
                '}';
    }
}
