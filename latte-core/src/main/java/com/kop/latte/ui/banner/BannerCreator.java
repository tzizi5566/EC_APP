package com.kop.latte.ui.banner;

import com.ToxicBakery.viewpager.transforms.DefaultTransformer;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.kop.latte.R;
import java.util.ArrayList;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/10/6 20:22
 */
public class BannerCreator {

  public static void setDefault(ConvenientBanner<String> convenientBanner,
      ArrayList<String> banners, OnItemClickListener clickListener) {
    convenientBanner.setPages(new HolderCreator(), banners)
        .setPageIndicator(new int[] { R.drawable.dot_normal, R.drawable.dot_focus })
        .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
        .setOnItemClickListener(clickListener)
        .setPageTransformer(new DefaultTransformer());
        //.startTurning(3000)
        //.setCanLoop(true);
  }
}
