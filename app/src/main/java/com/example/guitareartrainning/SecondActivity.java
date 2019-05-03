package com.example.guitareartrainning;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    private int mStageIndex = -1;
    private TextView mStageTextView;
    private TextView mCQRTextView;
    private Button mCQRButton;

    public static final String STAGE_INFO = "stage info";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // Find view elements by ID
        mStageTextView = findViewById(R.id.stage_textview);
        mCQRTextView = findViewById(R.id.cqr_textview);
        mCQRButton = findViewById(R.id.cqr_button);

        // Get intent from MainActivity
        Intent intent = getIntent();
        if(intent != null) {
            mStageIndex = (int) intent.getIntExtra(MainActivity.STAGE_INFO, -1);
        }

        mStageTextView.setText("Stage " + mStageIndex);
        // Stage 1 does not have  CQR trainning
        if(mStageIndex == 1){
            mCQRTextView.setVisibility(View.INVISIBLE);
            mCQRButton.setVisibility(View.INVISIBLE);
        }

    }

    // Goto CQRActivity
    public void goCQRTrainning(View view) {
        Intent intent = new Intent(this, CQRActivity.class);
        intent.putExtra(STAGE_INFO, mStageIndex);
        startActivity(intent);
    }

    // Goto CPRActivity
    public void goCPRTrainning(View view) {
        Intent intent = new Intent(this, CPRActivity.class);
        intent.putExtra(STAGE_INFO, mStageIndex);
        startActivity(intent);
    }

    // Goto SSRActivity
    public void goSSRTrainning(View view) {
        Intent intent = new Intent(this, SSRActivity.class);
        intent.putExtra(STAGE_INFO, mStageIndex);
        startActivity(intent);
    }
}
