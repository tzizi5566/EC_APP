package com.kop.latte.ec.main.sort.content;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;
import butterknife.BindView;
import com.kop.latte.delegates.LatteDelegate;
import com.kop.latte.ec.R;
import com.kop.latte.ec.R2;
import com.kop.latte.net.rx.RxRestClient;
import com.kop.latte.ui.loader.LatteLoader;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/10/8 17:43
 */
public class ContentDelegate extends LatteDelegate {

  @BindView(R2.id.rv_list_content) RecyclerView mRvListContent;

  private static final String ARG_CONTENT_ID = "CONTENT_ID";
  private int mContentId = -1;
  private List<SectionBean> mData;
  private SectionAdapter mAdapter;

  public static ContentDelegate newInstance(int contentId) {
    Bundle bundle = new Bundle();
    bundle.putInt(ARG_CONTENT_ID, contentId);
    final ContentDelegate contentDelegate = new ContentDelegate();
    contentDelegate.setArguments(bundle);
    return contentDelegate;
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    final Bundle bundle = getArguments();
    if (bundle != null) {
      mContentId = bundle.getInt(ARG_CONTENT_ID);
    }
  }

  @Override public Object setLayout() {
    return R.layout.delegate_list_content;
  }

  @Override public void onBindView(@Nullable Bundle savedInstanceState, @Nullable View rootView) {
    final StaggeredGridLayoutManager staggeredGridLayoutManager =
        new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
    mRvListContent.setLayoutManager(staggeredGridLayoutManager);
  }

  @Override public void onLazyInitView(@Nullable Bundle savedInstanceState) {
    super.onLazyInitView(savedInstanceState);
    initData(mContentId);
  }

  public void initData(int contentId) {
    RxRestClient.builder()
        .loader(getContext())
        .url("sort_content_data_" + contentId + ".json")
        .build()
        .get()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<String>() {
          @Override public void onSubscribe(Disposable d) {

          }

          @Override public void onNext(String s) {
            mData = new SectionDataConverter().convert(s);
            if (mAdapter == null) {
              mAdapter = new SectionAdapter(
                  R.layout.item_section_content,
                  R.layout.item_section_header,
                  mData);
              mRvListContent.setAdapter(mAdapter);
            } else {
              mAdapter.setNewData(mData);
            }
          }

          @Override public void onError(Throwable e) {
            LatteLoader.stopLoading();
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
          }

          @Override public void onComplete() {
            LatteLoader.stopLoading();
          }
        });
  }
}
