package com.erif.quickstate;

import android.content.Context;
import android.content.res.Resources;
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
import androidx.core.content.res.ResourcesCompat;

public class QuickStateView extends LinearLayout {

    private int illustration = 0;

    private String title;

    private String subtitle;

    private ImageView imgIllustration;
    private TextView txtTitle;
    private TextView txtSubtitle;

    private View contentView;
    private View contentLoader;

    private boolean useAnimation = false;

    public QuickStateView(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public QuickStateView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public QuickStateView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(@NonNull Context context, @Nullable AttributeSet attrs) {
        int defaultPaddingSize = getDimen(R.dimen.quick_state_parent_padding);
        setOrientation(LinearLayout.VERTICAL);
        setGravity(Gravity.CENTER);
        if (attrs != null) {
            TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs,
                    R.styleable.QuickStateView, 0, 0);
            try {
                int parentPadding = typedArray.getDimensionPixelSize(R.styleable.QuickStateView_android_padding, defaultPaddingSize);
                illustration = typedArray.getResourceId(R.styleable.QuickStateView_android_src, 0);
                title = typedArray.getString(R.styleable.QuickStateView_android_title);
                int titleTextColor = typedArray.getColor(R.styleable.QuickStateView_android_titleTextColor, Color.BLACK);
                int titleTextSize = typedArray.getDimensionPixelSize(R.styleable.QuickStateView_titleTextSize, getDimen(R.dimen.quick_state_title_text_size));
                subtitle = typedArray.getString(R.styleable.QuickStateView_android_subtitle);
                int subtitleTextColor = typedArray.getColor(R.styleable.QuickStateView_android_subtitleTextColor, Color.GRAY);
                int subtitleTextSize = typedArray.getDimensionPixelSize(R.styleable.QuickStateView_subtitleTextSize, getDimen(R.dimen.quick_state_subtitle_text_size));
                int imgScale = typedArray.getInt(R.styleable.QuickStateView_imageScale, 0);
                int imgWidth = typedArray.getDimensionPixelSize(R.styleable.QuickStateView_imageWidth, 0);
                int titleFont = typedArray.getResourceId(R.styleable.QuickStateView_titleFontFamily, 0);
                int subtitleFont = typedArray.getResourceId(R.styleable.QuickStateView_subtitleFontFamily, 0);

                setPadding(parentPadding, parentPadding, parentPadding, parentPadding);

                if (illustration != 0) {
                    imgIllustration = new ImageView(getContext());
                    imgIllustration.setId(R.id.quickStateImageIllustration);
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
                    txtTitle.setId(R.id.quickStateTextViewTitle);
                    txtTitle.setText(title);
                    txtTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleTextSize);
                    if (titleFont != 0) {
                        Typeface titleFace = getFont(titleFont);
                        txtTitle.setTypeface(titleFace, Typeface.BOLD);
                    } else {
                        txtTitle.setTypeface(null, Typeface.BOLD);
                    }
                    txtTitle.setGravity(Gravity.CENTER_HORIZONTAL);
                    txtTitle.setTextColor(titleTextColor);
                    LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                    txtTitle.setLayoutParams(params);
                    addView(txtTitle);
                }
                if (!TextUtils.isEmpty(subtitle)) {
                    txtSubtitle = new TextView(getContext());
                    txtSubtitle.setId(R.id.quickStateTextViewSubtitle);
                    txtSubtitle.setText(subtitle);
                    if (subtitleFont != 0) {
                        Typeface subtitleFace = getFont(subtitleFont);
                        txtSubtitle.setTypeface(subtitleFace, Typeface.NORMAL);
                    }
                    txtSubtitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, subtitleTextSize);
                    txtSubtitle.setGravity(Gravity.CENTER_HORIZONTAL);
                    txtSubtitle.setTextColor(subtitleTextColor);
                    LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                    if (!TextUtils.isEmpty(title))
                        params.topMargin = getDimen(R.dimen.quick_state_subtitle_margin_top);
                    txtSubtitle.setLayoutParams(params);
                    addView(txtSubtitle);
                }
            } finally {
                typedArray.recycle();
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

    public void animation(boolean isUseAnimation) {
        this.useAnimation = isUseAnimation;
    }

    public void contentView(View mainContentView) {
        this.contentView = mainContentView;
    }

    public void contentLoader(View loadingView) {
        this.contentLoader = loadingView;
    }

    public void illustration(@DrawableRes int illustration) {
        this.illustration = illustration;
        imgIllustration.setImageResource(illustration);
        imgIllustration.setVisibility(VISIBLE);
    }

    public ImageView getIllustrationImageView() {
        return imgIllustration;
    }

    public void title(String title) {
        this.title = title;
        txtTitle.setText(title);
        txtTitle.setVisibility(VISIBLE);
    }

    public TextView getTitleTextView() {
        return txtTitle;
    }

    public void subtitle(String subtitle) {
        this.subtitle = subtitle;
        txtSubtitle.setText(subtitle);
        txtSubtitle.setVisibility(VISIBLE);
    }

    public TextView getSubtitleTextView() {
        return txtSubtitle;
    }

    public void showWithAnim() {
        Animation animState = getAnim(R.anim.anim_quick_state_parent_alpha_in);
        setVisibility(VISIBLE);
        animState.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                if (contentView != null && contentView.getVisibility() == VISIBLE) {
                    contentView.clearAnimation();
                    Animation animContent = getAnim(R.anim.anim_quick_state_content_alpha_out);
                    if (contentView != null && animContent != null) {
                        animContent.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) { }
                            @Override
                            public void onAnimationEnd(Animation animation) {
                                contentView.setVisibility(GONE);
                            }
                            @Override
                            public void onAnimationRepeat(Animation animation) { }
                        });
                        contentView.startAnimation(animContent);
                    }
                }
                if (contentLoader != null && contentLoader.getVisibility() == VISIBLE) {
                    contentLoader.clearAnimation();
                    Animation animLoader = getAnim(R.anim.anim_quick_state_content_alpha_out);
                    if (contentLoader != null && animLoader != null) {
                        animLoader.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) { }
                            @Override
                            public void onAnimationEnd(Animation animation) {
                                contentLoader.setVisibility(GONE);
                            }
                            @Override
                            public void onAnimationRepeat(Animation animation) { }
                        });
                        contentLoader.startAnimation(animLoader);
                    }
                }
            }
            @Override
            public void onAnimationEnd(Animation animation) { }
            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        startAnimation(animState);
    }

    public void show() {
        if (useAnimation) {
            showWithAnim();
        } else {
            setVisibility(VISIBLE);
            if (contentView != null)
                contentView.setVisibility(View.GONE);
            if (contentLoader != null)
                contentLoader.setVisibility(View.GONE);
        }
    }

    public void hide() {
        if (useAnimation) {
            hideWithAnim(false);
        } else {
            setVisibility(GONE);
        }
    }

    public void hideAndShowLoader() {
        if (useAnimation) {
            hideWithAnim(true);
        } else {
            setVisibility(GONE);
            if(contentLoader != null && contentLoader.getVisibility() != VISIBLE)
                contentLoader.setVisibility(VISIBLE);
        }
    }

    private void hideWithAnim(boolean withLoader) {
        Animation anim = getAnim(R.anim.anim_quick_state_parent_alpha_out);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {
                setVisibility(GONE);
                if (withLoader) {
                    if (contentLoader != null && contentLoader.getVisibility() != VISIBLE) {
                        contentLoader.setVisibility(VISIBLE);
                        Animation animLoader = getAnim(R.anim.anim_quick_state_content_alpha_in);
                        contentLoader.startAnimation(animLoader);
                    }
                }
            }
            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        startAnimation(anim);
    }

    private int getDimen(int id) {
        return getResources().getDimensionPixelSize(id);
    }

    private void log(String message) {
        Log.d("QuickState", message);
    }

    private Animation getAnim(int id) {
        Animation anim = null;
        try {
            anim = AnimationUtils.loadAnimation(getContext(), id);
        } catch (Resources.NotFoundException e) {
            log("Anim res not found");
        }
        return anim;
    }

    private Typeface getFont(int fontRes) {
        Typeface typeface = null;
        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
                typeface = getContext().getResources().getFont(fontRes);
            else
                typeface = ResourcesCompat.getFont(getContext(), fontRes);
        } catch (Resources.NotFoundException e) {
            log("Font resource not found");
        }
        return typeface;
    }

}
