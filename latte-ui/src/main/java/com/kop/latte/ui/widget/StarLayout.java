package com.kop.latte.ui.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import com.blankj.utilcode.util.ConvertUtils;
import com.joanzapata.iconify.widget.IconTextView;
import com.kop.latte.ui.R;
import java.util.ArrayList;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/11/13 13:50
 */
public class StarLayout extends LinearLayoutCompat implements View.OnClickListener {

  private static final CharSequence ICON_UN_SELECT = "{fa-star-o}";
  private static final CharSequence ICON_SELECTED = "{fa-star}";
  private static final int STAR_TOTAL_COUNT = 5;
  private final ArrayList<IconTextView> STARS = new ArrayList<>();

  public StarLayout(Context context) {
    this(context, null);
  }

  public StarLayout(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public StarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    initStarIcon();
  }

  private void initStarIcon() {
    for (int i = 0; i < STAR_TOTAL_COUNT; i++) {
      final IconTextView textView = new IconTextView(getContext());
      textView.setGravity(Gravity.CENTER);
      final LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
          ViewGroup.LayoutParams.MATCH_PARENT);
      params.weight = 1;
      textView.setLayoutParams(params);
      textView.setTextSize(ConvertUtils.sp2px(16f));
      textView.setText(ICON_UN_SELECT);
      textView.setTag(R.id.star_count, i);
      textView.setTag(R.id.star_is_select, false);
      textView.setOnClickListener(this);
      STARS.add(textView);
      addView(textView);
    }
  }

  public int getStarCount() {
    int count = 0;
    for (int i = 0; i < STAR_TOTAL_COUNT; i++) {
      final IconTextView textView = STARS.get(i);
      final boolean isSelect = (boolean) textView.getTag(R.id.star_is_select);
      if (isSelect) {
        count++;
      }
    }
    return count;
  }

  private void selectStar(int count) {
    for (int i = 0; i <= count; i++) {
      if (i <= count) {
        final IconTextView textView = STARS.get(i);
        textView.setText(ICON_SELECTED);
        textView.setTextColor(Color.RED);
        textView.setTag(R.id.star_is_select, true);
      }
    }
  }

  private void unSelectStar(int count) {
    for (int i = 0; i < STAR_TOTAL_COUNT; i++) {
      if (i >= count) {
        final IconTextView textView = STARS.get(i);
        textView.setText(ICON_UN_SELECT);
        textView.setTextColor(Color.GRAY);
        textView.setTag(R.id.star_is_select, false);
      }
    }
  }

  @Override public void onClick(View v) {
    final IconTextView textView = (IconTextView) v;
    final int count = (int) textView.getTag(R.id.star_count);
    final boolean isSelect = (boolean) textView.getTag(R.id.star_is_select);
    if (!isSelect) {
      selectStar(count);
    } else {
      unSelectStar(count);
    }
  }
}
