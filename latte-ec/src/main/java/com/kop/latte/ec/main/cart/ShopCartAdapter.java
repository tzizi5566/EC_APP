package com.kop.latte.ec.main.cart;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.joanzapata.iconify.widget.IconTextView;
import com.kop.latte.app.Latte;
import com.kop.latte.ec.R;
import com.kop.latte.net.RestClient;
import com.kop.latte.net.callback.ISuccess;
import com.kop.latte.ui.recycler.MultipleFields;
import com.kop.latte.ui.recycler.MultipleItemEntity;
import com.kop.latte.ui.recycler.MultipleRecyclerAdapter;
import com.kop.latte.ui.recycler.MultipleViewHolder;
import java.util.List;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/10/29 15:14
 */
public class ShopCartAdapter extends MultipleRecyclerAdapter {

  private boolean mIsSelectedAll = false;
  private int mSelectedCount = 0;
  private ICartItemListener mICartItemListener;

  private static final RequestOptions OPTIONS =
      new RequestOptions()
          .diskCacheStrategy(DiskCacheStrategy.ALL)
          .centerCrop()
          .dontAnimate();

  private OnSelectedAll mOnSelectedAll;

  interface OnSelectedAll {
    void selectedAll(int selectedCount);
  }

  void setOnSelectedAll(OnSelectedAll onSelectedAll) {
    mOnSelectedAll = onSelectedAll;
  }

  void setICartItemListener(ICartItemListener listener) {
    mICartItemListener = listener;
  }

  /**
   * Same as QuickAdapter#QuickAdapter(Context,int) but with
   * some initialization data.
   *
   * @param data A new list is created out of this one to avoid mutable list
   */
  ShopCartAdapter(List<MultipleItemEntity> data) {
    super(data);
    addItemType(ShopCartItemType.SHOP_CART_ITEM, R.layout.item_shop_cart);
  }

  void setIsSelectedAll(boolean isSelectedAll) {
    this.mIsSelectedAll = isSelectedAll;
    mSelectedCount = mIsSelectedAll ? getItemCount() : 0;
  }

  void clearSelectedCount() {
    mSelectedCount = 0;
  }

  @Override
  protected void convert(final MultipleViewHolder holder, final MultipleItemEntity entity) {
    super.convert(holder, entity);
    switch (holder.getItemViewType()) {
      case ShopCartItemType.SHOP_CART_ITEM:
        final int id = entity.getField(MultipleFields.ID);
        final String thumb = entity.getField(MultipleFields.IMAGE_URL);
        final String title = entity.getField(ShopCartItemFields.TITLE);
        final String desc = entity.getField(ShopCartItemFields.DESC);
        final int count = entity.getField(ShopCartItemFields.COUNT);
        final double price = entity.getField(ShopCartItemFields.PRICE);

        final AppCompatImageView imgThumb = holder.getView(R.id.image_item_shop_cart);
        final AppCompatTextView tvTitle = holder.getView(R.id.tv_item_shop_cart_title);
        final AppCompatTextView tvDesc = holder.getView(R.id.tv_item_shop_cart_desc);
        final AppCompatTextView tvPrice = holder.getView(R.id.tv_item_shop_cart_price);
        final IconTextView iconMinus = holder.getView(R.id.icon_item_minus);
        final IconTextView iconPlus = holder.getView(R.id.icon_item_plus);
        final AppCompatTextView tvCount = holder.getView(R.id.tv_item_shop_cart_count);
        final IconTextView iconIsSelected = holder.getView(R.id.icon_item_shop_cart);

        tvTitle.setText(title);
        tvDesc.setText(desc);
        tvPrice.setText(String.valueOf(price));
        tvCount.setText(String.valueOf(count));
        Glide.with(mContext)
            .load(thumb)
            .apply(OPTIONS)
            .into(imgThumb);

        entity.setField(ShopCartItemFields.IS_SELECTED, mIsSelectedAll);
        final boolean isSelected = entity.getField(ShopCartItemFields.IS_SELECTED);

        if (isSelected) {
          iconIsSelected.setTextColor(
              ContextCompat.getColor(Latte.getApplicationContext(), R.color.app_main));
        } else {
          iconIsSelected.setTextColor(
              ContextCompat.getColor(Latte.getApplicationContext(), android.R.color.darker_gray));
        }

        iconIsSelected.setOnClickListener(new View.OnClickListener() {
          @Override public void onClick(View v) {
            final boolean currentSelected = entity.getField(ShopCartItemFields.IS_SELECTED);
            if (currentSelected) {
              mSelectedCount--;
              iconIsSelected.setTextColor(ContextCompat.getColor(Latte.getApplicationContext(),
                  android.R.color.darker_gray));
              entity.setField(ShopCartItemFields.IS_SELECTED, false);
            } else {
              mSelectedCount++;
              iconIsSelected.setTextColor(
                  ContextCompat.getColor(Latte.getApplicationContext(), R.color.app_main));
              entity.setField(ShopCartItemFields.IS_SELECTED, true);
            }

            mIsSelectedAll = mSelectedCount == getItemCount();

            if (mOnSelectedAll != null) {
              mOnSelectedAll.selectedAll(mSelectedCount);
            }

            if (mICartItemListener != null) {
              mICartItemListener.onItemClick();
            }
          }
        });

        entity.setField(ShopCartItemFields.POSITION, holder.getLayoutPosition());

        iconMinus.setOnClickListener(new View.OnClickListener() {
          @Override public void onClick(View v) {
            final int currentCount = entity.getField(ShopCartItemFields.COUNT);
            if (Integer.parseInt(tvCount.getText().toString()) > 1) {
              RestClient.builder()
                  .url("shop_cart_data.json")
                  .params("count", currentCount)
                  .success(new ISuccess() {
                    @Override public void onSuccess(String response) {
                      int countNum = Integer.parseInt(tvCount.getText().toString());
                      countNum--;
                      tvCount.setText(String.valueOf(countNum));

                      final boolean isSelected = entity.getField(ShopCartItemFields.IS_SELECTED);
                      if (isSelected && mICartItemListener != null) {
                        final double itemTotal = countNum * price;
                        entity.setField(ShopCartItemFields.TOTAL_PRICE, itemTotal);
                        mICartItemListener.onItemClick();
                      }
                    }
                  })
                  .build()
                  .post();
            }
          }
        });

        iconPlus.setOnClickListener(new View.OnClickListener() {
          @Override public void onClick(View v) {
            final int currentCount = entity.getField(ShopCartItemFields.COUNT);
            RestClient.builder()
                .url("shop_cart_data.json")
                .params("count", currentCount)
                .success(new ISuccess() {
                  @Override public void onSuccess(String response) {
                    int countNum = Integer.parseInt(tvCount.getText().toString());
                    countNum++;
                    tvCount.setText(String.valueOf(countNum));

                    final boolean isSelected = entity.getField(ShopCartItemFields.IS_SELECTED);
                    if (isSelected && mICartItemListener != null) {
                      final double itemTotal = countNum * price;
                      entity.setField(ShopCartItemFields.TOTAL_PRICE, itemTotal);
                      mICartItemListener.onItemClick();
                    }
                  }
                })
                .build()
                .post();
          }
        });

      default:

        break;
    }
  }
}
