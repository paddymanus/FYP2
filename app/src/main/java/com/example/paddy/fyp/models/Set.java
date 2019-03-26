package com.example.paddy.fyp.models;

import java.util.ArrayList;

public class Set {

    private ArrayList<Set> mSets = new ArrayList<>();

    private int _setNumber;
    private int _setWeight;
    private int _setReps;

    public Set(int _setNumber, int _setWeight, int _setReps) {
        this._setNumber = _setNumber;
        this._setWeight = _setWeight;
        this._setReps = _setReps;
    }

    public Set() {
    }


    public ArrayList<Set> getExerciseSets() {
        return mSets;
    }

    public void addExerciseSet(Set es) {
        mSets.add(es);
    }

    public int get_setNumber() {
        return _setNumber;
    }

    public void set_setNumber(int _setNumber) {
        this._setNumber = _setNumber;
    }

    public int get_setWeight() {
        return _setWeight;
    }

    public void set_setWeight(int _setWeight) {
        this._setWeight = _setWeight;
    }

    public int get_setReps() {
        return _setReps;
    }

    public void set_setReps(int _setReps) {
        this._setReps = _setReps;
    }

    @Override
    public String toString() {
        return "Set{" +
                "_setNumber=" + _setNumber +
                ", _setWeight=" + _setWeight +
                ", _setReps=" + _setReps +
                '}';
    }
}
