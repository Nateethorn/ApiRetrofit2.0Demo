package com.example.toshiba.youtubedemo;

public class VideoClip {
    private String id;
    private String title;
    private String subtitle;
    private String avatar_image;
    private String youtube_image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getAvatar_image() {
        return avatar_image;
    }

    public void setAvatar_image(String avatar_image) {
        this.avatar_image = avatar_image;
    }

    public String getYoutube_image() {
        return youtube_image;
    }

    public void setYoutube_image(String youtube_image) {
        this.youtube_image = youtube_image;
    }
}
