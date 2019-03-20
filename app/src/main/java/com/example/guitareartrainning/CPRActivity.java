package com.example.guitareartrainning;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.guitareartrainning.ResourceHelper.get2DimIntegerArray;
import static com.example.guitareartrainning.ResourceHelper.getStringArray;
import static com.example.guitareartrainning.ResourceHelper.getStringArrayId;

public class CPRActivity extends AppCompatActivity {
    private String[] mChords;
    private ArrayList<Integer> mPlayIndexList;
    private int mStageIndex;
    private ImageView mPlayImageView;
    private ArrayList<Button> mButtons;
    private TextView mPromotionMakeChoiceTextView;
    private TextView mScoreTextView;
    private TextView mPlayNumTextView;
    private TextView mHistoryBestTextView;
    private FloatingActionButton mRetryFAB;
    private List<Spinner> mSpinners;

    private List<List<String> > mCprChords;
    private List<List<Integer>> mCprChordsPlayTimes;
    private List<List<String> > mUserSelections;
    private List<String> mSelections;
    private int mPlayCount = 0;
    private chordsPlayer mChordsPlayer;
    //    private CountDownTimer mCountDownTimer;
    private int mScore = 0;

    private static final int PLAY_NUM = 3;

    public static final String STAGE_INFO = "stage info";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpr);

        // Get intent from MainActivity
        Intent intent = getIntent();
        if(intent != null) {
            mStageIndex = (int) intent.getIntExtra(MainActivity.STAGE_INFO, -1);
        }
        findViewElementsById();
        boolean isEnabledButton = false;

        if(savedInstanceState != null){
            mChords = savedInstanceState.getStringArray("chords");
//            mSelections = savedInstanceState.getStringArrayList("selections");
//            mUserSelections = savedInstanceState.getStringArrayList("user_selections");
            mPlayIndexList = savedInstanceState.getIntegerArrayList("play_index_list");
            mPlayCount = savedInstanceState.getInt("play_count");
            mScoreTextView.setText(savedInstanceState.getString("score_text"));
            mScoreTextView.setVisibility(savedInstanceState.getInt("score_textview_visibility"));
            mHistoryBestTextView.setVisibility(savedInstanceState.getInt("history_best_textview_visibility"));
//            mRetryFAB.setVisibility(savedInstanceState.getInt("retry_fab_visivility"));
            if(mScoreTextView.getVisibility() != View.VISIBLE)
                mPlayNumTextView.setText("# " + (mPlayCount+1));
            mChordsPlayer = savedInstanceState.getParcelable("play_chords");
            mPlayImageView.setEnabled(savedInstanceState.getBoolean("play_imageview_enable"));
            isEnabledButton = savedInstanceState.getBoolean("selection_button_enable");
            mPromotionMakeChoiceTextView.setText(savedInstanceState.getString("promotion"));
//            mCountDownTimer = savedInstanceState.getParcelable("count_down_timer");
        } else{
            setUp();
//            mRetryFAB.setVisibility(View.INVISIBLE);
            mScoreTextView.setVisibility(View.INVISIBLE);
            mHistoryBestTextView.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "Each Chord Will be played 3 times.", Toast.LENGTH_SHORT)
                    .show();
            mPromotionMakeChoiceTextView.setText("Tap to play next chord");
        }
        createSelectionButtons();
