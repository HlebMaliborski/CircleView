package hmaliborski.com.circleview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import hmaliborski.com.circleview.R;

public class CircleView extends android.support.v7.widget.AppCompatTextView {
    private static final String TAG = CircleView.class.getSimpleName();

    private static final float FULL_SWEEP_ANGLE = 360;

    private int mLoadingColor;
    private int mBorderColor;

    private float mBorderWidth;

    private int mLoadingPercentage;

    private Paint mInnerPaint;

    private Paint mOuterPaint;
    private RectF mRectF;

    public void setLoadingPercentage(int mLoadingPercentage) {
        this.mLoadingPercentage = mLoadingPercentage;
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet set) {
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(set, R.styleable.CircleView, 0, 0);

        int mCircleColor;
        try {
            mLoadingColor = typedArray.getInt(R.styleable.CircleView_circleLoadingColor, Color.BLACK);
            mCircleColor = typedArray.getInt(R.styleable.CircleView_circleColor, Color.BLACK);
            mBorderColor = typedArray.getColor(R.styleable.CircleView_circleBorderColor, Color.BLACK);
            mBorderWidth = typedArray.getFloat(R.styleable.CircleView_circleBorderWidth, 1);
        } finally {
            typedArray.recycle();
        }

        mRectF = new RectF();

        mInnerPaint = new Paint();
        mInnerPaint.setAntiAlias(true);
        mInnerPaint.setColor(mCircleColor);

        mOuterPaint = new Paint();
        mOuterPaint.setAntiAlias(true);
        mOuterPaint.setStyle(Paint.Style.STROKE);
        mOuterPaint.setStrokeWidth(mBorderWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();

        int leftPadding = getPaddingLeft();
        int rightPadding = getPaddingRight();
        int topPadding = getPaddingTop();
        int bottomPadding = getPaddingBottom();

        int actualWidth = width - (leftPadding + rightPadding);
        int actualHeight = height - (topPadding + bottomPadding);

        float radius = Math.min(actualWidth, actualHeight) / 2;

        int cx = leftPadding + actualWidth / 2;
        int cy = topPadding + actualHeight / 2;

        canvas.drawCircle(cx, cy, radius, mInnerPaint);

        mOuterPaint.setColor(mBorderColor);
        radius = radius - mBorderWidth / 2;
        canvas.drawCircle(cx, cy, radius, mOuterPaint);

        mRectF.set(cx - radius, cy - radius, cx + radius, cy + radius);
        mOuterPaint.setColor(mLoadingColor);

        canvas.drawArc(mRectF, 270, calculateSweepAngle(), false, mOuterPaint);
        super.onDraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    private float calculateSweepAngle() {
        if (mLoadingPercentage >= 100) {
            return 360;
        } else {
            return FULL_SWEEP_ANGLE * mLoadingPercentage / 100;
        }
    }
}
