package com.github.polok.flipview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ViewFlipper;

public class FlipView extends ViewFlipper implements View.OnClickListener {

    public static interface FlipViewChangeListener {
        void onFlipViewClick(FlipView flipView, boolean isChecked);
    }

    private enum FlipViewSide {
        NOT_CHECKED,
        CHECKED
    }

    private static final int ANIMATION_DEFAULT_DURATION = 100;

    private View mFrontView;
    private View mBackView;
    private FlipViewChangeListener mFlipViewChangeListener;

    private boolean mChecked;

    private boolean hasAnimations;
    private long animationDuration;

    public FlipView(Context context) {
        this(context, null);
    }

    public FlipView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.flipview, this, true);

        if (attrs != null) {
            final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FlipView);

            try {
                setFrontView(a.getResourceId(R.styleable.FlipView_flip_view_front_layout, 0));
                setBackView(a.getResourceId(R.styleable.FlipView_flip_view_back_layout, -1));

                mChecked = a.getBoolean(R.styleable.FlipView_is_checked, false);
                if(mChecked) {
                    this.setDisplayedChild(mChecked ? FlipViewSide.CHECKED.ordinal() : FlipViewSide.NOT_CHECKED.ordinal());
                }

                hasAnimations = a.getBoolean(R.styleable.FlipView_show_animations, false);
                if (hasAnimations) {
                    animationDuration = (long) a.getInteger(R.styleable.FlipView_animation_duration, ANIMATION_DEFAULT_DURATION);

                    this.setInAnimation(context, a.getResourceId(R.styleable.FlipView_fade_in_animation, -1));
                    this.getInAnimation().setDuration(animationDuration);

                    this.setOutAnimation(context, a.getResourceId(R.styleable.FlipView_fade_out_animation, -1));
                    this.getOutAnimation().setDuration(animationDuration);
                }

                setOnClickListener(this);

            } catch (Exception e) {
                Log.e(FlipView.class.getSimpleName(), "Please double check all required attributes");
                throw new RuntimeException(e);
            } finally {
                a.recycle();
            }
        }
    }

    public void setFrontView(int layoutResId) {
        setFrontView(LayoutInflater.from(getContext()).inflate(
                layoutResId > 0 ? layoutResId : R.layout.flip_default_view_front_layout,
                null));
    }

    public void setFrontView(View view) {
        if (view == null) {
            throw new IllegalArgumentException("The front view can not be null");
        }

        this.removeViewAt(FlipViewSide.NOT_CHECKED.ordinal());
        this.addView(view, FlipViewSide.NOT_CHECKED.ordinal());
    }

    public void setBackView(int layoutResId) {
        setBackView(LayoutInflater.from(getContext()).inflate(
                layoutResId > 0 ? layoutResId : R.layout.flip_default_view_front_layout,
                null));
    }

    public void setBackView(View view) {
        if (view == null) {
            throw new IllegalArgumentException("The back view can not be null");
        }
        this.removeViewAt(FlipViewSide.CHECKED.ordinal());
        this.addView(view, FlipViewSide.CHECKED.ordinal());
    }

    @Override
    public boolean isInEditMode() {
        return true;
    }

    @Override
    public void onClick(View v) {
        swapCheckedStatus(!mChecked);
    }

    public void swapCheckedStatus(boolean checked) {
        setChecked(checked);
    }

    private void setChecked(boolean checked) {
        mChecked = checked;
        this.setDisplayedChild(checked ? FlipViewSide.CHECKED.ordinal() : FlipViewSide.NOT_CHECKED.ordinal());

        if(mFlipViewChangeListener != null) {
            mFlipViewChangeListener.onFlipViewClick(this, checked);
        }
    }

    public void setFlipViewChangeListener(FlipViewChangeListener mFlipViewChangeListener) {
        this.mFlipViewChangeListener = mFlipViewChangeListener;
    }
}
