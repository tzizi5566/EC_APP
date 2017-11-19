package com.kop.latte.ui.scanner;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import com.kop.latte.R;
import com.kop.latte.util.log.LatteLogger;
import me.dm7.barcodescanner.core.ViewFinderView;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/11/15 15:06
 */
public class LatteViewFinderView extends ViewFinderView {

  private int cntr = 0;
  private boolean goingup = false;
  private static final long ANIMATION_DELAY = 50L;
  private static final int POINT_SIZE = 10;

  public LatteViewFinderView(Context context) {
    this(context, null);
  }

  public LatteViewFinderView(Context context, AttributeSet attributeSet) {
    super(context, attributeSet);
  }

  @Override public void drawLaser(Canvas canvas) {
    int defaultBorderStrokeWidth = getResources().getInteger(R.integer.viewfinder_border_width);
    Rect framingRect = this.getFramingRect();
    int distance = framingRect.height() / 2 - defaultBorderStrokeWidth;
    int middle = framingRect.height() / 2 + framingRect.top;
    middle = middle + cntr;
    if ((cntr < distance) && (!goingup)) {
      canvas.drawRect(
          framingRect.left + 8,
          middle - 1,
          framingRect.right - 8,
          middle + 2,
          mLaserPaint);
      cntr = cntr + 6;
    }

    if ((cntr >= distance) && (!goingup)) goingup = true;

    if ((cntr > -distance) && (goingup)) {
      canvas.drawRect(
          framingRect.left + 8,
          middle - 1,
          framingRect.right - 8,
          middle + 2,
          mLaserPaint);
      cntr = cntr - 6;
    }

    if ((cntr <= -distance) && (goingup)) goingup = false;

    postInvalidateDelayed(ANIMATION_DELAY,
        framingRect.left - POINT_SIZE,
        framingRect.top - POINT_SIZE,
        framingRect.right + POINT_SIZE,
        framingRect.bottom + POINT_SIZE);

    LatteLogger.d(cntr + "");
  }
}
