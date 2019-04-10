package com.example.paddy.fyp.models;

public class StatsHome {

    private String title;

    public StatsHome(String title) {
        this.title = title;
    }

    public StatsHome() {
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
