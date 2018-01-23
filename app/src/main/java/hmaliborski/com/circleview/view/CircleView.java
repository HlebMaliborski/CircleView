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

import hmaliborski.com.circleview.R;

public class CircleView extends View {
    private static final String TAG = CircleView.class.getSimpleName();

    private String mTitleText;

    private int mCircleColor;
    private int mLoadingColor;
    private int borderColor;
    private int mTitleColor;
    private float mBorderWidth;
    private float mTitleSize;

    private TextPaint mTextPaint;
    private Paint mInnerPaint;
    private Paint mOuterPaint;
    private RectF mRectF;

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet set) {
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(set, R.styleable.CircleView, 0, 0);


        try {
            mTitleColor = typedArray.getInt(R.styleable.CircleView_titleColor, Color.BLACK);
            mLoadingColor = typedArray.getInt(R.styleable.CircleView_loadingColor, Color.BLACK);
            mCircleColor = typedArray.getInt(R.styleable.CircleView_circleColor, Color.BLACK);
            borderColor = typedArray.getColor(R.styleable.CircleView_borderColor, Color.BLACK);
            mBorderWidth = typedArray.getFloat(R.styleable.CircleView_borderWidth, 1);
            mTitleSize = typedArray.getFloat(R.styleable.CircleView_titleSize, 18);
            mTitleText = typedArray.getString(R.styleable.CircleView_titleText);
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

        mTextPaint = new TextPaint();
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setLinearText(true);
        mTextPaint.setColor(mTitleColor);
        mTextPaint.setTextSize(mTitleSize);
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

        float radius = Math.min(actualWidth, actualHeight) / 2;

        int cx = leftPadding + actualWidth / 2;
        int cy = topPadding + actualHeight / 2;

        canvas.drawCircle(cx, cy, radius, mInnerPaint);

        mOuterPaint.setColor(borderColor);
        radius = radius - mBorderWidth / 2;
        canvas.drawCircle(cx, cy, radius, mOuterPaint);

        mRectF.set(cx - radius, cy - radius, cx + radius, cy + radius);
        mOuterPaint.setColor(mLoadingColor);

        canvas.drawArc(mRectF, 0, 30, false, mOuterPaint);
        canvas.drawText(mTitleText, cx, cy, mTextPaint);
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
