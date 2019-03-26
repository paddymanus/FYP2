package com.example.paddy.fyp.models;

import android.os.Parcel;
import android.os.Parcelable;


public class LogItem implements Parcelable {

    private String title;
    private String content;
    private String timestamp;

    public LogItem(String title, String content, String timestamp) {
        this.title = title;
        this.content = content;
        this.timestamp = timestamp;
    }

    public LogItem() {
    }

    protected LogItem(Parcel in) {
        title = in.readString();
        content = in.readString();
        timestamp = in.readString();
    }

    public static final Creator<LogItem> CREATOR = new Creator<LogItem>() {
        @Override
        public LogItem createFromParcel(Parcel in) {
            return new LogItem(in);
        }

        @Override
        public LogItem[] newArray(int size) {
            return new LogItem[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "LogItem{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(timestamp);
    }
}
