package edu.cnm.deepdive.hexed0x29a.activities;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;
import android.media.MediaPlayer.OnErrorListener;

import edu.cnm.deepdive.hexed0x29a.R;

// https://www.codeproject.com/Articles/258176/Adding-Background-Music-to-Android-App

/**
 * Created by natedaag on 8/30/17.
 */

public class MusicService extends Service implements MediaPlayer.OnErrorListener {
    public static final String TRACK_ID_KEY = "edu.cnm.deepdive.hexed0x29a.trackId";
    private final IBinder mBinder = new ServiceBinder();
    MediaPlayer mPlayer = null;
    private int length = 0;

    public MusicService() {
    }

    public class ServiceBinder extends Binder {
        MusicService getService() {
            return MusicService.this;
        }
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // created raw directory, placed mp3 into it.
        int trackId = intent.getIntExtra(TRACK_ID_KEY, R.raw.title_theme);
        mPlayer = MediaPlayer.create(this, trackId);
        mPlayer.setOnErrorListener(this);

        if (mPlayer != null) {
            mPlayer.setLooping(true);
            mPlayer.setVolume(100, 100);
        }


        mPlayer.setOnErrorListener(new OnErrorListener() {

            public boolean onError(MediaPlayer mp, int what, int
                extra) {

                onError(mPlayer, what, extra);
                return true;
            }
        });
        mPlayer.start();
        return START_STICKY;
    }

    public void pauseMusic() {
        if (mPlayer != null && mPlayer.isPlaying()) {
            mPlayer.pause();
            length = mPlayer.getCurrentPosition();

        }
    }

    public void resumeMusic() {
        if (mPlayer != null && mPlayer.isPlaying() == false) {
            mPlayer.seekTo(length);
            mPlayer.start();
        }
    }

    public void stopMusic() {
        if(mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPlayer != null) {
            try {
                mPlayer.stop();
                mPlayer.release();
            } finally {
                mPlayer = null;
            }
        }
    }

    public boolean onError(MediaPlayer mp, int what, int extra) {

        Toast.makeText(this, "music player failed", Toast.LENGTH_SHORT).show();
        if (mPlayer != null) {
            try {
                mPlayer.stop();
                mPlayer.release();
            } finally {
                mPlayer = null;
            }
        }
        return false;
    }
}
