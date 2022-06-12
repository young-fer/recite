package com.example.recite.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import com.example.recite.ReciteActivity;
import com.example.recite.http.MyHttp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class PronunciationPlayerService extends Service {

    private MediaPlayer mediaPlayer;
    private PlayPronunciationBinder mBinder = new PlayPronunciationBinder();

    public PronunciationPlayerService() {

    }


    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = new MediaPlayer();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }

    public class PlayPronunciationBinder extends Binder {
        public void play(String wordHead) {
            try {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                mediaPlayer=new MediaPlayer();
                String url = "http://dict.youdao.com/dictvoice?audio=" + wordHead; // your URL here
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setDataSource(url);
                mediaPlayer.prepare(); // might take long! (for buffering, etc)
                mediaPlayer.start();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}