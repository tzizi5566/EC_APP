package com.kop.latte.ui.recycler;

import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kop.latte.ui.R;
import com.kop.latte.ui.banner.BannerCreator;
import java.util.ArrayList;
import java.util.List;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/10/6 19:25
 */
public class MultipleRecyclerAdapter
    extends BaseMultiItemQuickAdapter<MultipleItemEntity, MultipleViewHolder>
    implements BaseQuickAdapter.SpanSizeLookup, OnItemClickListener {

  //确保只初始化一次Banner
  private boolean mIsInitBanner = false;
  //设置图片加载策略
  private static final RequestOptions REQUEST_OPTIONS =
      new RequestOptions()
          .centerCrop()
          .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
          .dontTransform();

  /**
   * Same as QuickAdapter#QuickAdapter(Context,int) but with
   * some initialization data.
   *
   * @param data A new list is created out of this one to avoid mutable list
   */
  protected MultipleRecyclerAdapter(List<MultipleItemEntity> data) {
    super(data);
    init();
  }

  public static MultipleRecyclerAdapter create(List<MultipleItemEntity> data) {
    return new MultipleRecyclerAdapter(data);
  }

  public static MultipleRecyclerAdapter create(DataConverter converter) {
    return new MultipleRecyclerAdapter(converter.convert());
  }

  private void init() {
    //设置不同的item布局
    addItemType(ItemType.TEXT, R.layout.item_multiple_text);
    addItemType(ItemType.IMAGE, R.layout.item_multiple_image);
    addItemType(ItemType.TEXT_IMAGE, R.layout.item_multiple_image_text);
    addItemType(ItemType.BANNER, R.layout.item_multiple_banner);
    //设置宽度监听
    setSpanSizeLookup(this);
    openLoadAnimation();
    //多次执行动画
    isFirstOnly(false);
  }

  @Override protected MultipleViewHolder createBaseViewHolder(View view) {
    return MultipleViewHolder.create(view);
  }

  @Override protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {
    final String text;
    final String imageUrl;
    final ArrayList<String> bannerImages;

    switch (holder.getItemViewType()) {
      case ItemType.TEXT:
        text = entity.getField(MultipleFields.TEXT);
        holder.setText(R.id.text_single, text);
        break;

      case ItemType.IMAGE:
        imageUrl = entity.getField(MultipleFields.IMAGE_URL);
        Glide.with(mContext)
            .load(imageUrl)
            .apply(REQUEST_OPTIONS)
            .into((ImageView) holder.getView(R.id.image_single));
        break;

      case ItemType.TEXT_IMAGE:
        text = entity.getField(MultipleFields.TEXT);
        imageUrl = entity.getField(MultipleFields.IMAGE_URL);
        holder.setText(R.id.tv_multiple, text);
        Glide.with(mContext)
            .load(imageUrl)
            .apply(REQUEST_OPTIONS)
            .into((ImageView) holder.getView(R.id.img_multiple));
        break;

      case ItemType.BANNER:
        if (!mIsInitBanner) {
          bannerImages = entity.getField(MultipleFields.BANNERS);
          final ConvenientBanner<String> convenientBanner =
              holder.getView(R.id.banner_recycler_item);
          BannerCreator.setDefault(convenientBanner, bannerImages, this);
          mIsInitBanner = true;
        }
        break;

      default:

        break;
    }
  }

  @Override public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
    return getData().get(position).getField(MultipleFields.SPAN_SIZE);
  }

  @Override public void onItemClick(int position) {

  }
}
