package com.kop.latte.ec.main.index;

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
import android.widget.Toast;
import butterknife.BindView;
import butterknife.OnClick;
import com.blankj.utilcode.util.ConvertUtils;
import com.joanzapata.iconify.widget.IconTextView;
import com.kop.latte.delegates.bottom.BottomItemDelegate;
import com.kop.latte.ec.R;
import com.kop.latte.ec.R2;
import com.kop.latte.ec.main.EcBottomDelegate;
import com.kop.latte.ec.main.index.search.SearchDelegate;
import com.kop.latte.ui.recycler.BaseDecoration;
import com.kop.latte.ui.refresh.RefreshHandler;
import com.kop.latte.util.callback.CallbackManager;
import com.kop.latte.util.callback.CallbackType;
import com.kop.latte.util.callback.IGlobalCallback;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/10/5 17:40
 */
public class IndexDelegate extends BottomItemDelegate implements View.OnFocusChangeListener {

  @BindView(R2.id.rv_index) RecyclerView mRvIndex;
  @BindView(R2.id.srl_index) SwipeRefreshLayout mSrlIndex;
  @BindView(R2.id.et_search_view) AppCompatEditText mEtSearchView;
  @BindView(R2.id.icon_index_message) IconTextView mIconIndexMessage;
  @BindView(R2.id.tb_index) Toolbar mTbIndex;

  private RefreshHandler mRefreshHandler = null;

  @OnClick(R2.id.icon_index_scan)
  void onClickScanQrCode() {
    startScanWithCheck(this.getParentDelegate());
  }

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
        BaseDecoration.create(ContextCompat.getColor(getContext(), android.R.color.white),
            ConvertUtils.dp2px(1f)));
    final EcBottomDelegate ecBottomDelegate = getParentDelegate();
    mRvIndex.addOnItemTouchListener(IndexItemClickListener.create(ecBottomDelegate));
  }

  @Override public void onLazyInitView(@Nullable Bundle savedInstanceState) {
    super.onLazyInitView(savedInstanceState);
    ((AppCompatActivity) getActivity()).setSupportActionBar(mTbIndex);
    initRefreshLayout();
    initRecyclerView();
    mRefreshHandler.firstPage("index_data.json");
  }

  @Override public Object setLayout() {
    return R.layout.delegate_index;
  }

  @Override public void onBindView(@Nullable Bundle savedInstanceState, @Nullable View rootView) {
    mRefreshHandler =
        RefreshHandler.create(getContext(), mSrlIndex, mRvIndex, new IndexDataConverter());

    CallbackManager.getInstance().addCallback(CallbackType.ON_SCAN, new IGlobalCallback<String>() {
      @Override public void executeCallback(@Nullable String args) {
        Toast.makeText(getContext(), args, Toast.LENGTH_LONG).show();
      }
    });

    mEtSearchView.setOnFocusChangeListener(this);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    final Toolbar toolbar = view.findViewById(R.id.tb_index);
    toolbar.getBackground().setAlpha(0);
  }

  @Override public void onFocusChange(View v, boolean hasFocus) {
    if (hasFocus) {
      getParentDelegate().getSupportDelegate().start(new SearchDelegate());
    }
  }
}
