package com.hug.simplecustomizeview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.hug.simplecustomizeview.R;

/**
 * Created by HStan on 2017/7/26.
 * <p>
 * 这是一个可以为图片添加渐变蒙板的ImageView
 */

public class GradientImageView extends AppCompatImageView {
    private Paint paint;
    private Shader shader;

    private int startColor;
    private int endColor;
    private int gradientStart;
    private int gradientType;
    private float radius;
    private boolean gradientEnable;

    private Point startPoint;
    private Point endPoint;


    public static final int LINEAR = 1;
    public static final int RADIAL = 2;
    public static final int SWEEP = 3;

    public static final int LEFT_TOP = 1;
    public static final int LEFT_BOTTOM = 2;
    public static final int RIGHT_TOP = 3;
    public static final int RIGHT_BOTTOM = 4;
    public static final int CENTER = 5;

    public GradientImageView(Context context) {
        this(context, null);
    }

    public GradientImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GradientImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.GradientImageView, 0, 0);
        startColor = ta.getColor(R.styleable.GradientImageView_startColor, Color.parseColor("#843F51B5"));
        endColor = ta.getColor(R.styleable.GradientImageView_endColor, Color.parseColor("#84FF4081"));
        gradientStart = ta.getInt(R.styleable.GradientImageView_gradientStart, LEFT_TOP);
        gradientType = ta.getInt(R.styleable.GradientImageView_gradientType, LINEAR);
        gradientEnable = ta.getBoolean(R.styleable.GradientImageView_gradientEnable, true);
        ta.recycle();
    }

    private void initPoint() {
        startPoint = new Point();
        endPoint = new Point();
        double width = getMeasuredWidth() - getPaddingRight() - getPaddingLeft();
        double height = getMeasuredHeight() - getPaddingBottom() - getPaddingTop();
        radius = (float) Math.sqrt(width * width + height * height);
        switch (gradientStart) {
            case LEFT_TOP:
                if (gradientType == LINEAR) {
                    endPoint.x = getMeasuredWidth() - getPaddingRight();
                    endPoint.y = getMeasuredHeight() - getPaddingBottom();
                }
                startPoint.x = getPaddingLeft();
                startPoint.y = getPaddingTop();
                break;
            case LEFT_BOTTOM:
                if (gradientType == LINEAR) {
                    endPoint.x = getMeasuredWidth() - getPaddingRight();
                    endPoint.y = getPaddingTop();
                }
                startPoint.x = getPaddingLeft();
                startPoint.y = getMeasuredHeight() - getPaddingBottom();
                break;
            case RIGHT_TOP:
                if (gradientType == LINEAR) {
                    endPoint.x = getPaddingLeft();
                    endPoint.y = getMeasuredHeight() - getPaddingBottom();
                }
                startPoint.x = getMeasuredWidth() - getPaddingRight();
                startPoint.y = getPaddingTop();
                break;
            case RIGHT_BOTTOM:
                if (gradientType == LINEAR) {
                    endPoint.x = getPaddingLeft();
                    endPoint.y = getPaddingTop();
                }
                startPoint.x = getMeasuredWidth() - getPaddingRight();
                startPoint.y = getMeasuredHeight() - getPaddingBottom();
                break;
            case CENTER:
                radius = (float) Math.sqrt(width * width + height * height) / 2;
                startPoint.x = (getMeasuredWidth() - getPaddingRight() + getPaddingLeft()) / 2;
                startPoint.y = (getMeasuredHeight() - getPaddingBottom() + getPaddingTop()) / 2;
                break;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (gradientEnable) {
            paint.setShader(shader);
            canvas.drawRect(getPaddingLeft(), getPaddingTop(),
                    getMeasuredWidth() - getPaddingRight(), getMeasuredHeight() - getPaddingBottom(), paint);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initPoint();
        initShader();
    }

    private void initShader() {
        switch (gradientType) {
            case LINEAR:
                shader = new LinearGradient(startPoint.x, startPoint.y, endPoint.x, endPoint.y, startColor,
                        endColor, Shader.TileMode.CLAMP);
                break;
            case RADIAL:
                shader = new RadialGradient(startPoint.x, startPoint.y, radius, startColor, endColor, Shader
                        .TileMode.CLAMP);
                break;
            case SWEEP:
                shader = new SweepGradient(startPoint.x, startPoint.y, startColor, endColor);
                Path path = new Path();
                break;
        }
    }
}
