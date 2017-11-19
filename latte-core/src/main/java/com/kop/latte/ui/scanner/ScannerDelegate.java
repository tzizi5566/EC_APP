package com.kop.latte.ui.scanner;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.kop.latte.delegates.LatteDelegate;
import com.kop.latte.ui.camera.RequestCodes;
import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/11/15 15:02
 */
public class ScannerDelegate extends LatteDelegate implements ZBarScannerView.ResultHandler {

  private ScanView mScanView;

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (mScanView == null) {
      mScanView = new ScanView(getContext());
    }
    mScanView.setAutoFocus(true);
    mScanView.setResultHandler(this);
  }

  @Override public Object setLayout() {
    return mScanView;
  }

  @Override public void onBindView(@Nullable Bundle savedInstanceState, @Nullable View rootView) {

  }

  @Override public void onResume() {
    super.onResume();
    if (mScanView != null) {
      mScanView.startCamera();
    }
  }

  @Override public void onPause() {
    super.onPause();
    if (mScanView != null) {
      mScanView.stopCameraPreview();
      mScanView.stopCamera();
    }
  }

  @Override public void handleResult(Result result) {
    final Bundle bundle = new Bundle();
    bundle.putString("SCAN_RESULT", result.getContents());
    setFragmentResult(RequestCodes.SCAN, bundle);
    getSupportDelegate().pop();
  }
}
