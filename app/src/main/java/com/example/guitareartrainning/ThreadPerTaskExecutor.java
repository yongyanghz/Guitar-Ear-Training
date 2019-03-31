package com.example.guitareartrainning;

import android.content.Context;
import android.os.Parcelable;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.Executor;


public class ThreadPerTaskExecutor implements Executor {

    private static Parcelable.Creator CREATOR;
    private ArrayList<Integer> mPlayTimesList;
    private Context mContext;
    private WeakReference<TextView> mPromotionTextView;
    private  SoundPlay mSoundPlay;

    // Constructor
    ThreadPerTaskExecutor(){
    }

    @Override
    public void execute(Runnable command) {
        new Thread(command).start();
    }

    public void cancel(){

    }
}
