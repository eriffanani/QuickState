package com.erif.quickstate;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.annotation.AnimRes;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

public class QuickState {

    private LayoutInflater inflater;
    private final View parentLayout;
    private QuickStateView stateView;
    private View contentLoader;
    private OnClickListener listener;
    private Context context;
    private SharedPreferences sp;
    private boolean enableAnimation = false;

    public static final int BUTTON_SINGLE = 1;
    public static final int BUTTON_LEFT = 2;
    public static final int BUTTON_TOP = 3;
    public static final int BUTTON_RIGHT = 4;
    public static final int BUTTON_BOTTOM = 5;
    private String currentState;

    public QuickState(RelativeLayout parentLayout) {
        this.parentLayout = parentLayout;
        init();
    }
    public QuickState(RelativeLayout parentLayout, boolean enableAnimation) {
        this.parentLayout = parentLayout;
        this.enableAnimation = enableAnimation;
        init();
    }

    public QuickState(ConstraintLayout parentLayout) {
        this.parentLayout = parentLayout;
        init();
    }
    public QuickState(ConstraintLayout parentLayout, boolean enableAnimation) {
        this.parentLayout = parentLayout;
        this.enableAnimation = enableAnimation;
        init();
    }

    public QuickState(CoordinatorLayout parentLayout) {
        this.parentLayout = parentLayout;
        init();
    }
    public QuickState(CoordinatorLayout parentLayout, boolean enableAnimation) {
        this.parentLayout = parentLayout;
        this.enableAnimation = enableAnimation;
        init();
    }

    public QuickState(FrameLayout parentLayout) {
        this.parentLayout = parentLayout;
        init();
    }
    public QuickState(FrameLayout parentLayout, boolean enableAnimation) {
        this.parentLayout = parentLayout;
        this.enableAnimation = enableAnimation;
        init();
    }

    private void init() {
        inflater = LayoutInflater.from(parentLayout.getContext());
        context = parentLayout.getContext();
        String packageName = context.getPackageName();
        String name = packageName + ".QuickState";
        sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public void show(@NonNull String name) {
        this.currentState = name;
        int layout = sp.getInt(name, 0);
        try {
            context.getResources().getLayout(layout);
            ViewGroup viewGroup = (ViewGroup) ((Activity) context).getWindow().getDecorView().getRootView();
            View view = inflater.inflate(layout, viewGroup, false);
            if (view instanceof QuickStateView)
                stateView = (QuickStateView) view;
            setupButton(view, R.id.quickStateButtonSingle);
            setupButton(view, R.id.quickStateButtonLeft);
            setupButton(view, R.id.quickStateButtonTop);
            setupButton(view, R.id.quickStateButtonRight);
            setupButton(view, R.id.quickStateButtonBottom);

            if (stateView != null) {
                if (parentLayout instanceof RelativeLayout) {
                    relative().removeView(view);
                    relative().addView(view, 0);
                } else if (parentLayout instanceof ConstraintLayout) {
                    constraint().removeView(view);
                    constraint().addView(view, 0);
                } else if (parentLayout instanceof CoordinatorLayout) {
                    coordinator().removeView(view);
                    coordinator().addView(view, 0);
                } else if (parentLayout instanceof FrameLayout) {
                    frame().removeView(view);
                    frame().addView(view, 0);
                }
                if (contentLoader != null)
                    stateView.contentLoader(contentLoader);
                stateView.animation(enableAnimation);
                stateView.show();
            }
        } catch (Resources.NotFoundException e) {
            log("Layout resource not found");
        }
    }

    public void hide() {
        if (stateView != null)
            stateView.hide();
    }

    public void hideAndShowLoader() {
        if (stateView != null)
            stateView.hideAndShowLoader();
    }

    public void enableAnimation(boolean enableAnimation) {
        this.enableAnimation = enableAnimation;
    }

    public void onClickListener(OnClickListener callback) {
        this.listener = callback;
    }

    public void contentLoader(View contentLoader) {
        this.contentLoader = contentLoader;
    }

    public String currentState() {
        return currentState;
    }

    private RelativeLayout relative() {
        return ((RelativeLayout) parentLayout);
    }

    private ConstraintLayout constraint() {
        return ((ConstraintLayout) parentLayout);
    }

    private CoordinatorLayout coordinator() {
        return ((CoordinatorLayout) parentLayout);
    }

    private FrameLayout frame() {
        return ((FrameLayout) parentLayout);
    }

    private void setupButton(View view, int id) {
        View button = view.findViewById(id);
        if (button != null) {
            String tag = button.getTag().toString();
            button.setOnClickListener(v -> {
                if (listener != null) {
                    int state = 0;
                    if (id == R.id.quickStateButtonSingle) {
                        state = BUTTON_SINGLE;
                    } else if (id == R.id.quickStateButtonLeft) {
                        state = BUTTON_LEFT;
                    } else if (id == R.id.quickStateButtonTop) {
                        state = BUTTON_TOP;
                    } else if (id == R.id.quickStateButtonRight) {
                        state = BUTTON_RIGHT;
                    } else if (id == R.id.quickStateButtonBottom) {
                        state = BUTTON_BOTTOM;
                    }
                    listener.onClickStateButton(stateView, state, tag);
                }
            });
        }
    }

    public interface OnClickListener {
        public void onClickStateButton(QuickStateView stateView, int buttonState, String tag);
    }

    public Animation getAnim(@AnimRes int id) {
        Animation anim = null;
        try {
            anim = AnimationUtils.loadAnimation(context, id);
        } catch (Resources.NotFoundException e) {
            log("Anim res not found");
        }
        return anim;
    }

    private void log(String message) {
        Log.d("QuickState", message);
    }

}
