package com.example.paddy.fyp.models;

public class StatsOptions {

    private String title;

    public StatsOptions(String title) {
        this.title = title;
    }

    public StatsOptions() {
    }

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
}
