package com.example.paddy.fyp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class StatsHome implements Parcelable {

    private String title;

    public StatsHome(String title) {
        this.title = title;
    }

    public StatsHome() {
    }

    protected StatsHome(Parcel in) {
        title = in.readString();
    }

    public static final Creator<StatsHome> CREATOR = new Creator<StatsHome>() {
        @Override
        public StatsHome createFromParcel(Parcel in) {
            return new StatsHome(in);
        }

        @Override
        public StatsHome[] newArray(int size) {
            return new StatsHome[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "StatsHome{" +
                "title='" + title + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
    }
}
