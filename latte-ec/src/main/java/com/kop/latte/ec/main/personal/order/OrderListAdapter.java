package com.kop.latte.ec.main.personal.order;

import android.annotation.SuppressLint;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.kop.latte.ec.R;
import com.kop.latte.ui.recycler.MultipleFields;
import com.kop.latte.ui.recycler.MultipleItemEntity;
import com.kop.latte.ui.recycler.MultipleRecyclerAdapter;
import com.kop.latte.ui.recycler.MultipleViewHolder;
import java.util.List;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/11/8 15:58
 */
public class OrderListAdapter extends MultipleRecyclerAdapter {

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
  protected OrderListAdapter(List<MultipleItemEntity> data) {
    super(data);
    addItemType(OrderListItemType.ITEM_ORDER_LIST, R.layout.item_order_list);
  }

  @SuppressLint("SetTextI18n") @Override
  protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {
    super.convert(holder, entity);
    switch (holder.getItemViewType()) {
      case OrderListItemType.ITEM_ORDER_LIST:
        final AppCompatImageView imageView = holder.getView(R.id.image_order_list);
        final AppCompatTextView title = holder.getView(R.id.tv_order_list_title);
        final AppCompatTextView price = holder.getView(R.id.tv_order_list_price);
        final AppCompatTextView time = holder.getView(R.id.tv_order_list_time);

        final String titleVal = entity.getField(MultipleFields.TITLE);
        final String thumb = entity.getField(MultipleFields.IMAGE_URL);
        final String timeVal = entity.getField(OrderItemFields.TIME);
        final double priceVal = entity.getField(OrderItemFields.PRICE);

        title.setText(titleVal);
        time.setText("时间：" + timeVal);
        price.setText("价格：" + String.valueOf(priceVal));
        Glide.with(mContext)
            .load(thumb)
            .apply(OPTIONS)
            .into(imageView);

        break;

      default:

        break;
    }
  }
}
