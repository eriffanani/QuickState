package com.erif.quickstate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

public class MainActivity extends AppCompatActivity {

    private QuickState state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CoordinatorLayout parentView = findViewById(R.id.act_main_parentLayout);
        state = new QuickState(parentView, true);
        LinearLayout contentLoader = findViewById(R.id.loadingLayout);

        loadingProcess();
        state.contentLoader(contentLoader);
        state.onClickListener((stateView, buttonState, tag) -> {
            if (buttonState == QuickState.BUTTON_SINGLE) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });
    }

    private void loadingProcess() {
        Handler handler = new Handler();
        Runnable run = () -> state.show(ConstantState.EMPTY);
        handler.postDelayed(run, 1500);
    }

}