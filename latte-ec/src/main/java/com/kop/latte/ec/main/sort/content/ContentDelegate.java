package com.kop.latte.ec.main.sort.content;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import butterknife.BindView;
import com.kop.latte.delegates.LatteDelegate;
import com.kop.latte.ec.R;
import com.kop.latte.ec.R2;
import com.kop.latte.net.RestClient;
import com.kop.latte.net.callback.ISuccess;
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
    initData();
  }

  private void initData() {
    RestClient.builder()
        .url("sort_content_data_" + mContentId + ".json")
        .success(new ISuccess() {
          @Override public void onSuccess(String response) {
            mData = new SectionDataConverter().convert(response);
            final SectionAdapter adapter =
                new SectionAdapter(R.layout.item_section_content, R.layout.item_section_header,
                    mData);
            mRvListContent.setAdapter(adapter);
          }
        })
        .build()
        .get();
  }
}
