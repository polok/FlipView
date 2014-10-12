package pl.polak.flipview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ViewFlipper;

public class FlipView extends ViewFlipper {

    private static final int ANIMATION_DEFAULT_DURATION = 100;

    private View mFrontView;
    private View mBackView;

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

                hasAnimations = a.getBoolean(R.styleable.FlipView_show_animations, false);

                if (hasAnimations) {
                    animationDuration = (long) a.getInteger(R.styleable.FlipView_animation_duration, ANIMATION_DEFAULT_DURATION);
                    this.setInAnimation(context, a.getResourceId(R.styleable.FlipView_fade_in_animation, -1));
                    this.getInAnimation().setDuration(animationDuration);

                    this.setOutAnimation(context, a.getResourceId(R.styleable.FlipView_fade_out_animation, -1));
                    this.getOutAnimation().setDuration(animationDuration);
                }
            } catch (Exception e) {
                Log.e(FlipView.class.getSimpleName(), "Please double check all required attributes");
                throw new RuntimeException(e);
            } finally {
                a.recycle();
            }
        }
    }

}
