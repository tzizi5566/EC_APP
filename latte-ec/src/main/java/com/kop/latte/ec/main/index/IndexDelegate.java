package com.kop.latte.ec.main.index;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import butterknife.BindView;
import com.joanzapata.iconify.widget.IconTextView;
import com.kop.latte.delegates.bottom.BottomItemDelegate;
import com.kop.latte.ec.R;
import com.kop.latte.ec.R2;
import com.kop.latte.ec.main.EcBottomDelegate;
import com.kop.latte.ui.recycler.BaseDecoration;
import com.kop.latte.ui.recycler.RgbValue;
import com.kop.latte.ui.refresh.RefreshHandler;
import com.kop.latte.util.log.LatteLogger;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/10/5 17:40
 */
public class IndexDelegate extends BottomItemDelegate implements RefreshHandler.OnDataFinish {

  @BindView(R2.id.rv_index) RecyclerView mRvIndex;
  @BindView(R2.id.srl_index) SwipeRefreshLayout mSrlIndex;
  @BindView(R2.id.icon_index_scan) IconTextView mIconIndexScan;
  @BindView(R2.id.et_search_view) AppCompatEditText mEtSearchView;
  @BindView(R2.id.icon_index_message) IconTextView mIconIndexMessage;
  @BindView(R2.id.tb_index) Toolbar mTbIndex;

  private RefreshHandler mRefreshHandler = null;
  private int mDistanceY = 0;
  private final RgbValue RGB_VALUE = RgbValue.create(255, 124, 2);
  private int mFirstItemBottom;
  private int mTargetHeight;

  private void initRefreshLayout() {
    mSrlIndex.setColorSchemeResources(
        android.R.color.holo_blue_bright,
        android.R.color.holo_orange_light,
        android.R.color.holo_red_light
    );
    mSrlIndex.setProgressViewOffset(true, 120, 200);
  }

  private void initRecyclerView() {
    final GridLayoutManager manager = new GridLayoutManager(getContext(), 4);
    mRvIndex.setLayoutManager(manager);
    mRvIndex.addItemDecoration(
        BaseDecoration.create(ContextCompat.getColor(getContext(), R.color.app_background), 5));
    final EcBottomDelegate ecBottomDelegate = getParentDelegate();
    mRvIndex.addOnItemTouchListener(IndexItemClickListener.create(ecBottomDelegate));
  }

  @Override public void onLazyInitView(@Nullable Bundle savedInstanceState) {
    super.onLazyInitView(savedInstanceState);
    ((AppCompatActivity) getActivity()).setSupportActionBar(mTbIndex);
    initRefreshLayout();
    initRecyclerView();
    mRefreshHandler.setOnDataFinish(this);
    mRefreshHandler.firstPage("index_data.json");
  }

  @Override public Object setLayout() {
    return R.layout.delegate_index;
  }

  @Override public void onBindView(@Nullable Bundle savedInstanceState, @Nullable View rootView) {
    mRefreshHandler =
        RefreshHandler.create(getContext(), mSrlIndex, mRvIndex, new IndexDataConverter());
  }

  @Override public void onFinish() {
    final int tbBottom = mTbIndex.getBottom();
    mRvIndex.getViewTreeObserver().addOnGlobalLayoutListener(
        new ViewTreeObserver.OnGlobalLayoutListener() {
          @Override public void onGlobalLayout() {
            View view = mRvIndex.getChildAt(0);
            if (view == null) {
              return;
            }
            mFirstItemBottom = view.getBottom();
            mTargetHeight = mFirstItemBottom - tbBottom;

            mRvIndex.addOnScrollListener(new RecyclerView.OnScrollListener() {
              @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //增加滑动距离
                mDistanceY += dy;
                LatteLogger.d(mDistanceY);
                //当滑动时，并且距离小于toolbar高度的时候，调整渐变色
                if (mDistanceY > 0 && mDistanceY <= mTargetHeight) {
                  if (!recyclerView.canScrollVertically(-1)) {
                    mDistanceY = 0;
                  }
                  final float scale = (float) mDistanceY / mTargetHeight;
                  final float alpha = scale * 255;
                  mTbIndex.setBackgroundColor(
                      Color.argb((int) alpha, RGB_VALUE.red(), RGB_VALUE.green(),
                          RGB_VALUE.blue()));
                } else if (mDistanceY > mTargetHeight) {
                  mTbIndex.setBackgroundColor(
                      Color.rgb(RGB_VALUE.red(), RGB_VALUE.green(), RGB_VALUE.blue()));
                } else if (mDistanceY <= 0) {
                  mTbIndex.setBackgroundColor(
                      Color.argb(0, RGB_VALUE.red(), RGB_VALUE.green(), RGB_VALUE.blue()));
                }
              }
            });
            mRvIndex.getViewTreeObserver().removeOnGlobalLayoutListener(this);
          }
        });
  }
}
