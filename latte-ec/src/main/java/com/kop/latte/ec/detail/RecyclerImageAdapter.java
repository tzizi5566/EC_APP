package com.kop.latte.ec.detail;

import android.support.v7.widget.AppCompatImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.kop.latte.ec.R;
import com.kop.latte.ui.recycler.ItemType;
import com.kop.latte.ui.recycler.MultipleFields;
import com.kop.latte.ui.recycler.MultipleItemEntity;
import com.kop.latte.ui.recycler.MultipleRecyclerAdapter;
import com.kop.latte.ui.recycler.MultipleViewHolder;
import java.util.List;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/11/18 15:26
 */
public class RecyclerImageAdapter extends MultipleRecyclerAdapter {

  private static final RequestOptions OPTIONS =
      new RequestOptions()
          .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
          .centerCrop()
          .dontTransform();

  /**
   * Same as QuickAdapter#QuickAdapter(Context,int) but with
   * some initialization data.
   *
   * @param data A new list is created out of this one to avoid mutable list
   */
  RecyclerImageAdapter(List<MultipleItemEntity> data) {
    super(data);
    addItemType(ItemType.SINGLE_BIG_IMAGE, R.layout.item_image);
  }

  @Override protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {
    super.convert(holder, entity);
    switch (holder.getItemViewType()) {
      case ItemType.SINGLE_BIG_IMAGE:
        final AppCompatImageView imageView = holder.getView(R.id.image_rv_item);
        final String url = entity.getField(MultipleFields.IMAGE_URL);
        Glide.with(mContext)
            .load(url)
            .apply(OPTIONS)
            .into(imageView);
        break;

      default:

        break;
    }
  }
}
