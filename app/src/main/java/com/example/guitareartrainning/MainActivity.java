package com.example.guitareartrainning;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public static final String STAGE_INFO = "stage info";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goTrainning(View view) {
        int stageIndex = -1;
        switch (view.getId()){
            case R.id.stage1_button:
                stageIndex = 1; break;
            case R.id.stage2_button:
                stageIndex = 2; break;
            case R.id.stage3_button:
                stageIndex = 3; break;
            case R.id.stage4_button:
                stageIndex = 4; break;
            case R.id.stage5_button:
                stageIndex = 5; break;
            case R.id.stage6_button:
                stageIndex = 6; break;
            case R.id.stage7_button:
                stageIndex = 7; break;
            case R.id.stage8_button:
                stageIndex = 8; break;
            case R.id.stage9_button:
                stageIndex = 9; break;
            default: break;
        }
        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra(STAGE_INFO, stageIndex);
        startActivity(intent);
    }
}
