package com.erif.quickstate;

import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;

import java.util.Random;

public class MainActivity2 extends AppCompatActivity implements QuickState.OnClickListener {

    private NestedScrollView contentLoader;
    private QuickState state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        CoordinatorLayout parentLayout = findViewById(R.id.parentView);
        contentLoader = findViewById(R.id.loadingLayout2);
        state = new QuickState(parentLayout, true);
        state.contentLoader(contentLoader);
        state.onClickListener(this);
        requestIllustration();
    }

    private void requestIllustration() {
        blinkLoading();
        Handler handler = new Handler();
        Runnable runnable = () -> state.show(getRandomState());
        handler.postDelayed(runnable, 1500);
    }

    private void blinkLoading() {
        Animation anim = new AlphaAnimation(0.4f, 1.0f);
        anim.setDuration(500); //You can manage the blinking time with this parameter
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        contentLoader.startAnimation(anim);
    }

    @Override
    public void onClickStateButton(QuickStateView stateView, int buttonState, String tag) {
        switch (buttonState) {
            case QuickState.BUTTON_LEFT:
                finish();
                break;
            case QuickState.BUTTON_RIGHT:
                state.hideAndShowLoader();
                // Delay
                Handler handler = new Handler();
                Runnable runnable = this::requestIllustration;
                handler.postDelayed(runnable, 500);
                break;
        }
    }

    private String getRandomState() {
        String[] arr = new String[]{
                ConstantState.NETWORK, ConstantState.INTERNAL
        };
        return arr[random()];
    }

    private int random() {
        Random rand = new Random();
        return rand.nextInt(2);
    }

}