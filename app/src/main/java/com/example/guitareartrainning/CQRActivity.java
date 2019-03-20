package com.example.guitareartrainning;

import android.content.Intent;
import android.content.res.Resources;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    private TextView mScoreTextView;
    private TextView mPlayNumTextView;
    private TextView mHistoryBestTextView;
    private FloatingActionButton mRetryFAB;

    private ArrayList<String> mUserSelections;
    private int mPlayCount = 0;
    private chordsPlayer mChordsPlayer;
//    private CountDownTimer mCountDownTimer;
    private int mScore = 0;

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
        findViewElementsById();
        boolean isEnabledButton = false;
        if(savedInstanceState != null){
            mChords = savedInstanceState.getStringArray("chords");
            mSelection = savedInstanceState.getStringArrayList("selections");
            mUserSelections = savedInstanceState.getStringArrayList("user_selections");
            mPlayIndexList = savedInstanceState.getIntegerArrayList("play_index_list");
            mPlayCount = savedInstanceState.getInt("play_count");
            mScoreTextView.setText(savedInstanceState.getString("score_text"));
            mScoreTextView.setVisibility(savedInstanceState.getInt("score_textview_visibility"));
            mHistoryBestTextView.setVisibility(savedInstanceState.getInt("history_best_textview_visibility"));
            mRetryFAB.setVisibility(savedInstanceState.getInt("retry_fab_visivility"));
            if(mScoreTextView.getVisibility() != View.VISIBLE)
                mPlayNumTextView.setText("# " + (mPlayCount+1));
            mChordsPlayer = savedInstanceState.getParcelable("play_chords");
            mPlayImageView.setEnabled(savedInstanceState.getBoolean("play_imageview_enable"));
            isEnabledButton = savedInstanceState.getBoolean("selection_button_enable");
            mPromotionMakeChoiceTextView.setText(savedInstanceState.getString("promotion"));
//            mCountDownTimer = savedInstanceState.getParcelable("count_down_timer");
        } else{
            setUp();
            mRetryFAB.setVisibility(View.INVISIBLE);
            mScoreTextView.setVisibility(View.INVISIBLE);
            mHistoryBestTextView.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "Each Chord Will be played 3 times.", Toast.LENGTH_SHORT)
                    .show();
            mPromotionMakeChoiceTextView.setText("Tap to play next chord");
        }
        createSelectionButtons();
        mButtonsLayout.setColumnCount(3);
        setAllButtonsEnabled(isEnabledButton);


    }

    // Create selection item buttons
    private void createSelectionButtons(){
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
                    if(!mChordsPlayer.isCancelled())
                        mChordsPlayer.cancel(true);
//                    mCountDownTimer.cancel();
                    mPromotionMakeChoiceTextView.setText("Tap to play next chord");
                    mUserSelections.add(button.getText().toString());
                    if(GuitarChords.isCorrectType(
                            button.getText().toString(), mChords[mPlayIndexList.get(mPlayCount)])){
                        mScore++;
                        Snackbar.make(v, "Correct! " + "The chord is "+ mChords[mPlayIndexList.get(mPlayCount)], Snackbar.LENGTH_SHORT)
                                .show();
                    } else{
                        Snackbar.make(v, "Whoops! " + "The chord is "+ mChords[mPlayIndexList.get(mPlayCount)], Snackbar.LENGTH_SHORT)
                                .show();
                    }
                    mPlayCount++;
                    if(mPlayCount == PLAY_NUM)
                        showTrainningResult(v);
                }
            });
        }
    }

    // Find View Elements By ID, which will be used in onCreate() method
    private void findViewElementsById(){
        mPromotionMakeChoiceTextView = findViewById(R.id.promote_make_choice_textview);
        mButtonsLayout = findViewById(R.id.buttons_layout);
        mPlayImageView = findViewById(R.id.begin_imageview);
        mPlayNumTextView = findViewById(R.id.play_num_textview);
        mScoreTextView = findViewById(R.id.score_textview);
        mHistoryBestTextView = findViewById(R.id.history_best_textview);
        mRetryFAB = findViewById(R.id.retry_floatingActionButton);
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
                        Arrays.asList("Major", "Minor", "7"));
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

    // When hit the play button, call this method to play chord
    public void playChords(View view) {
        mPlayImageView.setEnabled(false);
        setAllButtonsEnabled(true);
        if(mPlayCount < PLAY_NUM){
            mPlayNumTextView.setText("# " + (mPlayCount+1));
//            mCountDownTimer = new CountDownTimer(7000, 2000) {
//                public void onTick(long millisUntilFinished) {
//                    mPromotionMakeChoiceTextView.setText("play times remaining: " + millisUntilFinished / 2000);
//                }
//
//                public void onFinish() {
//                    mPromotionMakeChoiceTextView.setText("Please select a chord");
//                }
//            }.start();
            ArrayList<Integer> chordsAudios  = new ArrayList<>();
            chordsAudios.add(GuitarChords.getChordAudioId(mChords[mPlayIndexList.get(mPlayCount)]));
            ArrayList<Integer> playTimesList = new ArrayList<>();
            playTimesList.add(3);

            mChordsPlayer = new chordsPlayer(this, playTimesList, mPromotionMakeChoiceTextView);
            mChordsPlayer.execute(chordsAudios);
        } else{
            showTrainningResult(view);
        }
    }

    // Set All Buttons of selections Enabled Or Disabled
    private void setAllButtonsEnabled(Boolean isEnable){
        for(Button button : mButtons){
            button.setEnabled(isEnable);
        }
    }

    // Show Trainning Result, score and history best
    private void showTrainningResult(View view){

        Snackbar.make(view, "Congratulations! You have finished this trainning!", Snackbar.LENGTH_SHORT)
                .show();
        mScoreTextView.setVisibility(View.VISIBLE);
        mScoreTextView.setText("Your Score: " + mScore + "/" + PLAY_NUM);
        mHistoryBestTextView.setVisibility(View.VISIBLE);
        mHistoryBestTextView.setText("History Best: ");
        mPlayNumTextView.setVisibility(View.INVISIBLE);
        mPromotionMakeChoiceTextView.setText("Well Done!");

        mPlayImageView.setEnabled(true);
        setAllButtonsEnabled(false);
        mRetryFAB.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Variables should be saved

//        private String[] mChords;
//        private ArrayList<String> mSelection;
//        private ArrayList<Integer> mPlayIndexList;
//        private ArrayList<String> mUserSelections;
//        private int mPlayCount = 0;

        outState.putStringArray("chords", mChords);
        outState.putStringArrayList("selections", mSelection);
        outState.putStringArrayList("user_selections", mUserSelections);
        outState.putIntegerArrayList("play_index_list", mPlayIndexList);
        outState.putInt("play_count", mPlayCount);
        outState.putString("score_text", mScoreTextView.getText().toString());
        outState.putInt("score_textview_visibility", mScoreTextView.getVisibility());
        outState.putInt("history_best_textview_visibility", mHistoryBestTextView.getVisibility());
        outState.putParcelable("play_chords", mChordsPlayer);
        outState.putBoolean("play_imageview_enable", mPlayImageView.isEnabled());
        outState.putBoolean("selection_button_enable", mButtons.get(0).isEnabled());
        outState.putString("promotion", mPromotionMakeChoiceTextView.getText().toString());
        outState.putInt("retry_fab_visivility", mRetryFAB.getVisibility());
//        outState.putParcelable("count_down_timer", (Parcelable) mCountDownTimer);
    }

    // Retry this ear training
    public void retry(View view) {
        finish();
        startActivity(getIntent());
    }
}
