package com.sharad.finance.common;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;

import com.sharad.finance.deposit.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Sharad on 13-Sep-15.
 */
public class Logo extends View {
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private List<Integer> color_id;
    private int cirColor_id;
    private Bitmap shadow;
    private RectF rectLogo;
    private RectF rectCir;

    public Logo(Context context) {
        super(context);

        cirColor_id = context.getResources().getColor(R.color.action_circle);

        TypedArray ta = context.getResources().obtainTypedArray(R.array.colors);
        color_id = new ArrayList<>();
        for (int i = 0; i < ta.length(); i++) {
            color_id.add(ta.getColor(i, 0));
        }
        ta.recycle();
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
        rectLogo = new RectF(rectCir);
        rectLogo.left += (rectCir.width() * scale);
        rectLogo.top += (rectCir.height() * scale);
        rectLogo.right -= (rectCir.width() * scale);
        rectLogo.bottom -= (rectCir.height() * scale);

        shadow = Bitmap.createBitmap((int)diameter, (int)diameter, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(shadow);
        paint.setColor(cirColor_id);
        canvas.drawCircle(rectCir.centerX(), rectCir.centerY(), (rectCir.width() / 2), paint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(shadow, rectCir.left, rectCir.top, paint);
    }
}
