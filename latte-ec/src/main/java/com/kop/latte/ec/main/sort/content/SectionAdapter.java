package com.kop.latte.ec.main.sort.content;

import android.support.v7.widget.AppCompatImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kop.latte.ec.R;
import java.util.List;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/10/9 13:17
 */
public class SectionAdapter extends BaseSectionQuickAdapter<SectionBean, BaseViewHolder> {

  private static final RequestOptions OPTIONS =
      new RequestOptions()
          .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
          .centerCrop()
          .dontTransform();

  /**
   * Same as QuickAdapter#QuickAdapter(Context,int) but with
   * some initialization data.
   *
   * @param layoutResId The layout resource id of each item.
   * @param sectionHeadResId The section head layout id for each item
   * @param data A new list is created out of this one to avoid mutable list
   */
  public SectionAdapter(int layoutResId, int sectionHeadResId, List<SectionBean> data) {
    super(layoutResId, sectionHeadResId, data);
  }

  @Override protected void convertHead(BaseViewHolder helper, SectionBean item) {
    helper.setText(R.id.header, item.header);
    helper.setVisible(R.id.more, item.isMore());
    helper.addOnClickListener(R.id.more);
  }

  @Override protected void convert(BaseViewHolder helper, SectionBean item) {
    final String thumb = item.t.getGoodsThumb();
    final String name = item.t.getGoodsName();
    final int id = item.t.getGoodsId();
    helper.setText(R.id.tv, name);
    final AppCompatImageView imageView = helper.getView(R.id.iv);
    Glide.with(mContext)
        .load(thumb)
        .apply(OPTIONS)
        .into(imageView);
  }
}
