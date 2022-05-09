package com.erif.quickstate;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class QuickState extends LinearLayout {

    private int parentPadding;
    private int illustration = 0;
    private int imgScale;
    private int imgWidth;

    private String title;
    private int titleTextColor;
    private int titleTextSize;

    private String subtitle;
    private int subtitleTextColor;
    private int subtitleTextSize;

    private String buttonText;
    private int buttonIconColor;
    private int buttonBackgroundColor;

    private ImageView imgIllustration;
    private TextView txtTitle;
    private TextView txtSubtitle;
    private FloatingActionButton fabAction;

    private View mainContentView;
    private View loadingView;

    private Animation animationShowState;
    private Animation animationHideLoading;
    private Animation animationHideContent;

    public QuickState(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public QuickState(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public QuickState(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    /*public QuickState(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }*/

    public void setMainContentView(View mainContentView) {
        this.mainContentView = mainContentView;
    }

    public void setLoadingView(View loadingView) {
        this.loadingView = loadingView;
    }

    public void setContentAndLoadingView(View mainContentView, View loadingView) {
        this.mainContentView = mainContentView;
        this.loadingView = loadingView;
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        int defaultPaddingSize = getDimen(R.dimen.quick_state_parent_padding);
        setOrientation(LinearLayout.VERTICAL);
        setGravity(Gravity.CENTER);
        if (attrs != null) {
            TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                    R.styleable.QuickState, 0, 0);
            try {
                parentPadding = a.getDimensionPixelSize(R.styleable.QuickState_android_padding, defaultPaddingSize);
                illustration = a.getResourceId(R.styleable.QuickState_android_src, 0);
                title = a.getString(R.styleable.QuickState_android_title);
                titleTextColor  = a.getColor(R.styleable.QuickState_android_titleTextColor, Color.BLACK);
                titleTextSize  = a.getDimensionPixelSize(R.styleable.QuickState_titleTextSize, getDimen(R.dimen.quick_state_title_text_size));
                subtitle = a.getString(R.styleable.QuickState_android_subtitle);
                subtitleTextColor  = a.getColor(R.styleable.QuickState_android_subtitleTextColor, Color.GRAY);
                subtitleTextSize  = a.getDimensionPixelSize(R.styleable.QuickState_subtitleTextSize, getDimen(R.dimen.quick_state_subtitle_text_size));
                buttonText = a.getString(R.styleable.QuickState_button);
                int buttonType = a.getInt(R.styleable.QuickState_buttonType, 0);
                buttonIconColor = a.getColor(R.styleable.QuickState_buttonIconColor, Color.WHITE);
                int defaultColor = ContextCompat.getColor(getContext(), R.color.design_default_color_error);
                buttonBackgroundColor = a.getColor(R.styleable.QuickState_buttonBackgroundColor, defaultColor);
                imgScale = a.getInt(R.styleable.QuickState_imageScale, 0);
                imgWidth = a.getDimensionPixelSize(R.styleable.QuickState_imageWidth, 0);

                setPadding(parentPadding, parentPadding, parentPadding, parentPadding);

                if (illustration != 0) {
                    imgIllustration = new ImageView(getContext());
                    imgIllustration.setImageResource(illustration);

                    if (imgScale > 0) {
                        imgIllustration.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        ConstraintLayout constraint = new ConstraintLayout(getContext());
                        constraint.setId(View.generateViewId());
                        LayoutParams paramConstraint = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
                        paramConstraint.bottomMargin = getDimen(R.dimen.quick_state_image_margin_bottom);
                        constraint.setLayoutParams(paramConstraint);
                        int getImgWidth = imgWidth == 0 ? ConstraintLayout.LayoutParams.MATCH_PARENT : imgWidth;
                        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(getImgWidth, 0);
                        params.startToStart = constraint.getId();
                        params.topToTop = constraint.getId();
                        params.endToEnd = constraint.getId();
                        if (imgScale == 1) {
                            params.dimensionRatio = "1:1";
                        } else if (imgScale == 2) {
                            params.dimensionRatio = "16:9";
                        }
                        imgIllustration.setLayoutParams(params);
                        constraint.addView(imgIllustration);
                        addView(constraint);
                    } else {
                        imgIllustration.setAdjustViewBounds(true);
                        int getImgWidth = imgWidth == 0 ? LayoutParams.MATCH_PARENT : imgWidth;
                        LayoutParams params = new LayoutParams(getImgWidth, LayoutParams.WRAP_CONTENT);
                        params.bottomMargin = getDimen(R.dimen.quick_state_image_margin_bottom);
                        imgIllustration.setLayoutParams(params);
                        addView(imgIllustration);
                    }
                }

                if (!TextUtils.isEmpty(title)) {
                    txtTitle = new TextView(getContext());
                    txtTitle.setText(title);
                    txtTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleTextSize);
                    txtTitle.setTypeface(null, Typeface.BOLD);
                    txtTitle.setGravity(Gravity.CENTER_HORIZONTAL);
                    txtTitle.setTextColor(titleTextColor);
                    LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                    txtTitle.setLayoutParams(params);
                    addView(txtTitle);
                }
                if (!TextUtils.isEmpty(subtitle)) {
                    txtSubtitle = new TextView(getContext());
                    txtSubtitle.setText(subtitle);
                    txtSubtitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, subtitleTextSize);
                    txtSubtitle.setGravity(Gravity.CENTER_HORIZONTAL);
                    txtSubtitle.setTextColor(subtitleTextColor);
                    LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                    if (!TextUtils.isEmpty(title))
                        params.topMargin = getDimen(R.dimen.quick_state_subtitle_margin_top);
                    txtSubtitle.setLayoutParams(params);
                    addView(txtSubtitle);
                }

                if (buttonType == 1) {
                    fabAction = new FloatingActionButton(getContext());
                    fabAction.setSize(FloatingActionButton.SIZE_MINI);
                    fabAction.setImageResource(R.drawable.ic_reload_white);
                    fabAction.setBackgroundTintList(
                            ColorStateList.valueOf(buttonBackgroundColor)
                    );
                    ImageViewCompat.setImageTintList(
                            fabAction,
                            ColorStateList.valueOf(buttonIconColor)
                    );
                    LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                    params.topMargin= getDimen(R.dimen.quick_state_button_margin_top);
                    params.bottomMargin= getDimen(R.dimen.quick_state_subtitle_margin_top);
                    fabAction.setLayoutParams(params);
                    addView(fabAction);
                }

            } finally {
                a.recycle();
            }
        }
        /*if (attrs != null) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View child = inflater.inflate(R.layout.frg_quick_state, null);
        this.removeAllViews();
        this.addView(child);
        if (attrs != null) {
            TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                    R.styleable.QuickState, 0, 0);
            try {
                illustration = a.getResourceId(R.styleable.QuickState_illustration, 0);
                title = a.getString(R.styleable.QuickState_title);
                message = a.getString(R.styleable.QuickState_message);
                buttonText = a.getString(R.styleable.QuickState_button);
                int buttonStyle = a.getInt(R.styleable.QuickState_button_style, 0);

                imgIllustration = child.findViewById(R.id.frg_quick_state_imgIllustration);
                txtTitle = child.findViewById(R.id.frg_quick_state_txtTitle);
                txtMessage = child.findViewById(R.id.frg_quick_state_txtMessage);
                btnAction = child.findViewById(R.id.frg_quick_state_btnAction);
                fabAction = child.findViewById(R.id.frg_quick_state_fabAction);

                if (illustration != 0) {
                    imgIllustration.setImageResource(illustration);
                    imgIllustration.setVisibility(VISIBLE);
                } else {
                    imgIllustration.setVisibility(GONE);
                }

                if (title != null && !title.equals("")) {
                    txtTitle.setText(title);
                    txtTitle.setVisibility(VISIBLE);
                } else {
                    txtTitle.setVisibility(GONE);
                }

                if (message != null && !message.equals("")) {
                    txtMessage.setText(message);
                    txtMessage.setVisibility(VISIBLE);
                } else {
                    txtMessage.setVisibility(GONE);
                }

                if (buttonText != null && !buttonText.equals("")) {
                    if (buttonStyle == 1) {
                        btnAction.setVisibility(GONE);
                        fabAction.setVisibility(VISIBLE);
                    } else {
                        btnAction.setText(buttonText);
                        btnAction.setVisibility(VISIBLE);
                        fabAction.setVisibility(GONE);
                    }
                } else {
                    btnAction.setVisibility(GONE);
                    if (buttonStyle == 1) {
                        fabAction.setVisibility(VISIBLE);
                    } else {
                        fabAction.setVisibility(GONE);
                    }
                }
            } finally {
                a.recycle();
            }
        }*/
    }

    public void setIllustration(@DrawableRes int illustration) {
        this.illustration = illustration;
        imgIllustration.setImageResource(illustration);
        imgIllustration.setVisibility(VISIBLE);
    }

    public void setTitle(String title) {
        this.title = title;
        txtTitle.setText(title);
        txtTitle.setVisibility(VISIBLE);
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
        txtSubtitle.setText(subtitle);
        txtSubtitle.setVisibility(VISIBLE);
    }

    public void setStateListener(QuickStateListener callback) {
        if (fabAction != null)
            fabAction.setOnClickListener(view -> callback.onClickButton());
    }

    public void setAnimationOnShow(Animation showState) {
        this.animationShowState = showState;
    }

    public void setAnimationOnShow(Animation showState, Animation hideLoading) {
        this.animationShowState = showState;
        this.animationHideLoading = hideLoading;
    }

    public void setAnimationOnShow(Animation showState, Animation hideLoading, Animation hideContent) {
        this.animationShowState = showState;
        this.animationHideLoading = hideLoading;
        this.animationHideContent = hideContent;
    }

    public void show() {
        if (animationShowState != null) {
            startAnimation(animationShowState);
            setVisibility(VISIBLE);
            animationShowState.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    if (mainContentView != null && mainContentView.getVisibility() == VISIBLE) {
                        if (mainContentView != null && animationHideContent != null) {
                            mainContentView.startAnimation(animationHideContent);
                            animationHideContent.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) { }
                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    mainContentView.setVisibility(GONE);
                                }
                                @Override
                                public void onAnimationRepeat(Animation animation) { }
                            });
                        }
                    }
                    if (loadingView != null && loadingView.getVisibility() == VISIBLE) {
                        loadingView.clearAnimation();
                        if (loadingView != null && animationHideLoading != null) {
                            loadingView.startAnimation(animationHideLoading);
                            animationHideLoading.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) { }
                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    loadingView.setVisibility(GONE);
                                }
                                @Override
                                public void onAnimationRepeat(Animation animation) { }
                            });
                        }
                    }
                }
                @Override
                public void onAnimationEnd(Animation animation) { }
                @Override
                public void onAnimationRepeat(Animation animation) {}
            });
        } else {
            setVisibility(VISIBLE);
            if (mainContentView != null) {
                mainContentView.setVisibility(View.GONE);
            }
            if (loadingView != null) {
                loadingView.clearAnimation();
                loadingView.setVisibility(View.GONE);
            }
        }
    }

    public void hide() {
        setVisibility(GONE);
        if (mainContentView != null) {
            mainContentView.setVisibility(View.VISIBLE);
        }
        if (loadingView != null) {
            loadingView.setVisibility(View.VISIBLE);
        }
    }

    public void hide(boolean animate) {
        /*if (animate) {
            Animation animation = AnimationUtils.loadAnimation(this.getContext(), R.anim.alpha_out);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {}
                @Override
                public void onAnimationEnd(Animation animation) {
                    setVisibility(GONE);
                    if (mainContentView != null) {
                        mainContentView.setVisibility(View.VISIBLE);
                    }
                    if (loadingView != null) {
                        loadingView.setVisibility(View.VISIBLE);
                    }
                }
                @Override
                public void onAnimationRepeat(Animation animation) {}
            });
            this.startAnimation(animation);
        } else {
            hide();
        }*/
    }

    private int getDimen(int id) {
        return getResources().getDimensionPixelSize(id);
    }

    private void debug(String message) {
        Log.d("QuickState", message);
    }

    public static Animation getAnim(Context context, int id) {
        return AnimationUtils.loadAnimation(context, id);
    }

    public void create(Computer builder) {

    }

}
