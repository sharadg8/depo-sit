package com.sharad.finance.common;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.view.View;

import com.sharad.finance.deposit.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Sharad on 13-Sep-15.
 */
public class PieChart extends View {
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float[] value_degree;
    private List<Integer> color_id;
    private int cirColor_id;
    private Bitmap shadow;
    RectF rectPie;
    RectF rectCir;
    float temp = 0;
    private boolean showBackground;

    public PieChart(Context context, boolean background) {
        super(context);

        showBackground = background;
        cirColor_id = context.getResources().getColor(R.color.action_circle);

        TypedArray ta = context.getResources().obtainTypedArray(R.array.colors);
        color_id = new ArrayList<>();
        for (int i = 0; i < ta.length(); i++) {
            color_id.add(ta.getColor(i, 0));
        }
        ta.recycle();
        Collections.shuffle(color_id);
    }

    public void setValues(float[] values) {
        float total = 0;
        for (int i = 0; i < values.length; i++) {
            total += values[i];
        }

        value_degree = new float[values.length];
        for (int i = 0; i < values.length; i++) {
            value_degree[i] = 360 * (values[i] / total);
        }

        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Rect rect = new Rect();
        this.getDrawingRect(rect);
        rectCir = new RectF(rect);
        float diameter = Math.min(rectCir.width(), rectCir.height());
        rectCir.left = (rectCir.right - rectCir.left - diameter) / 2;
        rectCir.top = (rectCir.bottom - rectCir.top - diameter) / 2;
        rectCir.right = rectCir.left + diameter;
        rectCir.bottom = rectCir.top + diameter;

        float scale = 0.15f;
        rectPie = new RectF(rectCir);
        rectPie.left += (rectCir.width() * scale);
        rectPie.top += (rectCir.height() * scale);
        rectPie.right -= (rectCir.width() * scale);
        rectPie.bottom -= (rectCir.height() * scale);

        shadow = Bitmap.createBitmap((int)diameter, (int)diameter, Bitmap.Config.ARGB_8888);
        if(showBackground) {
            Canvas canvas = new Canvas(shadow);
            paint.setColor(cirColor_id);
            canvas.drawCircle(rectCir.centerX(), rectCir.centerY(), (rectCir.width() / 2), paint);
            paint.setShadowLayer(2.0f, 6.0f, 6.0f, Color.LTGRAY);
            canvas.drawCircle((diameter / 2), (diameter / 2), (rectPie.width() / 2), paint);
            paint.setShadowLayer(0.0f, 0.0f, 0.0f, Color.BLACK);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(shadow, rectCir.left, rectCir.top, paint);
        temp = 0;
        for (int i = 0; i < value_degree.length; i++) {
            paint.setColor(color_id.get(i));
            canvas.drawArc(rectPie, temp, value_degree[i], true, paint);
            temp += value_degree[i];
        }
    }
}
