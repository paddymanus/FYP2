package com.example.paddy.fyp.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity
public class RoutineHome implements Parcelable {

    @PrimaryKey
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    public RoutineHome(String title) {
        this.title = title;
    }

    @Ignore
    public RoutineHome() {
    }

    protected RoutineHome(Parcel in) {
        id = in.readInt();
        title = in.readString();
    }

    public static final Creator<RoutineHome> CREATOR = new Creator<RoutineHome>() {
        @Override
        public RoutineHome createFromParcel(Parcel in) {
            return new RoutineHome(in);
        }

        @Override
        public RoutineHome[] newArray(int size) {
            return new RoutineHome[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "RoutineHome{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
    }
}
