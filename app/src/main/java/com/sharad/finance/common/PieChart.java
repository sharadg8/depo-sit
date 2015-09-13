package com.sharad.finance.common;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;

import com.sharad.finance.deposit.R;

import java.util.Random;

/**
 * Created by Sharad on 13-Sep-15.
 */
public class PieChart extends View {
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float[] value_degree;
    private int[] color_id;
    RectF rectf;
    float temp = 0;

    public PieChart(Context context, float[] values) {
        super(context);

        float total = 0;
        for (int i = 0; i < values.length; i++) {
            total += values[i];
        }

        Random r = new Random();
        TypedArray ta = context.getResources().obtainTypedArray(R.array.colors);
        value_degree = new float[values.length];
        color_id = new int[values.length];
        for (int i = 0; i < values.length; i++) {
            value_degree[i] = 360 * (values[i] / total);
            color_id[i] = ta.getColor(r.nextInt(ta.length()), 0);
        }
        ta.recycle();
        color_id[0] = Color.argb(230, 255, 255, 255);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Rect rect = new Rect();
        this.getDrawingRect(rect);
        rectf = new RectF(rect);
        float diameter = Math.min(rectf.width(), rectf.height());
        rectf.left = (rectf.right - rectf.left - diameter) / 2;
        rectf.top = (rectf.bottom - rectf.top - diameter) / 2;
        rectf.right = rectf.left + diameter;
        rectf.bottom = rectf.top + diameter;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        temp = 0;
        for (int i = 0; i < value_degree.length; i++) {
            paint.setColor(color_id[i]);
            canvas.drawArc(rectf, temp, value_degree[i], true, paint);
            temp += value_degree[i];
        }
    }
}
