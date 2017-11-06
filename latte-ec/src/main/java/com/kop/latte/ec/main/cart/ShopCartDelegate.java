package com.kop.latte.ec.main.cart;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ViewStubCompat;
import android.view.View;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.OnClick;
import com.joanzapata.iconify.widget.IconTextView;
import com.kop.latte.app.Latte;
import com.kop.latte.delegates.bottom.BottomItemDelegate;
import com.kop.latte.ec.R;
import com.kop.latte.ec.R2;
import com.kop.latte.net.RestClient;
import com.kop.latte.net.callback.ISuccess;
import com.kop.latte.ui.recycler.MultipleItemEntity;
import java.util.ArrayList;
import java.util.List;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/10/27 13:23
 */
public class ShopCartDelegate extends BottomItemDelegate
    implements ShopCartAdapter.OnSelectedAll, ICartItemListener {

  @BindView(R2.id.tv_top_shop_cart_clear) AppCompatTextView mTvTopShopCartClear;
  @BindView(R2.id.tv_top_shop_cart_remove_selected) AppCompatTextView mTvTopShopCartRemoveSelected;
  @BindView(R2.id.rv_shop_cart) RecyclerView mRvShopCart;
  @BindView(R2.id.icon_shop_cart_select_all) IconTextView mIconShopCartSelectAll;
  @BindView(R2.id.stub_no_item) ViewStubCompat mStubNoItem;
  @BindView(R2.id.tv_shop_cart_total_price) AppCompatTextView mTvShopCartTotalPrice;

  private ShopCartAdapter mAdapter;

  @OnClick(R2.id.icon_shop_cart_select_all)
  void onClickSelectAll() {
    if (mAdapter.getData().isEmpty()) {
      Toast.makeText(getContext(), "购物车为空！", Toast.LENGTH_SHORT).show();
      return;
    }

    final int tag = (int) mIconShopCartSelectAll.getTag();
    if (tag == 0) {
      mIconShopCartSelectAll.setTextColor(ContextCompat.getColor(getContext(), R.color.app_main));
      mIconShopCartSelectAll.setTag(1);
      mAdapter.setIsSelectedAll(true);
      mAdapter.notifyItemRangeChanged(0, mAdapter.getItemCount(), 1);
    } else {
      mIconShopCartSelectAll.setTextColor(
          ContextCompat.getColor(getContext(), android.R.color.darker_gray));
      mIconShopCartSelectAll.setTag(0);
      mAdapter.setIsSelectedAll(false);
      mAdapter.notifyItemRangeChanged(0, mAdapter.getItemCount(), 1);
    }

    Latte.getHandler().post(new Runnable() {
      @Override public void run() {
        calTotalPrice();
      }
    });
  }

  @OnClick(R2.id.tv_top_shop_cart_remove_selected)
  void onClickRemoveSelectedItem() {
    if (mAdapter.getData().isEmpty()) {
      Toast.makeText(getContext(), "购物车为空！", Toast.LENGTH_SHORT).show();
      return;
    }

    final List<MultipleItemEntity> data = mAdapter.getData();
    List<MultipleItemEntity> deleteEntities = new ArrayList<>();
    for (MultipleItemEntity entity : data) {
      final boolean isSelected = entity.getField(ShopCartItemFields.IS_SELECTED);
      if (isSelected) {
        deleteEntities.add(entity);
      }
    }

    if (deleteEntities.isEmpty()) {
      return;
    }

    if (deleteEntities.size() == data.size()) {
      mIconShopCartSelectAll.setTextColor(
          ContextCompat.getColor(getContext(), android.R.color.darker_gray));
      mIconShopCartSelectAll.setTag(0);
      mAdapter.setIsSelectedAll(false);
    }

    int totalCount = mAdapter.getItemCount();
    int currentCount = mAdapter.getItemCount();

    for (MultipleItemEntity entity : deleteEntities) {
      int removePosition;
      final int entityPosition = entity.getField(ShopCartItemFields.POSITION);
      removePosition = entityPosition - (totalCount - currentCount);
      mAdapter.remove(removePosition);
      currentCount = mAdapter.getItemCount();
      mAdapter.notifyItemRangeChanged(removePosition, currentCount, 1);
    }

    mAdapter.clearSelectedCount();

    checkItemCount();

    calTotalPrice();
  }

  @OnClick(R2.id.tv_top_shop_cart_clear)
  void onClickClear() {
    if (mAdapter.getData().isEmpty()) {
      Toast.makeText(getContext(), "购物车为空！", Toast.LENGTH_SHORT).show();
      return;
    }

    mAdapter.getData().clear();
    mIconShopCartSelectAll.setTextColor(
        ContextCompat.getColor(getContext(), android.R.color.darker_gray));
    mIconShopCartSelectAll.setTag(0);
    mAdapter.setIsSelectedAll(false);
    mAdapter.notifyDataSetChanged();

    checkItemCount();

    calTotalPrice();
  }

  //显示空购物车界面
  @SuppressLint("RestrictedApi") private void checkItemCount() {
    final int count = mAdapter.getItemCount();
    if (count == 0) {
      final View stubView = mStubNoItem.inflate();
      final AppCompatTextView tvToBuy =
          (AppCompatTextView) stubView.findViewById(R.id.tv_stub_to_buy);
      tvToBuy.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          Toast.makeText(getContext(), "购物车为空！", Toast.LENGTH_SHORT).show();
        }
      });
      mRvShopCart.setVisibility(View.GONE);
    } else {
      mRvShopCart.setVisibility(View.VISIBLE);
    }
  }

  @Override public Object setLayout() {
    return R.layout.delegate_shop_cart;
  }

  @Override public void onBindView(@Nullable Bundle savedInstanceState, @Nullable View rootView) {
    mIconShopCartSelectAll.setTag(0);

    final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
    mRvShopCart.setLayoutManager(linearLayoutManager);
  }

  @Override public void onLazyInitView(@Nullable Bundle savedInstanceState) {
    super.onLazyInitView(savedInstanceState);
    RestClient.builder()
        .url("shop_cart_data.json")
        .loader(getContext())
        .success(new ISuccess() {
          @Override public void onSuccess(String response) {
            final ArrayList<MultipleItemEntity> data =
                new ShopCartDataConverter().setJsonData(response).convert();
            if (mAdapter == null) {
              mAdapter = new ShopCartAdapter(data);
              mRvShopCart.setAdapter(mAdapter);
            } else {
              mAdapter.setNewData(data);
            }
            mAdapter.setOnSelectedAll(ShopCartDelegate.this);
            mAdapter.setICartItemListener(ShopCartDelegate.this);

            checkItemCount();
          }
        })
        .build()
        .get();
  }

  @Override public void selectedAll(int selectedCount) {
    if (selectedCount == mAdapter.getItemCount()) {
      mIconShopCartSelectAll.setTextColor(ContextCompat.getColor(getContext(), R.color.app_main));
      mIconShopCartSelectAll.setTag(1);
    } else {
      mIconShopCartSelectAll.setTextColor(
          ContextCompat.getColor(getContext(), android.R.color.darker_gray));
      mIconShopCartSelectAll.setTag(0);
    }
  }

  @Override public void onItemClick() {
    calTotalPrice();
  }

  //计算所有商品总价
  private void calTotalPrice() {
    double totalPrice = 0;
    List<MultipleItemEntity> data = mAdapter.getData();
    for (MultipleItemEntity entity : data) {
      final boolean isSelected = entity.getField(ShopCartItemFields.IS_SELECTED);
      if (isSelected) {
        final double itemTotalPrice = entity.getField(ShopCartItemFields.TOTAL_PRICE);
        totalPrice += itemTotalPrice;
      }
    }
    mTvShopCartTotalPrice.setText(String.valueOf(totalPrice));
  }
}
