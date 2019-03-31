/*
 * ChordsPlayer is extended from AsyncTask to do the play chords audio task
 * in background.
 */


package com.example.guitareartrainning;

import android.content.Context;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class ChordsPlayer extends AsyncTask<ArrayList<Integer>, Void, String> implements Parcelable {

    private static Creator CREATOR;
    private ArrayList<Integer> mPlayTimesList;
    private Context mContext;
    private WeakReference<TextView> mPromotionTextView;
    private  SoundPlay mSoundPlay;
    // Constructor
    ChordsPlayer(Context context, ArrayList<Integer> playTimesList, TextView tv){
        mContext = context;
        mPlayTimesList = playTimesList;
        mPromotionTextView = new WeakReference<>(tv);
    }

    @Override
    protected String doInBackground(ArrayList<Integer>... chords) {

        mSoundPlay = new SoundPlay(mContext, chords[0], mPlayTimesList);
        mSoundPlay.run();
        return "Please select a chord";
    }

    public void stopAndCancel(){
        mSoundPlay.stop();
        cancel(true);
    }



    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        mPromotionTextView.get().setText(result);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
