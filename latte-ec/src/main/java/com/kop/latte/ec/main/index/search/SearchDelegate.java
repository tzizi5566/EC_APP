package com.kop.latte.ec.main.index.search;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.OnClick;
import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.StringUtils;
import com.choices.divider.Divider;
import com.choices.divider.DividerItemDecoration;
import com.kop.latte.delegates.LatteDelegate;
import com.kop.latte.ec.R;
import com.kop.latte.ec.R2;
import com.kop.latte.net.rx.RxRestClient;
import com.kop.latte.ui.loader.LatteLoader;
import com.kop.latte.ui.recycler.MultipleItemEntity;
import com.kop.latte.util.storage.LattePreference;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/11/16 16:10
 */
public class SearchDelegate extends LatteDelegate {

  @BindView(R2.id.et_search_view) AppCompatEditText mEtSearchView;
  @BindView(R2.id.tb_main_page) Toolbar mTbMainPage;
  @BindView(R2.id.rv_search) RecyclerView mRvSearch;

  @OnClick(R2.id.tv_top_search)
  void onClickSearch() {
    final String searchItemText = mEtSearchView.getText().toString();
    RxRestClient.builder()
        .loader(getContext())
        .url("about.json")
        .params("search", searchItemText)
        .build()
        .get()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<String>() {
          @Override public void onSubscribe(Disposable d) {

          }

          @Override public void onNext(String s) {
            saveItem(searchItemText);
            mEtSearchView.setText("");
            getSupportDelegate().hideSoftInput();
          }

          @Override public void onError(Throwable e) {
            LatteLoader.stopLoading();
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
          }

          @Override public void onComplete() {
            LatteLoader.stopLoading();
            Toast.makeText(getContext(), "搜索成功！", Toast.LENGTH_SHORT).show();
          }
        });
  }

  @OnClick(R2.id.icon_top_search_back)
  void onClickBack() {
    getSupportDelegate().hideSoftInput();
    getSupportDelegate().pop();
  }

  @SuppressWarnings("unchecked") private void saveItem(String item) {
    if (!TextUtils.isEmpty(item) && !StringUtils.isSpace(item)) {
      List<String> history;
      final String historyStr =
          LattePreference.getCustomAppProfile(SearchDataConverter.TAG_SEARCH_HISTORY);
      if (TextUtils.isEmpty(historyStr)) {
        history = new ArrayList<>();
      } else {
        history = JSON.parseObject(historyStr, ArrayList.class);
      }
      history.add(item);
      final String json = JSON.toJSONString(history);

      LattePreference.addCustomAppProfile(SearchDataConverter.TAG_SEARCH_HISTORY, json);
    }
  }

  @Override public Object setLayout() {
    return R.layout.delegate_search;
  }

  @Override public void onBindView(@Nullable Bundle savedInstanceState, @Nullable View rootView) {
    getSupportDelegate().showSoftInput(mEtSearchView);

    final LinearLayoutManager manager = new LinearLayoutManager(getContext());
    mRvSearch.setLayoutManager(manager);

    final List<MultipleItemEntity> data = new SearchDataConverter().convert();
    final SearchAdapter adapter = new SearchAdapter(data);
    mRvSearch.setAdapter(adapter);

    final DividerItemDecoration itemDecoration = new DividerItemDecoration();
    itemDecoration.setDividerLookup(new DividerItemDecoration.DividerLookup() {
      @Override public Divider getVerticalDivider(int position) {
        return null;
      }

      @Override public Divider getHorizontalDivider(int position) {
        return new Divider.Builder()
            .size(2)
            .margin(20, 20)
            .color(Color.GRAY)
            .build();
      }
    });
    mRvSearch.addItemDecoration(itemDecoration);
  }
}
