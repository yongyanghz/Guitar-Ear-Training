package com.example.guitareartrainning;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;

import java.io.IOException;
import java.util.ArrayList;

public class SoundPlay implements Runnable{

    private static MediaPlayer mMediaPlayer;

    private ArrayList<Integer> mSoundIds;
    private ArrayList<Integer> mPlayTimesList;

    private int mCount = 1;
    private Context mContext;
    private boolean mIsFinished;
    private ArrayList<Boolean> mIsLoadedList;


    static {
        // Create a MediaPlayer instance
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

    }

    // Play sound file in sequence in the ArrayList
    SoundPlay(Context context, ArrayList<Integer> sounds, ArrayList<Integer> playTimesList){
        mContext = context;
        mIsFinished = true;
        mSoundIds = sounds;
        mPlayTimesList = playTimesList;
    }


    private void playSound(Integer soundId, final Integer times){
        mMediaPlayer.reset();
        Uri path = Uri.parse("android.resource://com.example.guitareartrainning/"
                + soundId);
        try {
            mMediaPlayer.setDataSource(mContext, path);
            mMediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if(mCount < times){
                    mCount++;
                    mp.seekTo(1);
                    mp.start();
                } else {
                  mp.stop();
                  mIsFinished = true;
                  mCount = 1;
                }
            }
        });
        mMediaPlayer.start();
    }

    public static void stop(){
        if(mMediaPlayer.isPlaying())
            mMediaPlayer.stop();
    }

    public static boolean isPlaying(){
        return mMediaPlayer.isPlaying();
    }

    @Override
    public void run() {
        for(int i=0; i<mSoundIds.size(); ++i) {
            while (mMediaPlayer.isPlaying() || !mIsFinished) {} // Wait for the last audio to finish
            mIsFinished = false;
            playSound(mSoundIds.get(i), mPlayTimesList.get(i));
        }
    }
}
