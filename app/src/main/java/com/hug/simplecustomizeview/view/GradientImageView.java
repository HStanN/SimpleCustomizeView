package com.hug.simplecustomizeview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.hug.simplecustomizeview.R;

/**
 * Created by HStan on 2017/7/26.
 *
 * 这是一个可以为图片添加渐变蒙板的ImageView
 *
 */

public class GradientImageView extends AppCompatImageView {
    private Paint paint;
    private Shader shader;
    private LinearGradient linearGradient;

    private int startColor;
    private int endColor;
    private int gradientStart;
    private boolean gradientEnable;

    public static final int LEFT_TOP = 1;
    public static final int LEFT_BOTTOM = 2;
    public GradientImageView(Context context) {
        this(context,null);
    }

    public GradientImageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public GradientImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.GradientImageView,0,0);
        startColor = ta.getColor(R.styleable.GradientImageView_startColor,Color.parseColor("#843F51B5"));
        endColor = ta.getColor(R.styleable.GradientImageView_endColor,Color.parseColor("#84FF4081"));
        gradientStart = ta.getInt(R.styleable.GradientImageView_gradientStart,LEFT_TOP);
        gradientEnable = ta.getBoolean(R.styleable.GradientImageView_gradientEnable,true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (gradientEnable) {

            switch (gradientStart){
                case LEFT_TOP:
                    shader = new LinearGradient(getPaddingLeft() , getPaddingTop(),
                            getMeasuredWidth() - getPaddingRight(), getMeasuredHeight() - getPaddingBottom(),
                            startColor,endColor, Shader.TileMode.CLAMP);
                    break;
                case LEFT_BOTTOM:
                    shader = new LinearGradient(getPaddingLeft(),  getMeasuredHeight() - getPaddingBottom(),
                            getMeasuredWidth() - getPaddingRight() , 0,
                            startColor,endColor, Shader.TileMode.CLAMP);
                    break;
            }
            paint.setShader(shader);
            canvas.drawRect(getPaddingLeft() , getPaddingTop(),
                    getMeasuredWidth() - getPaddingRight(), getMeasuredHeight() - getPaddingBottom(), paint);
        }
    }
}
