package com.erif.quickstate;

import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private QuickState quickState;
    private LinearLayout loadingLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadingLayout = findViewById(R.id.loadingLayout);
        quickState = findViewById(R.id.act_main_quick_state);
        setupState();
        loadingProcess();

    }

    private void setupState() {
        //quickState.setLoadingView(loadingLayout);
        /*quickState.setAnimationOnShow(
                QuickState.getAnim(this, R.anim.alpha_in),
                QuickState.getAnim(this, R.anim.alpha_out)
        );*/
        quickState.setStateListener(new QuickStateListener() {
            @Override
            public void onClickButton() {

            }
        });
    }

    private void loadingProcess() {
        Handler handler = new Handler();
        Runnable run = () -> quickState.show();
        handler.postDelayed(run, 1500);
    }

}