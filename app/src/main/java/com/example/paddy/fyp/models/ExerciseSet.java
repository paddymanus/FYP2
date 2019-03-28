package com.example.paddy.fyp.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;


@Entity(tableName = "exerciseSets", foreignKeys = @ForeignKey(entity = LogItem.class,
        parentColumns = "id", childColumns = "workout_id"))
public class ExerciseSet {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "workout_id", index = true)
    private int workoutID;

    @ColumnInfo(name = "exerciseName")
    private String name;

    @ColumnInfo(name = "parameters")
    private String parameters;

    @ColumnInfo(name = "setNo")
    private String number;

    @ColumnInfo(name = "weight")
    private int weight;

    @ColumnInfo(name = "reps")
    private int reps;


//  private ArrayList<ExerciseSet> mExerciseSets = new ArrayList<>();
    //ExexerciseSet set("name", 0, 0, 0);


    public ExerciseSet(int workoutID, String name, String parameters, String number, int weight, int reps) {
        this.name = name;
        this.workoutID = workoutID;
        this.parameters = parameters;
        this.number = number;
        this.weight = weight;
        this.reps = reps;
    }

    @Ignore
    public ExerciseSet() {
    }

//    public void setExerciseSets(ArrayList<ExerciseSet> exerciseSets) {
//        mExerciseSets = exerciseSets;
//    }


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


    @Override
    public String toString() {
        return "ExerciseSet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parameters='" + parameters + '\'' +
                ", number='" + number + '\'' +
                ", weight=" + weight +
                ", reps=" + reps +
                '}';
    }
}
