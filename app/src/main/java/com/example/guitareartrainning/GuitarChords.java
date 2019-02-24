package com.example.guitareartrainning;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class GuitarChords {

    private static HashMap<String, Integer> mChordsHashTab = new HashMap<>();
    private static MediaPlayer mMediaPlayer;
//    private static int mCount = 0;
    private static int mPosition;
    private static String mPlayingChord;
    private static ArrayList<String> mChords;

    public final static  String MAJOR_CHORD = "Major";
    public final static  String MINOR_CHORD = "Minor";
    public final static  String SEVEN_CHORD = "7";
    public final static  String POWER_CHORD = "Power";
    public final static  String SUS_CHORD = "Sus";
    public final static  String MAJ7_CHORD = "Maj7";


    static {
        mPosition = 0;
        mMediaPlayer = new MediaPlayer();
         mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        // Major Chords
        mChordsHashTab.put("A", R.raw._a);
        mChordsHashTab.put("C", R.raw._c);
        mChordsHashTab.put("D", R.raw._d);
        mChordsHashTab.put("E", R.raw._e);
        mChordsHashTab.put("F", R.raw._f);
        mChordsHashTab.put("G", R.raw._g);
        // Minor Chords
        mChordsHashTab.put("Am", R.raw._amin);
        mChordsHashTab.put("Cm", R.raw._cmin);
        mChordsHashTab.put("Dm", R.raw._dmin);
        mChordsHashTab.put("Em", R.raw._emin);
        // Major 7 Chords
        mChordsHashTab.put("A7", R.raw._a7);
        mChordsHashTab.put("B7", R.raw._b7);
        mChordsHashTab.put("C7", R.raw._c7);
        mChordsHashTab.put("D7", R.raw._d7);
        mChordsHashTab.put("E7", R.raw._e7);
        mChordsHashTab.put("G7", R.raw._g7);
        mChordsHashTab.put("Fmaj7", R.raw._fmaj7);
        // Power Chords root on 6th String
        mChordsHashTab.put("A5_R6", R.raw._a5_root_6th);
        mChordsHashTab.put("A#5_R6", R.raw._asharp5_root_6th);
        mChordsHashTab.put("Ab5_R6", R.raw._ab5_root_6th);
        mChordsHashTab.put("B5_R6", R.raw._b5_root_6th);
        mChordsHashTab.put("C5_R6", R.raw._c5_root_6th);
        mChordsHashTab.put("C#5_R6", R.raw._csharp5_root_6th);
        mChordsHashTab.put("F5_R6", R.raw._f5_root_6th);
        mChordsHashTab.put("G5_R6", R.raw._g5_root_6th);
        // Power Chords root on 5th String
        mChordsHashTab.put("A#5_R5", R.raw._asharp5_root_5th);
        mChordsHashTab.put("C5_R5", R.raw._csharp5_root_5th);
        mChordsHashTab.put("D5_R5", R.raw._d5_root_5th);
        mChordsHashTab.put("D#5_R5", R.raw._dsharp5_root_5th);
        mChordsHashTab.put("F5_R5", R.raw._f5_root_6th);
        mChordsHashTab.put("G5_R5", R.raw._g5_root_5th);
        // Suspend Chords
        mChordsHashTab.put("Asus2", R.raw._asus2);
        mChordsHashTab.put("Asus4", R.raw._asus4);
        mChordsHashTab.put("Dsus2", R.raw._dsus2);
        mChordsHashTab.put("Dsus4", R.raw._dsus4);
        mChordsHashTab.put("Esus4", R.raw._esus4);
        // EASY SLASH CHORDS
        mChordsHashTab.put("D/F#", R.raw._d0_fsharp);
    }

//    public static void playChord(final Context context, final String chord, final int playTimes) {
//        Uri path = Uri.parse("android.resource://com.example.guitareartrainning/"
//                + mChordsHashTab.get(chord));
//        mMediaPlayer.reset();
//        try {
//            mMediaPlayer.setDataSource(context, path);
//            mMediaPlayer.prepare();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        mCount = 1;
//        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mp) {
//                final int maxCount = playTimes;
//                if(mCount < maxCount){
//                    mCount++;
//                    mMediaPlayer.seekTo(0);
//                    mMediaPlayer.start();
//                }
//            }
//        });
//        mMediaPlayer.start();
//    }

//    private static void playChordsRecur(final Context context, final ArrayList<String> chords) {
//        Uri path = Uri.parse("android.resource://com.example.guitareartrainning/"
//                + mChordsHashTab.get(chords.get(mPosition)));
//        mMediaPlayer.reset();
//        try {
//            mMediaPlayer.setDataSource(context, path);
//            mMediaPlayer.prepare();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mp) {
//                mMediaPlayer.stop();
//                if(mPosition < chords.size()){
//                    mPosition++;
//                    playChords(context, chords);  // Recursively play next audio file
//                }
//            }
//        });
//        mMediaPlayer.setLooping(false);
//        mMediaPlayer.start();
//    }


//      public static void stopPlay() {
//          mMediaPlayer.stop();
//      }
//
//
//    public static void playChords(final Context context, final ArrayList<String> chords){
////        mMediaPlayer.stop();
//        mPosition = 0;
//        mPlayingChord = chords.get(0);
//        playChordsRecur(context, chords);
//    }

    // Return a chord type

    public static String getChordType(String chord)
    {
        String res = "";

        if(chord.length() == 1)
            res = MAJOR_CHORD;
        else if(chord.length() == 2){
            if (chord.charAt(1) == 'm')
                res = MINOR_CHORD;
            else if (chord.charAt(1) == '7')
            res = SEVEN_CHORD;
        }
        else if(chord.contains("sus"))
            res = SUS_CHORD;
        else if(chord.contains("maj7"))
            res = MAJ7_CHORD;
        else if(chord.contains("5"))
            res = POWER_CHORD;

        return res;
    }

    public static boolean isCorrectType(String buttonSelection, String realChord){
        boolean res = buttonSelection.equals(getChordType(realChord));
        return res;
    }

    public static boolean isSameChord(String buttonSelection, String realChord){
        boolean res = false;
        if(buttonSelection.equals(realChord))
            res = true;
        else if(realChord.contains("5")){
            if(realChord.contains(buttonSelection))
                res = true;
        }

        return  res;
    }

//    public static String getPlayingChord() {
//        return mPlayingChord;
//    }

    public static Integer getChordAudioId(String chord) {
        return mChordsHashTab.get(chord);
    }
}


