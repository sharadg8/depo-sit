package com.sharad.finance.common;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
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
public class Progress extends View {
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float value_degree;
    private int color_id;
    private int cirColor_id;
    private Bitmap shadow;
    private RectF rectPro;
    private RectF rectCir;
    private boolean showBackground;

    public Progress(Context context, boolean background) {
        super(context);

        showBackground = background;

        cirColor_id = context.getResources().getColor(R.color.action_circle);

        TypedArray ta = context.getResources().obtainTypedArray(R.array.colors);
        List<Integer> color_list = new ArrayList<>();
        for (int i = 0; i < ta.length(); i++) {
            color_list.add(ta.getColor(i, 0));
        }
        ta.recycle();
        Collections.shuffle(color_list);
        value_degree = 360 * (30 / 100);
        color_id = color_list.get(0);
    }

    public void setProgress(int value) {
        value_degree = 360 * ((float)value / 100);
        invalidate();
    }

    public void setProgress(int value, int max) {
        value_degree = 360 * ((float)value / max);
        invalidate();
    }

    public void setColor(int value) {
        color_id = value;
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
        rectPro = new RectF(rectCir);
        rectPro.left += (rectCir.width() * scale);
        rectPro.top += (rectCir.height() * scale);
        rectPro.right -= (rectCir.width() * scale);
        rectPro.bottom -= (rectCir.height() * scale);

        shadow = Bitmap.createBitmap((int)diameter, (int)diameter, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(shadow);
        paint.setColor(cirColor_id);
        if(showBackground) {
            canvas.drawCircle(rectCir.centerX(), rectCir.centerY(), (rectCir.width() / 2), paint);
        }
        paint.setStrokeWidth(0.07f * diameter);
        paint.setStrokeCap(Paint.Cap.BUTT);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(color_id);
        paint.setAlpha(25);
        canvas.drawArc(rectPro, -90, 360, true, paint);
        paint.setAlpha(255);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(shadow, rectCir.left, rectCir.top, paint);
        paint.setColor(color_id);
        Path path = new Path();
        path.arcTo(rectPro, -90, value_degree, true);
        canvas.drawPath(path, paint);
    }
}
