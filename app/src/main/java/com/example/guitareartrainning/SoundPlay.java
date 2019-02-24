package com.example.guitareartrainning;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;

import java.io.IOException;
import java.util.ArrayList;

public class SoundPlay {

    private static MediaPlayer mediaPlayer;

    private ArrayList<Integer> mSoundIds;
    private ArrayList<Integer> mPlayTimesList;

    private int mCount = 1;
    private Context mContext;
    private boolean mIsFinished = false;
    private ArrayList<Boolean> mIsLoadedList;


    static {
        // Create a MediaPlayer instance
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

    }

    // Play sound file in sequence in the ArrayList
    SoundPlay(Context context, ArrayList<Integer> sounds, ArrayList<Integer> playTimesList){
        mContext = context;
        mIsFinished = true;
        for(int i=0; i<sounds.size(); ++i){
            while(!mIsFinished){}
            mIsFinished = false;
            playSound(sounds.get(i), playTimesList.get(i));
        }
    }

    private void playSound(Integer soundId, final Integer times){
        Uri path = Uri.parse("android.resource://com.example.guitareartrainning/"
                + soundId);
        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(mContext, path);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if(mCount < times){
                    mCount++;
                    mp.seekTo(0);
                    mp.start();
                } else {
                  mp.stop();
                  mIsFinished = true;
                  mCount = 1;
                }
            }
        });
        mediaPlayer.start();
    }

    public void stop(){
        mediaPlayer.stop();
    }

}
