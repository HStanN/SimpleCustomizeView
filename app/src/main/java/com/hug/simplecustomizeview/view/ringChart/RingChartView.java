package com.hug.simplecustomizeview.view.ringChart;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.OvershootInterpolator;

import com.hug.simplecustomizeview.R;

import java.util.List;

/**
 * Created by HStan on 2017/8/7.
 * <p>
 * 仿支付宝月账单中的环形图效果
 */

public class RingChartView extends View {
    private Context mContext;
    private Paint paint;
    private Paint textPaint;
    private Path wcPath;


    private float textRadius;
    private int extendRadius = 30;
    private int lineMarign = 30;

    private float mTextSize;
    private int mTextColor = Color.parseColor("#666666");
    private float mChartRadius;
    private float mCenterRadius;

    private final int DEFAULT_CENTER_RADIUS = 60;
    private final int DEFAULT_RING_RADIUS = 200;
    private final int DEFAULT_TEXTSIZE = 30;

    private List<ChartItem> mDatas;

    public RingChartView(Context context) {
        this(context, null);
    }

    public RingChartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RingChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mContext = context;
        TypedArray ta = mContext.getTheme().obtainStyledAttributes(attrs,R.styleable.RingChartView,0,0);
        mChartRadius = ta.getDimension(R.styleable.RingChartView_bigRingRadius,DEFAULT_RING_RADIUS);
        mCenterRadius = ta.getDimension(R.styleable.RingChartView_smallCircleRadius,DEFAULT_CENTER_RADIUS);
        mTextSize =ta.getDimension(R.styleable.RingChartView_ringTextSize,DEFAULT_TEXTSIZE);
        ta.recycle();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(mTextSize);
        textPaint.setColor(mTextColor);

        wcPath = new Path();
    }

    public void setChartData(List<ChartItem> list) {
        mDatas = list;
        invalidate();
    }

    /**
     * @param textSize px value
     *
     * */
    public void setTextSize(float textSize){
        textPaint.setTextSize(textSize);
        invalidate();
    }

    /**
     * @param radius px value
     *
     * */
    public void setRingRadius(int radius){
        mChartRadius = radius;
        invalidate();
    }

    /**
     * @param radius px value
     *
     * */
    public void setCenterRadius(int radius){
        mCenterRadius = radius;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mDatas != null) {
            textRadius = mChartRadius + 30;
            canvas.translate(getMeasuredWidth() / 2, getMeasuredHeight() / 2);
            canvas.drawColor(Color.WHITE);
            RectF rectF = new RectF(-mChartRadius,
                    -mChartRadius,
                    +mChartRadius,
                    +mChartRadius);
            float preRadius = 0;
            for (int i = 0; i < mDatas.size(); i++) {
                paint.setColor(mContext.getResources().getColor(mDatas.get(i).getColor()));
                float radius = (float) (360 * mDatas.get(i).getPercent());
                float halfRadius = (float) Math.toRadians((preRadius + radius / 2));
                float x = (float) (Math.cos(halfRadius) * textRadius);
                float y = (float) (Math.sin(halfRadius) * textRadius);
                float x1 = 0;
                float y1 = 0;
                if (x >= 0 && y >= 0) {
                    x1 = x + extendRadius;
                    y1 = y + extendRadius;
                }
                if (x > 0 && y < 0) {
                    x1 = x + extendRadius;
                    y1 = y - extendRadius;
                }
                if (x <= 0 && y <= 0) {
                    x1 = x - extendRadius;
                    y1 = y - extendRadius;
                }
                if (x < 0 && y > 0) {
                    x1 = x - extendRadius;
                    y1 = y + extendRadius;
                }

                paint.setStrokeWidth(15);
                paint.setStrokeCap(Paint.Cap.ROUND);
                canvas.drawPoint(x, y, paint);
                paint.setStrokeWidth(2);
                paint.setStrokeCap(Paint.Cap.ROUND);
                canvas.drawLine(x, y, x1, y1, paint);
                if (x > 0) {
                    canvas.drawLine(x1, y1, getMeasuredWidth() / 2 - lineMarign, y1, paint);
                    textPaint.setTextAlign(Paint.Align.RIGHT);
                    canvas.drawText("" + mDatas.get(i).getAmount(), getMeasuredWidth() / 2 - lineMarign, y1 - 5,
                            textPaint);
                    canvas.drawText(mDatas.get(i).getName(), getMeasuredWidth() / 2 - lineMarign, y1 + 30,
                            textPaint);
                } else {
                    canvas.drawLine(x1, y1, -getMeasuredWidth() / 2 + lineMarign, y1, paint);
                    textPaint.setTextAlign(Paint.Align.LEFT);
                    canvas.drawText("" + mDatas.get(i).getAmount(), -getMeasuredWidth() / 2 + lineMarign, y1 - 5,
                            textPaint);
                    canvas.drawText("" + mDatas.get(i).getName(), -getMeasuredWidth() / 2 + lineMarign, y1 +
                            30, textPaint);
                }
                canvas.drawArc(rectF, preRadius, radius, true, paint);
                preRadius = radius + preRadius;
            }
            wcPath.addCircle(0, 0, mCenterRadius, Path.Direction.CW);
            paint.setColor(Color.WHITE);
            canvas.drawPath(wcPath, paint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureWidth(), getWrapHeight());
    }

    private int getWrapHeight() {
        return (int) (2 * mChartRadius + 2 * Math.sqrt(extendRadius * extendRadius + extendRadius * extendRadius) +
                mTextSize + 80);
    }

    private int measureWidth() {
        return getScreenWidth();
    }

    private int getScreenWidth() {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager manager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }
}
