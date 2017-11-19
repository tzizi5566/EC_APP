package com.kop.latte.ui.scanner;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import me.dm7.barcodescanner.core.IViewFinder;
import me.dm7.barcodescanner.core.ViewFinderView;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/11/15 15:05
 */
public class ScanView extends ZBarScannerView {

  public ScanView(Context context) {
    this(context, null);
  }

  public ScanView(Context context, AttributeSet attributeSet) {
    super(context, attributeSet);
  }

  @Override protected IViewFinder createViewFinderView(Context context) {
    ViewFinderView viewFinderView = new LatteViewFinderView(context);
    viewFinderView.setSquareViewFinder(true);
    viewFinderView.setLaserEnabled(true);
    viewFinderView.setBorderColor(Color.YELLOW);
    viewFinderView.setLaserColor(Color.YELLOW);
    return viewFinderView;
  }
}
