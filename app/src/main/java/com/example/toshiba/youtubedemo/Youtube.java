package com.example.toshiba.youtubedemo;

import java.util.List;

/**
 * Created by Toshiba on 17/08/2017.
 */

class Youtube {
    private List<VideoClip> youtubes;

    List<VideoClip> getClips() {
        return youtubes;
    }

    public void setClips(List<VideoClip> youtubes) {
        this.youtubes = youtubes;
    }
}
