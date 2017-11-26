package com.kop.latte.ec.main.index;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import com.kop.latte.app.Latte;
import com.kop.latte.ec.R;

@SuppressWarnings("unused")
public final class TranslucentBehavior extends CoordinatorLayout.Behavior<Toolbar> {

  //注意这个变量一定要定义成类变量
  private int mOffset = 0;
  //延长滑动过程
  private static final int MORE = 100;
  //初始化一次
  private boolean isInitFinish = false;
  private int mStartOffset;
  private int mEndOffset;
  private RecyclerView mRecyclerView;

  public TranslucentBehavior(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  public boolean layoutDependsOn(CoordinatorLayout parent, Toolbar child, View dependency) {
    return dependency.getId() == R.id.rv_index;
  }

  @Override public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout,
      @NonNull Toolbar child, @NonNull View directTargetChild, @NonNull View target, int axes,
      int type) {
    return true;
  }

  @Override
  public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull Toolbar child,
      @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed,
      int type) {
    if (!isInitFinish) {
      final Context context = Latte.getApplicationContext();
      mEndOffset = context.getResources().getDimensionPixelOffset(R.dimen.header_height) + MORE;
      if (target instanceof RecyclerView) {
        mRecyclerView = (RecyclerView) target;
        mEndOffset = mRecyclerView.getChildAt(0).getHeight() - child.getHeight();
      } else if (target instanceof SwipeRefreshLayout) {
        mRecyclerView = (RecyclerView) ((SwipeRefreshLayout) target).getChildAt(0);
        mEndOffset = mRecyclerView.getChildAt(0).getHeight() - child.getHeight();
      }
      isInitFinish = true;
    }

    mOffset += dyConsumed;

    if (mOffset <= mStartOffset) {
      child.getBackground().setAlpha(0);
    } else if (mOffset > mStartOffset && mOffset < mEndOffset) {
      if (mRecyclerView != null && !mRecyclerView.canScrollVertically(-1)) {
        mOffset = 0;
      }
      final float percent = (float) (mOffset - mStartOffset) / mEndOffset;
      final int alpha = Math.round(percent * 255);
      child.getBackground().setAlpha(alpha);
    } else if (mOffset >= mEndOffset) {
      child.getBackground().setAlpha(255);
    }
  }
}
