package com.example.paddy.fyp.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

@Entity
public class RoutineExercise implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    @NonNull
    @ColumnInfo(name = "category")
    private String category;

    @ColumnInfo(name = "log_id")
    private int logId;

    public RoutineExercise(String name, String category, int logId) {
        this.name = name;
        this.category = category;
        this.logId = logId;
    }

    @Ignore
    public RoutineExercise() {
    }


    protected RoutineExercise(Parcel in) {
        id = in.readInt();
        name = in.readString();
        category = in.readString();
        logId = in.readInt();
    }

    public static final Creator<RoutineExercise> CREATOR = new Creator<RoutineExercise>() {
        @Override
        public RoutineExercise createFromParcel(Parcel in) {
            return new RoutineExercise(in);
        }

        @Override
        public RoutineExercise[] newArray(int size) {
            return new RoutineExercise[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    @Override
    public String toString() {
        return "Exercise{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", logId='" + logId + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(category);
        dest.writeInt(logId);
    }
}