//        setAllButtonsEnabled(isEnabledButton);
    }

    // Create selection item buttons
    private void createSelectionButtons(){
//        mButtons = new ArrayList<>();
//        for(int i = 0; i< mSelections.size(); ++i){
//            final Button button = new Button(this);
//            mButtons.add(button);
//            button.setText(mSelections.get(i));
////            mButtonsLayout.addView(button);
//            button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mPlayImageView.setEnabled(true);
//                    setAllButtonsEnabled(false);
//                    if(!mChordsPlayer.isCancelled())
//                        mChordsPlayer.cancel(true);
////                    mCountDownTimer.cancel();
//                    mPromotionMakeChoiceTextView.setText("Tap to play next chord");
//                    mUserSelections.add(button.getText().toString());
//                    if(GuitarChords.isCorrectType(
//                            button.getText().toString(), mChords[mPlayIndexList.get(mPlayCount)])){
//                        mScore++;
//                        Snackbar.make(v, "Correct! " + "The chord is "+ mChords[mPlayIndexList.get(mPlayCount)], Snackbar.LENGTH_SHORT)
//                                .show();
//                    } else{
//                        Snackbar.make(v, "Whoops! " + "The chord is "+ mChords[mPlayIndexList.get(mPlayCount)], Snackbar.LENGTH_SHORT)
//                                .show();
//                    }
//                    mPlayCount++;
//                    if(mPlayCount == PLAY_NUM)
//                        showTrainningResult(v);
//                }
//            });
//        }
    }

    // Find View Elements By ID, which will be used in onCreate() method
    private void findViewElementsById(){
        mPromotionMakeChoiceTextView = findViewById(R.id.promote_make_choice_textview_cpr);
        mPlayImageView = findViewById(R.id.begin_imageview_cpr);
        mPlayNumTextView = findViewById(R.id.play_num_textview_cpr);
        mScoreTextView = findViewById(R.id.score_textview_cpr);
        mHistoryBestTextView = findViewById(R.id.history_best_textview_cpr);
        mRetryFAB = (FloatingActionButton) findViewById(R.id.retry_fab_cpr);
        mSpinners = new ArrayList<>();
        for(int i=0; i<8; ++i){
            try{
                int spinnerId = getResources().getIdentifier("spinner" + i,"id", getPackageName());
                Spinner spinner = findViewById(spinnerId);
                mSpinners.add(spinner);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void setUp() {
        mCprChords = new ArrayList<>();
        mChords = getStringArray(this, "stage" + mStageIndex + "_cpr_chords");
        for(int i=0; i<mChords.length; ++i){
            String[] values = mChords[i].split(",");
            List<String> array = new ArrayList<>(Arrays.asList(values));
            mCprChords.add(array);
        }
        mCprChordsPlayTimes = get2DimIntegerArray(this, "stage" + mStageIndex +"_cpr_chord_play_times");
        mUserSelections = new ArrayList<>();
        for(int i=0; i<3; ++i)
            mUserSelections.add(new ArrayList<String>());
        mSelections = Arrays.asList(getStringArray(this, "stage" + mStageIndex + "_ssr_chords"));
        for(int i=0; i<mSpinners.size(); ++i){
            // Create an ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    getStringArrayId(this, "stage" + mStageIndex + "_ssr_chords"), android.R.layout.simple_spinner_item);
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Apply the adapter to the spinner
            mSpinners.get(i).setAdapter(adapter);
        }

        // Set visibility of Spinners accordingt to the playnums

        int spinnerIndex = 0;
        for(int i=0; i<mCprChordsPlayTimes.get(0).size(); ++i){
            if(mCprChordsPlayTimes.get(0).get(i) == 4){
                int spinnerId = getResources().getIdentifier("spinner" + (++spinnerIndex),"id", getPackageName());
                Spinner spinner = findViewById(spinnerId);
                spinner.setVisibility(View.INVISIBLE);
            }
            spinnerIndex++;
        }
    }

    // When hit the play button, call this method to play chord
    public void playChords(View view) {
        mPlayImageView.setEnabled(false);
        if(mPlayCount < PLAY_NUM){
            mPlayNumTextView.setText("# " + (mPlayCount+1));

            ArrayList<Integer> chordsAudios  = new ArrayList<>();
            for(String chord : mCprChords.get(mPlayCount))
                chordsAudios.add(GuitarChords.getChordAudioId(chord));
            ArrayList<Integer> playTimesList = (ArrayList<Integer>) mCprChordsPlayTimes.get(mPlayCount);
            mChordsPlayer = new chordsPlayer(this, playTimesList, mPromotionMakeChoiceTextView);
            mChordsPlayer.execute(chordsAudios);
        } else{
            showTrainningResult(view);
        }
    }

    // Set All Buttons of selections Enabled Or Disabled
    private void setAllButtonsEnabled(Boolean isEnable){
//        for(Button button : mButtons){
//            button.setEnabled(isEnable);
//        }
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
//        private ArrayList<String> mSelections;
//        private ArrayList<Integer> mPlayIndexList;
//        private ArrayList<String> mUserSelections;
//        private int mPlayCount = 0;

        outState.putStringArray("chords", mChords);
//        outState.putStringArrayList("selections", mSelections);
//        outState.putStringArrayList("user_selections", mUserSelections);
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

    public void doneSelections(View view) {
        mPlayImageView.setEnabled(true);
        mPlayCount++;
    }
}
