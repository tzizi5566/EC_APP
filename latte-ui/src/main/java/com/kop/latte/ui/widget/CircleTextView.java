package com.kop.latte.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.support.annotation.ColorInt;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/11/17 13:58
 */
public class CircleTextView extends AppCompatTextView {

  private final Paint PAINT;
  private final PaintFlagsDrawFilter FILTER;

  public CircleTextView(Context context) {
    this(context, null);
  }

  public CircleTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
    PAINT = new Paint(Paint.ANTI_ALIAS_FLAG);
    FILTER = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
    PAINT.setColor(Color.WHITE);
  }

  public void setCircleBackground(@ColorInt int color) {
    PAINT.setColor(color);
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    final int width = getMeasuredWidth();
    final int height = getMeasuredHeight();
    final int max = Math.max(width, height);
    setMeasuredDimension(max, max);
  }

  @Override public void draw(Canvas canvas) {
    canvas.setDrawFilter(FILTER);
    canvas.drawCircle(
        getWidth() / 2,
        getHeight() / 2,
        Math.max(getWidth(), getHeight()) / 2,
        PAINT);
    super.draw(canvas);
  }
}
