package com.erif.quickstates;

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

import com.erif.quickstate.R;

public class QuickState {

    private LayoutInflater inflater;
    private final View parentLayout;
    private QuickStateView stateView;
    private View contentLoader;
    private OnClickListener listener;
    private Context context;
    private SharedPreferences sp;
    private boolean enableAnimation = false;
    private View viewState;

    public static final int BUTTON_PRIMARY = 1;
    public static final int BUTTON_SECONDARY = 2;
    public static final int BUTTON_TERTIARY = 3;
    public static final int BUTTON_QUATERNARY = 4;
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
        if (currentState == null) {
            this.currentState = name;
            createState(name);
        } else {
            if (!currentState.equals(name)) {
                this.currentState = name;
                if (parentLayout instanceof RelativeLayout relative) {
                    relative.removeView(viewState);
                } else if (parentLayout instanceof ConstraintLayout constraint) {
                    constraint.removeView(viewState);
                } else if (parentLayout instanceof CoordinatorLayout coordinator) {
                    coordinator.removeView(viewState);
                } else if (parentLayout instanceof FrameLayout frame) {
                    frame.removeView(viewState);
                }
                createState(name);
            } else {
                stateView.show();
            }
        }
    }

    private void createState(String name) {
        int layout = sp.getInt(name, 0);
        try {
            context.getResources().getLayout(layout);
            ViewGroup viewGroup = (ViewGroup) parentLayout.getRootView();
            viewState = inflater.inflate(layout, viewGroup, false);
            viewState.setId(R.id.quickStateContainer);
            if (viewState instanceof QuickStateView)
                stateView = (QuickStateView) viewState;
            setupButton(viewState, R.id.stateButtonPrimary);
            setupButton(viewState, R.id.stateButtonSecondary);
            setupButton(viewState, R.id.stateButtonTertiary);
            setupButton(viewState, R.id.stateButtonQuaternary);

            if (stateView != null) {
                if (parentLayout instanceof RelativeLayout relative) {
                    relative.addView(viewState);
                } else if (parentLayout instanceof ConstraintLayout constraint) {
                    constraint.addView(viewState);
                } else if (parentLayout instanceof CoordinatorLayout coordinator) {
                    coordinator.addView(viewState);
                } else if (parentLayout instanceof FrameLayout frame) {
                    frame.addView(viewState);
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
        if (stateView != null && stateView.getVisibility() == View.VISIBLE)
            stateView.hide();
    }

    public void hideAndShowLoader() {
        if (stateView != null && stateView.getVisibility() == View.VISIBLE)
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

    private void setupButton(View view, int id) {
        View button = view.findViewById(id);
        if (button != null) {
            button.setOnClickListener(v -> {
                Object getTag = button.getTag();
                if (listener != null) {
                    int state = 0;
                    if (id == R.id.stateButtonPrimary) {
                        state = BUTTON_PRIMARY;
                    } else if (id == R.id.stateButtonSecondary) {
                        state = BUTTON_SECONDARY;
                    } else if (id == R.id.stateButtonTertiary) {
                        state = BUTTON_TERTIARY;
                    } else if (id == R.id.stateButtonQuaternary) {
                        state = BUTTON_QUATERNARY;
                    }
                    String tag = null;
                    if (getTag != null)
                        tag = getTag.toString();
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
