package com.example.guitareartrainning;

import android.content.Intent;
import android.content.res.Resources;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.support.v7.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.support.design.widget.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;

public class CQRActivity extends AppCompatActivity {

    private String[] mChords;
    private ArrayList<String> mChoies;
    private int mStageIndex;
    private ImageView mPlayImageView;
    private GridLayout mButtonsLayout;

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

        mButtonsLayout = findViewById(R.id.buttons_layout);
        Resources res = getResources();
        switch (mStageIndex){
            case 2:
                mChoies = new ArrayList<>(
                        Arrays.asList("Major", "Minor"));
                mChords = res.getStringArray(R.array.stage2_cqr_chords);
                break;
            case 3:
                mChoies = new ArrayList<>(
                        Arrays.asList("Major", "Minor"));
                mChords = res.getStringArray(R.array.stage3_cqr_chords);
                break;
            case 4:
                mChoies = new ArrayList<>(
                        Arrays.asList("D", "Dm", "D7"));
                mChords = res.getStringArray(R.array.stage4_cqr_chords);
                break;
            case 5:
                mChoies = new ArrayList<>(
                        Arrays.asList("Major", "Minor", "7"));
                mChords = res.getStringArray(R.array.stage5_cqr_chords);
                break;
            case 6:
                mChoies = new ArrayList<>(
                        Arrays.asList("Major", "Minor", "7"));
                mChords = res.getStringArray(R.array.stage6_cqr_chords);
                break;
            case 7:
                mChoies = new ArrayList<>(
                        Arrays.asList("Major", "Minor", "7"));
                mChords = res.getStringArray(R.array.stage7_cqr_chords);
                break;
            case 8:
                mChoies = new ArrayList<>(
                        Arrays.asList("Major", "Minor", "7", "Maj7", "Power"));
                mChords = res.getStringArray(R.array.stage8_cqr_chords);
                break;
//            case 9:
//                mChoies = new ArrayList<>(
//                        Arrays.asList("E", "A", "D", "Em", "Am", "Dm"));
//                break;
            default: break;
        }
        for(int i=0; i<mChoies.size();++i){
            final Button button = new Button(this);
            button.setText(mChoies.get(i));
            mButtonsLayout.addView(button);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snackbar.make(v, "You selected the chord as a " + button.getText(), Snackbar.LENGTH_SHORT)
                            .show();
                }
            });
        }
    }

    public void playChords(View view) {


        GuitarChords.stopPlay();
        ArrayList<String> chords = new ArrayList<>(
                Arrays.asList("E", "E", "E", "E", "A", "A", "A", "A",
                        "D","D", "D", "D", "Em", "Em", "Em", "Em"));
        GuitarChords.setUp();

        GuitarChords.playChords(this, chords);
    }

}
