package hmaliborski.com.circleview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import hmaliborski.com.circleview.R;

public class CircleView extends View {
    private static final String TAG = CircleView.class.getSimpleName();

    private int mCircleColor;
    private boolean isBorder;
    private int mBorderColor;

    private Paint mPaint;

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet set) {
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(set, R.styleable.CircleView, 0, 0);

        try {
            mCircleColor = typedArray.getInt(R.styleable.CircleView_circleColor, Color.BLACK);
            isBorder = typedArray.getBoolean(R.styleable.CircleView_isBorder, false);
            mBorderColor = typedArray.getColor(R.styleable.CircleView_borderColor, Color.BLACK);
        } finally {
            typedArray.recycle();
        }

        mPaint = new Paint();
        mPaint.setColor(mCircleColor);
        mPaint.set
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        int leftPadding = getPaddingLeft();
        int rightPadding = getPaddingRight();
        int topPadding = getPaddingTop();
        int bottomPadding = getPaddingBottom();

        int actualWidth = width - (leftPadding + rightPadding);
        int actualHeight = height - (topPadding + bottomPadding);

        int radius = Math.min(actualWidth, actualHeight) / 2;

        int cx = leftPadding + actualWidth / 2;
        int cy = topPadding + actualHeight / 2;

        canvas.drawCircle(cx, cy, radius, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    public int getCircleColor() {
        return mCircleColor;
    }

    public void setCircleColor(int circleColor) {
        mCircleColor = circleColor;
    }
}
