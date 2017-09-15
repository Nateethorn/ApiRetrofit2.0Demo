package com.example.toshiba.youtubedemo;

import java.util.List;


class Youtube {
    private List<VideoClip> youtubes;

    List<VideoClip> getClips() {
        return youtubes;
    }

    public void setClips(List<VideoClip> youtubes) {
        this.youtubes = youtubes;
    }
}
