package pl.polak.flipview;

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

    private boolean hasAnimations;
    private boolean mChecked;

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

                mFrontView = LayoutInflater.from(context).inflate(a.getResourceId(R.styleable.FlipView_flip_view_front_layout, -1), this);
                mBackView = LayoutInflater.from(context).inflate(a.getResourceId(R.styleable.FlipView_flip_view_back_layout, -1), this);

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
