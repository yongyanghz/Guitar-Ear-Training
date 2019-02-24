package com.example.guitareartrainning;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.support.design.widget.Snackbar;

import java.util.ArrayList;

public class SoundEffect {

    private static SoundPool soundPool;

    private ArrayList<Integer> mSoundIds;
    private ArrayList<Integer> mPlayTimesList;

    private int mCount = 0;
    private ArrayList<Boolean> mIsLoadedList;


    static {
        // Create a SoundPool instance
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setMaxStreams(1)
                    .setAudioAttributes(audioAttributes)
                    .build();
        } else {
            soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        }
    }

    SoundEffect(Context context, ArrayList<Integer> sounds, ArrayList<Integer> playTimesList){

        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                mIsLoadedList.set(mCount, true);
                mCount++;
            }
        });

        mPlayTimesList = playTimesList;
        mSoundIds = new ArrayList<>();
        mIsLoadedList = new ArrayList<>();

        for(int i=0; i<sounds.size(); ++i){
            mIsLoadedList.add(false);
            mSoundIds.add(soundPool.load(context, sounds.get(i), 0));
        }



    }


    public void playSounds(){
        for(int i = 0; i<1;++i){
            while(!mIsLoadedList.get(i)){}
            soundPool.play(mSoundIds.get(i), 1,1,0, mPlayTimesList.get(i), 1);
        }
    }


    public static  void releaseSoundPool(){
        soundPool.release();
    }

}
