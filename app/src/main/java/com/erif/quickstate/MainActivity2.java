package com.erif.quickstate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class MainActivity2 extends AppCompatActivity {

    private NestedScrollView nestedScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        nestedScrollView = findViewById(R.id.loadingLayout2);
        blinkLoading();

        QuickState quickState = findViewById(R.id.quickState2);
        quickState.setLoadingView(nestedScrollView);
        quickState.setAnimationOnShow(
                QuickState.getAnim(this, R.anim.alpha_in),
                QuickState.getAnim(this, R.anim.alpha_out)
        );
        Handler handler = new Handler();
        Runnable runnable = quickState::show;
        handler.postDelayed(runnable, 1700);

        Computer computer = new Computer.ComputerBuilder("", "").build();
        Computer.ComputerBuilder builder = new Computer.ComputerBuilder();
        builder.setGraphicsCardEnabled(false);
        Computer c = builder.build();

    }

    private void blinkLoading() {
        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(500); //You can manage the blinking time with this parameter
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        nestedScrollView.startAnimation(anim);
    }

}