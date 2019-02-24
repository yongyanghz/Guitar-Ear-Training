package com.example.guitareartrainning;

import android.content.Intent;
import android.content.res.Resources;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.support.v7.widget.GridLayout;
import android.widget.ImageView;
import android.support.design.widget.Snackbar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class CQRActivity extends AppCompatActivity {

    private String[] mChords;
    private ArrayList<String> mSelection;
    private ArrayList<Integer> mPlayIndexList;
    private int mStageIndex;
    private ImageView mPlayImageView;
    private GridLayout mButtonsLayout;
    private ArrayList<Button> mButtons;
    private TextView mPromotionMakeChoiceTextView;
    private TextView mPlayNumTextView;
    private ArrayList<String> mUserSelections;
    private int mPlayCount = 0;
    private PlayChords mPlayChords;
    private CountDownTimer mCountDownTimer;

    private static final int PLAY_NUM = 5;

    public static final String STAGE_INFO = "stage info";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cqr);
        // Get intent from MainActivity
        Intent intent = getIntent();
        if(intent != null) {
            mStageIndex = (int) intent.getIntExtra(MainActivity.STAGE_INFO, -1);
        }
        mPromotionMakeChoiceTextView = findViewById(R.id.promote_make_choice_textview);
        mButtonsLayout = findViewById(R.id.buttons_layout);
        mPlayImageView = findViewById(R.id.begin_imageview);
        mPlayNumTextView = findViewById(R.id.play_num_textview);
        setUp();
        mButtons = new ArrayList<>();
        for(int i = 0; i< mSelection.size(); ++i){
            final Button button = new Button(this);
            mButtons.add(button);
            button.setText(mSelection.get(i));
            mButtonsLayout.addView(button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPlayImageView.setEnabled(true);
                    setAllButtonsEnabled(false);
                    if(!mPlayChords.isCancelled())
                        mPlayChords.cancel(true);
                    mCountDownTimer.cancel();
                    mPromotionMakeChoiceTextView.setText("Next Chord");
                    mUserSelections.add(button.getText().toString());
                    if(GuitarChords.isCorrectType(
                            button.getText().toString(), mChords[mPlayIndexList.get(mPlayCount)])){
                        Snackbar.make(v, "Correct! " + "The chord is "+ mChords[mPlayIndexList.get(mPlayCount)], Snackbar.LENGTH_SHORT)
                                .show();
                    } else{
                        Snackbar.make(v, "Whoops! " + "The chord is "+ mChords[mPlayIndexList.get(mPlayCount)], Snackbar.LENGTH_SHORT)
                                .show();
                    }
                    mPlayCount++;
                }
            });
        }

        Toast.makeText(this, "Each Chord Will be played 3 times.", Toast.LENGTH_SHORT)
                .show();
    }

    private void setUp() {
        Resources res = getResources();
        switch (mStageIndex){
            case 2:
                mSelection = new ArrayList<>(
                        Arrays.asList("Major", "Minor"));
                mChords = res.getStringArray(R.array.stage2_cqr_chords);
                break;
            case 3:
                mSelection = new ArrayList<>(
                        Arrays.asList("Major", "Minor"));
                mChords = res.getStringArray(R.array.stage3_cqr_chords);
                break;
            case 4:
                mSelection = new ArrayList<>(
                        Arrays.asList("D", "Dm", "D7"));
                mChords = res.getStringArray(R.array.stage4_cqr_chords);
                break;
            case 5:
                mSelection = new ArrayList<>(
                        Arrays.asList("Major", "Minor", "7"));
                mChords = res.getStringArray(R.array.stage5_cqr_chords);
                break;
            case 6:
                mSelection = new ArrayList<>(
                        Arrays.asList("Major", "Minor", "7"));
                mChords = res.getStringArray(R.array.stage6_cqr_chords);
                break;
            case 7:
                mSelection = new ArrayList<>(
                        Arrays.asList("Major", "Minor", "7"));
                mChords = res.getStringArray(R.array.stage7_cqr_chords);
                break;
            case 8:
                mSelection = new ArrayList<>(
                        Arrays.asList("Major", "Minor", "7", "Maj7", "Power"));
                mChords = res.getStringArray(R.array.stage8_cqr_chords);
                break;
//            case 9:
//                mSelection = new ArrayList<>(
//                        Arrays.asList("E", "A", "D", "Em", "Am", "Dm"));
//                break;
            default: break;
        }
        // Set random number list for chords to play
        mPlayIndexList = new ArrayList<>();
        Random random = new Random();
        for(int i=0; i<PLAY_NUM; ++i)
            mPlayIndexList.add(random.nextInt(mChords.length));
        mUserSelections = new ArrayList<>();
    }

    public void playChords(View view) {
        mPlayImageView.setEnabled(false);
        setAllButtonsEnabled(true);
        if(mPlayCount < PLAY_NUM){
            mPlayNumTextView.setText("# " + (mPlayCount+1));
            mCountDownTimer = new CountDownTimer(7000, 2000) {
                public void onTick(long millisUntilFinished) {
                    mPromotionMakeChoiceTextView.setText("play times remaining: " + millisUntilFinished / 2000);
                }

                public void onFinish() {
                    mPromotionMakeChoiceTextView.setText("Please select a chord.");
                }
            }.start();
            ArrayList<Integer> chordsAudios  = new ArrayList<>();
            chordsAudios.add(GuitarChords.getChordAudioId(mChords[mPlayIndexList.get(mPlayCount)]));
            ArrayList<Integer> playTimesList = new ArrayList<>();
            playTimesList.add(3);

            mPlayChords= new PlayChords(this, playTimesList, mPromotionMakeChoiceTextView);
            mPlayChords.execute(chordsAudios);
            // Wait for three seconds for user to make choice if not make

        } else{
            Snackbar.make(view, "Congratulations! You have finished this trainning!", Snackbar.LENGTH_SHORT)
                    .show();
            mPlayImageView.setEnabled(true);
            setAllButtonsEnabled(false);
        }

    }

    private void setAllButtonsEnabled(Boolean isEnable){
        for(Button button : mButtons){
            button.setEnabled(isEnable);
        }
    }

}
