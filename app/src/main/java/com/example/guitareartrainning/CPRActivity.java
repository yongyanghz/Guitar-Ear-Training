package com.example.guitareartrainning;

import android.content.Intent;
import android.os.Parcelable;
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
import java.util.concurrent.Executor;

import static com.example.guitareartrainning.ResourceHelper.get2DimIntegerArray;
import static com.example.guitareartrainning.ResourceHelper.getStringArray;
import static com.example.guitareartrainning.ResourceHelper.getStringArrayId;

public class CPRActivity extends AppCompatActivity {
    private String[] mChords;
    private int mStageIndex;
    private ImageView mPlayImageView;
    private TextView mPromotionMakeChoiceTextView;
    private TextView mScoreTextView;
    private TextView mPlayNumTextView;
    private TextView mHistoryBestTextView;
    private FloatingActionButton mRetryFAB;
    private List<Spinner> mSpinners;
    private Button mDoneSelectionButton;

    private List<List<String> > mCprChords;
    private List<List<Integer>> mCprChordsPlayTimes;
//    private List<List<String> > mUserSelections;
    private List<String> mUserSelections;
    private List<String> mSelections;
    private int mPlayCount = 0;
    private ChordsPlayer mChordsPlayer;
    private int mScore = 0;
    private boolean mIsFinish = false;


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
        setUp();
        if(savedInstanceState != null){
            mPlayNumTextView.setText(savedInstanceState.getString("play_num_text"));
            mPlayNumTextView.setVisibility(savedInstanceState.getInt("play_num_textview_visibility"));
            mScoreTextView.setText(savedInstanceState.getString("score_text"));
            mScoreTextView.setVisibility(savedInstanceState.getInt("score_textview_visibility"));
            mHistoryBestTextView.setText(savedInstanceState.getString("history_best_text"));
            mHistoryBestTextView.setVisibility(savedInstanceState.getInt("history_best_textview_visibility"));

            mPlayImageView.setEnabled(savedInstanceState.getBoolean("play_imageview_enable"));
            mDoneSelectionButton.setEnabled(savedInstanceState.getBoolean("done_selections_button_enable"));

            mUserSelections = savedInstanceState.getStringArrayList("user_selections");
            mPlayCount = savedInstanceState.getInt("play_count");
            mScore = savedInstanceState.getInt("score");
        } else{
            Toast.makeText(this, "You will hear a sequence of chords.", Toast.LENGTH_SHORT)
                    .show();
        }
    }



    // Find View Elements By ID, which will be used in onCreate() method
    private void findViewElementsById(){
        mPromotionMakeChoiceTextView = findViewById(R.id.promote_make_choice_textview_cpr);
        mPlayImageView = findViewById(R.id.begin_imageview_cpr);
        mPlayNumTextView = findViewById(R.id.play_num_textview_cpr);
        mScoreTextView = findViewById(R.id.score_textview_cpr);
        mHistoryBestTextView = findViewById(R.id.history_best_textview_cpr);
        mRetryFAB = (FloatingActionButton) findViewById(R.id.retry_fab_cpr);
        mDoneSelectionButton = findViewById(R.id.done_selections_button);
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

    // Setup for the CPR activity
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
        mSelections = Arrays.asList(getStringArray(this, "stage" + mStageIndex + "_ssr_chords"));
        setSpinnersContents();
        mDoneSelectionButton.setEnabled(false);

        mScoreTextView.setVisibility(View.INVISIBLE);
        mHistoryBestTextView.setVisibility(View.INVISIBLE);
        mPromotionMakeChoiceTextView.setText("Tap to begin");
    }

    // Set the contents of spinners
    private void setSpinnersContents(){
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
        mPromotionMakeChoiceTextView.setText("Listen And Select");
        mPlayImageView.setEnabled(false);
        mDoneSelectionButton.setEnabled(true);
        if(mPlayCount < PLAY_NUM){
            mPlayNumTextView.setText("# " + (mPlayCount+1));
            ArrayList<Integer> chordsAudios  = new ArrayList<>();
            for(String chord : mCprChords.get(mPlayCount))
                chordsAudios.add(GuitarChords.getChordAudioId(chord));
            ArrayList<Integer> playTimesList = (ArrayList<Integer>) mCprChordsPlayTimes.get(mPlayCount);
//            SoundPlay soundPlay = new SoundPlay(this, chordsAudios, playTimesList);
//            ThreadPerTaskExecutor executor = new ThreadPerTaskExecutor();
//            executor.execute(soundPlay);
            mChordsPlayer = new ChordsPlayer(this, playTimesList, mPromotionMakeChoiceTextView);
            mChordsPlayer.execute(chordsAudios);
        } else{
            showTrainningResult(view);
        }
    }


    // Show Trainning Result, score and history best
    private void showTrainningResult(View view){
        int pos = 0;
        if(!mIsFinish)
            for(int i=0; i<mCprChords.size(); ++i)
                for(int j=0; j<mCprChords.get(i).size(); ++j){
                    if(mCprChords.get(i).get(j).equals(mUserSelections.get(pos)))  // Not "==", "==" just compares the references
                        mScore++;
                    pos++;
            }
        Snackbar.make(view, "Congratulations! You have finished this trainning!", Snackbar.LENGTH_SHORT)
                .show();
        mScoreTextView.setVisibility(View.VISIBLE);
        mScoreTextView.setText("Your Score: " + mScore + "/" + mUserSelections.size());
        mHistoryBestTextView.setVisibility(View.VISIBLE);
        mHistoryBestTextView.setText("History Best: ");
        mPlayNumTextView.setVisibility(View.INVISIBLE);
        mPromotionMakeChoiceTextView.setText("Well Done!");
        mIsFinish = true;
        mPlayImageView.setEnabled(true);
        mDoneSelectionButton.setEnabled(false);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Variables should be saved
        outState.putString("play_num_text", mPlayNumTextView.getText().toString());
        outState.putInt("play_num_textview_visibility", mPlayNumTextView.getVisibility());
        outState.putInt("score_textview_visibility", mScoreTextView.getVisibility());
        outState.putString("score_text", mScoreTextView.getText().toString());
        outState.putInt("score_textview_visibility", mScoreTextView.getVisibility());
        outState.putString("history_best_text", mHistoryBestTextView.getText().toString());
        outState.putInt("history_best_textview_visibility", mHistoryBestTextView.getVisibility());

        outState.putBoolean("play_imageview_enable", mPlayImageView.isEnabled());
        outState.putBoolean("done_selections_button_enable", mDoneSelectionButton.isEnabled());

        outState.putStringArrayList("user_selections", (ArrayList<String>) mUserSelections);
        outState.putInt("play_count", mPlayCount);
        outState.putInt("score", mScore);
    }

    // Retry this ear training
    public void retry(View view) {
        finish();
        startActivity(getIntent());
    }

    public void doneSelections(View view) {
        SoundPlay.stop();

        getUserAnswers();
        showCorrectAnswers(view);

        mPlayImageView.setEnabled(true);
        mDoneSelectionButton.setEnabled(false);
        mPlayCount++;
        mPromotionMakeChoiceTextView.setText("Tap to continue");
    }

    private void getUserAnswers(){
        for(int i=0; i<mSpinners.size(); ++i){
            if(mSpinners.get(i).getVisibility() == View.VISIBLE) // the view is visble
            {
                String selection = mSpinners.get(i).getSelectedItem().toString();
                mUserSelections.add(selection);
            }
        }
    }

    private void showCorrectAnswers(View v){
        String correctAnswers = "";
        for(int i=0; i<mCprChords.get(mPlayCount).size(); ++i){
            correctAnswers += " " + mCprChords.get(mPlayCount).get(i);
        }
        Snackbar.make(v, "The correct chords is "+ correctAnswers, Snackbar.LENGTH_SHORT)
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mChordsPlayer!=null){
            mChordsPlayer.stopAndCancel();
        }
    }
}
