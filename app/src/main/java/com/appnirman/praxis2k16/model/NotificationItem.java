package com.appnirman.praxis2k16.model;

/**
 * Created by santy on 28-08-2016.
 */
public class NotificationItem {
    private String title;
    private String thumbnail;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getTitle() {
        return title;
    }
}
