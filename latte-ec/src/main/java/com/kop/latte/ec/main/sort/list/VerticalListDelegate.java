package com.kop.latte.ec.main.sort.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.BindView;
import com.kop.latte.delegates.LatteDelegate;
import com.kop.latte.ec.R;
import com.kop.latte.ec.R2;
import com.kop.latte.ec.main.sort.SortDelegate;
import com.kop.latte.net.RestClient;
import com.kop.latte.net.callback.ISuccess;
import com.kop.latte.ui.recycler.MultipleItemEntity;
import java.util.List;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/10/8 17:38
 */
public class VerticalListDelegate extends LatteDelegate {

  @BindView(R2.id.rv_vertical_menu_list) RecyclerView mRvVerticalMenuList;

  @Override public Object setLayout() {
    return R.layout.delegate_vertical_list;
  }

  private void initRecyclerView() {
    final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
    mRvVerticalMenuList.setLayoutManager(linearLayoutManager);
    //屏蔽动画效果
    mRvVerticalMenuList.setItemAnimator(null);
  }

  @Override public void onBindView(@Nullable Bundle savedInstanceState, @Nullable View rootView) {
    initRecyclerView();
  }

  @Override public void onLazyInitView(@Nullable Bundle savedInstanceState) {
    super.onLazyInitView(savedInstanceState);
    RestClient.builder()
        .url("sort_list_data.json")
        .loader(getContext())
        .success(new ISuccess() {
          @Override public void onSuccess(String response) {
            final List<MultipleItemEntity> data =
                new VerticalListDataConverter().setJsonData(response).convert();
            final SortDelegate sortDelegate = getParentDelegate();
            final SortRecyclerAdapter adapter = new SortRecyclerAdapter(data, sortDelegate);
            mRvVerticalMenuList.setAdapter(adapter);
          }
        })
        .build()
        .get();
  }
}
