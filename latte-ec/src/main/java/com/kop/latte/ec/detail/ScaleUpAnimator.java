package com.kop.latte.ec.detail;

import android.animation.ObjectAnimator;
import android.view.View;
import com.daimajia.androidanimations.library.BaseViewAnimator;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/11/18 16:44
 */
public class ScaleUpAnimator extends BaseViewAnimator {

  @Override protected void prepare(View target) {
    getAnimatorAgent().playTogether(
        ObjectAnimator.ofFloat(target, "scaleX", 0.8f, 1f, 1.4f, 1.2f, 1f),
        ObjectAnimator.ofFloat(target, "scaleY", 0.8f, 1f, 1.4f, 1.2f, 1f)
    );
  }
}
