package com.example.gamematch3.util;

import android.content.Context;
import android.media.MediaPlayer;


import com.example.gamematch3.R;

import java.io.IOException;

public class Sound {
    private final MediaPlayer fonPlay;
    private final MediaPlayer loadingPlay;
    private final Context context;

    public Sound(Context context) {
        this.context = context;
        this.fonPlay = MediaPlayer.create(context, R.raw.background);
        this.loadingPlay = MediaPlayer.create(context, R.raw.loading);
    }

    public void GroundStart() {
        fonPlay.start();
        fonPlay.setLooping(true);
    }

    public void GroundStop() {
        fonPlay.stop();
        try {
            fonPlay.prepare();
            fonPlay.seekTo(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void LoadStart() {
        loadingPlay.start();
    }

    public void LoadStop() {
        loadingPlay.stop();
        try {
            loadingPlay.prepare();
            loadingPlay.seekTo(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void FallCand() {
        MediaPlayer mPlayer;
        mPlayer= MediaPlayer.create(context, R.raw.fall_cand);
        mPlayer.start();
    }

    public void FallCandStart() {
        MediaPlayer mPlayer;
        mPlayer= MediaPlayer.create(context, R.raw.fall_cand_start);
        mPlayer.start();
    }

    public void Alert() {
        MediaPlayer mPlayer;
        mPlayer= MediaPlayer.create(context, R.raw.alert);
        mPlayer.start();
    }

    public void Fail() {
        MediaPlayer mPlayer;
        mPlayer= MediaPlayer.create(context, R.raw.failure);
        mPlayer.start();
    }

    public void Moving() {
        MediaPlayer mPlayer;
        mPlayer= MediaPlayer.create(context, R.raw.moving);
        mPlayer.start();
    }

    public void Slide() {
        MediaPlayer mPlayer;
        mPlayer= MediaPlayer.create(context, R.raw.moving_back);
        mPlayer.start();
    }

    public void Victory() {
        MediaPlayer mPlayer;
        mPlayer= MediaPlayer.create(context, R.raw.victory);
        mPlayer.start();
    }

    public void Disappearance() {
        MediaPlayer mPlayer;
        mPlayer= MediaPlayer.create(context, R.raw.disappearance);
        mPlayer.start();
    }
}
