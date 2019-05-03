package com.example.guitareartrainning;

import android.content.Intent;
import android.content.res.Resources;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static com.example.guitareartrainning.ResourceHelper.getStringArray;

public class SSRActivity extends AppCompatActivity {

    private String[] mChords;
    private ArrayList<String> mSelections;
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
    private ChordsPlayer mChordsPlayer;
    //    private CountDownTimer mCountDownTimer;
    private int mScore = 0;

    private static final int PLAY_NUM = 5;

    public static final String STAGE_INFO = "stage info";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ssr);
        // Get intent from MainActivity
        Intent intent = getIntent();
        if(intent != null) {
            mStageIndex = (int) intent.getIntExtra(MainActivity.STAGE_INFO, -1);
        }
        findViewElementsById();
        boolean isButtonEnabled = false;
        setUp();
        if(savedInstanceState != null){
            mUserSelections = savedInstanceState.getStringArrayList("user_selections");
            mPlayIndexList = savedInstanceState.getIntegerArrayList("play_index_list");
            mPlayCount = savedInstanceState.getInt("play_count");
            mPlayNumTextView.setText(savedInstanceState.getString("play_num_text"));
            mPlayNumTextView.setVisibility(savedInstanceState.getInt("play_num_textview_visibility"));
            mScoreTextView.setText(savedInstanceState.getString("score_text"));
            mScoreTextView.setVisibility(savedInstanceState.getInt("score_textview_visibility"));
            mHistoryBestTextView.setVisibility(savedInstanceState.getInt("history_best_textview_visibility"));
            mChordsPlayer = savedInstanceState.getParcelable("play_chords");
            mPlayImageView.setEnabled(savedInstanceState.getBoolean("play_imageview_enable"));
            isButtonEnabled = savedInstanceState.getBoolean("selection_button_enable");
            mPromotionMakeChoiceTextView.setText(savedInstanceState.getString("promotion"));
        } else{
            Toast.makeText(this, "Each Chord Will be played 3 times.", Toast.LENGTH_SHORT)
                    .show();
        }
        mButtonsLayout.setColumnCount(3);
        setAllButtonsEnabled(isButtonEnabled);
    }

    // Create selection item buttons
    private void createSelectionButtons(){
        mButtons = new ArrayList<>();
        for(int i = 0; i< mSelections.size(); ++i){
            final Button button = new Button(this);
            mButtons.add(button);
            button.setText(mSelections.get(i));
            mButtonsLayout.addView(button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPlayImageView.setEnabled(true);
                    setAllButtonsEnabled(false);
                    if(!mChordsPlayer.isCancelled())
                        mChordsPlayer.stopAndCancel();
                    mPromotionMakeChoiceTextView.setText("Tap to play next chord");
                    mUserSelections.add(button.getText().toString());
                    if(button.getText().toString().equals(mChords[mPlayIndexList.get(mPlayCount)])){
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
            button.setAllCaps(false);
        }
    }

    // Find View Elements By ID, which will be used in onCreate() method
    private void findViewElementsById(){
        mPromotionMakeChoiceTextView = findViewById(R.id.promote_make_choice_textview_ssr);
        mButtonsLayout = findViewById(R.id.buttons_layout_ssr);
        mPlayImageView = findViewById(R.id.play_imageview_ssr);
        mPlayNumTextView = findViewById(R.id.play_num_textview_ssr);
        mScoreTextView = findViewById(R.id.score_textview_ssr);
        mHistoryBestTextView = findViewById(R.id.history_best_textview_ssr);
        mRetryFAB = findViewById(R.id.retry_floatingActionButton_ssr);
    }

    private void setUp() {
        Resources res = getResources();
        mChords = getStringArray(this, "stage" + mStageIndex + "_ssr_chords");
        mSelections = new ArrayList<>(Arrays.asList(mChords));
        // Set random number list for chords to play
        mPlayIndexList = new ArrayList<>();
        Random random = new Random();
        for(int i=0; i<PLAY_NUM; ++i)
            mPlayIndexList.add(random.nextInt(mChords.length));
        mUserSelections = new ArrayList<>();
        createSelectionButtons();
        mScoreTextView.setVisibility(View.INVISIBLE);
        mHistoryBestTextView.setVisibility(View.INVISIBLE);
        mPromotionMakeChoiceTextView.setText("Tap to play next chord");
    }

    // When hit the play button, call this method to play chord
    public void playChords(View view) {
        mPlayImageView.setEnabled(false);
        setAllButtonsEnabled(true);
        if(mPlayCount < PLAY_NUM){
            mPlayNumTextView.setText("# " + (mPlayCount+1));
            ArrayList<Integer> chordsAudios  = new ArrayList<>();
            chordsAudios.add(GuitarChords.getChordAudioId(mChords[mPlayIndexList.get(mPlayCount)]));
            ArrayList<Integer> playTimesList = new ArrayList<>();
            playTimesList.add(3);

            mChordsPlayer = new ChordsPlayer(this, playTimesList, mPromotionMakeChoiceTextView);
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
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Variables should be saved

        outState.putStringArray("chords", mChords);
        outState.putStringArrayList("selections", mSelections);
        outState.putStringArrayList("user_selections", mUserSelections);
        outState.putIntegerArrayList("play_index_list", mPlayIndexList);
        outState.putInt("play_count", mPlayCount);
        outState.putString("play_num_text", mPlayNumTextView.getText().toString());
        outState.putInt("play_num_textview_visibility", mPlayNumTextView.getVisibility());
        outState.putString("score_text", mScoreTextView.getText().toString());
        outState.putInt("score_textview_visibility", mScoreTextView.getVisibility());
        outState.putInt("history_best_textview_visibility", mHistoryBestTextView.getVisibility());
        outState.putParcelable("play_chords", mChordsPlayer);
        outState.putBoolean("play_imageview_enable", mPlayImageView.isEnabled());
        outState.putBoolean("selection_button_enable", mButtons.get(0).isEnabled());
        outState.putString("promotion", mPromotionMakeChoiceTextView.getText().toString());
        outState.putInt("retry_fab_visivility", mRetryFAB.getVisibility());
    }

    // Retry this ear training
    public void retry(View view) {
        finish();
        startActivity(getIntent());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mChordsPlayer != null){
            mChordsPlayer.stopAndCancel();
        }
    }
}
