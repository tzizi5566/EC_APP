package com.kop.latte.ui.loader;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import com.kop.latte.R;
import com.wang.avi.AVLoadingIndicatorView;
import java.util.ArrayList;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/8/8 21:57
 */
public class LatteLoader {

  private static final int LOADER_SCALE = 8;
  private static final int LOADER_OFFSET_SCALE = 10;

  private static final ArrayList<AppCompatDialog> LOADERS = new ArrayList<>();

  private static final String DEFALUT_LOADER = LoaderStyle.BallPulseIndicator.name();

  public static void showLoading(Context context, String type) {
    final AppCompatDialog dialog = new AppCompatDialog(context, R.style.dialog);
    final AVLoadingIndicatorView avLoadingIndicatorView = LoaderCreator.create(type, context);
    dialog.setContentView(avLoadingIndicatorView);
    dialog.setCanceledOnTouchOutside(false);

    //int deviceWidth = DimenUtil.getScreenWidth();
    //int deviceHeight = DimenUtil.getScreenHeight();
    //
    //final Window window = dialog.getWindow();
    //if (window != null) {
    //  WindowManager.LayoutParams lp = window.getAttributes();
    //  lp.width = deviceWidth / LOADER_SCALE;
    //  lp.height = deviceHeight / LOADER_SCALE;
    //  //lp.height = lp.height + deviceHeight / LOADER_OFFSET_SCALE;
    //  lp.gravity = Gravity.CENTER;
    //}

    LOADERS.add(dialog);
    dialog.show();
  }

  public static void showLoading(Context context) {
    showLoading(context, DEFALUT_LOADER);
  }

  public static void showLoading(Context context, Enum<LoaderStyle> type) {
    showLoading(context, type.name());
  }

  public static void stopLoading() {
    for (AppCompatDialog dialog : LOADERS) {
      if (dialog != null) {
        if (dialog.isShowing()) {
          dialog.cancel();
        }
      }
    }
  }
}
