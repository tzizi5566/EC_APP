package com.kop.latte.ec.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.BindView;
import com.kop.latte.delegates.LatteDelegate;
import com.kop.latte.ec.R;
import com.kop.latte.ec.R2;
import com.kop.latte.ui.recycler.ItemType;
import com.kop.latte.ui.recycler.MultipleFields;
import com.kop.latte.ui.recycler.MultipleItemEntity;
import java.util.ArrayList;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/11/18 14:50
 */
public class ImageDelegate extends LatteDelegate {

  public static ImageDelegate newInstance(ArrayList<String> pictures) {
    Bundle args = new Bundle();
    args.putStringArrayList(ARG_PICTURES, pictures);
    ImageDelegate fragment = new ImageDelegate();
    fragment.setArguments(args);
    return fragment;
  }

  @BindView(R2.id.rv_image_container) RecyclerView mRvImageContainer;

  public static final String ARG_PICTURES = "ARG_PICTURES";

  @Override public Object setLayout() {
    return R.layout.delegate_image;
  }

  @Override public void onBindView(@Nullable Bundle savedInstanceState, @Nullable View rootView) {

  }

  @Override public void onLazyInitView(@Nullable Bundle savedInstanceState) {
    super.onLazyInitView(savedInstanceState);
    final LinearLayoutManager manager = new LinearLayoutManager(getContext());
    mRvImageContainer.setLayoutManager(manager);
    initImages();
  }

  private void initImages() {
    final ArrayList<String> pictures = getArguments().getStringArrayList(ARG_PICTURES);
    final ArrayList<MultipleItemEntity> entities = new ArrayList<>();
    final int size;
    if (pictures != null) {
      size = pictures.size();
      for (int i = 0; i < size; i++) {
        final String imageUrl = pictures.get(i);
        final MultipleItemEntity entity = MultipleItemEntity
            .builder()
            .setItemType(ItemType.SINGLE_BIG_IMAGE)
            .setField(MultipleFields.IMAGE_URL, imageUrl)
            .build();
        entities.add(entity);
      }
      final RecyclerImageAdapter adapter = new RecyclerImageAdapter(entities);
      mRvImageContainer.setAdapter(adapter);
    }
  }
}
