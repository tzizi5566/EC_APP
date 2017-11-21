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
import com.kop.latte.ec.main.index.IndexDelegate;
import com.kop.latte.net.rx.RxRestClient;
import com.kop.latte.ui.loader.LatteLoader;
import com.kop.latte.ui.recycler.MultipleItemEntity;
import com.kop.latte.util.callback.CallbackManager;
import com.kop.latte.util.callback.CallbackType;
import com.kop.latte.util.callback.IGlobalCallback;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;
import me.yokeyword.fragmentation.SupportHelper;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/10/27 13:23
 */
public class ShopCartDelegate extends BottomItemDelegate
    implements ShopCartAdapter.OnSelectedAll, ICartItemListener {

  public static ShopCartDelegate newInstance(boolean formGoodsDetailDelegate) {
    Bundle args = new Bundle();
    args.putBoolean(FROM_GOODS_DATEIL_DELEGATE, formGoodsDetailDelegate);
    ShopCartDelegate fragment = new ShopCartDelegate();
    fragment.setArguments(args);
    return fragment;
  }

  @BindView(R2.id.rv_shop_cart) RecyclerView mRvShopCart;
  @BindView(R2.id.icon_shop_cart_select_all) IconTextView mIconShopCartSelectAll;
  @BindView(R2.id.stub_no_item) ViewStubCompat mStubNoItem;
  @BindView(R2.id.tv_shop_cart_total_price) AppCompatTextView mTvShopCartTotalPrice;

  public static final String FROM_GOODS_DATEIL_DELEGATE = "FROM_GOODS_DATEIL_DELEGATE";
  private boolean mFromGoodsDetailDelegate;
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
          IndexDelegate indexDelegate =
              SupportHelper.findFragment(getParentFragment().getChildFragmentManager(),
                  IndexDelegate.class);
          getParentDelegate().getSupportDelegate()
              .showHideFragment(indexDelegate, ShopCartDelegate.this);

          @SuppressWarnings("unchecked") IGlobalCallback<Integer> callback =
              CallbackManager.getInstance().getCallback(CallbackType.SHOP_CART_TO_INDEX);
          if (callback != null) {
            callback.executeCallback(0);
          }
        }
      });
      mRvShopCart.setVisibility(View.GONE);
    } else {
      mRvShopCart.setVisibility(View.VISIBLE);
    }
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Bundle bundle = getArguments();
    if (bundle != null) {
      mFromGoodsDetailDelegate = getArguments().getBoolean(FROM_GOODS_DATEIL_DELEGATE);
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
    RxRestClient.builder()
        .loader(getContext())
        .url("shop_cart_data.json")
        .build()
        .get()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<String>() {
          @Override public void onSubscribe(Disposable d) {

          }

          @Override public void onNext(String s) {
            final ArrayList<MultipleItemEntity> data =
                new ShopCartDataConverter().setJsonData(s).convert();
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

          @Override public void onError(Throwable e) {
            LatteLoader.stopLoading();
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
          }

          @Override public void onComplete() {
            LatteLoader.stopLoading();
          }
        });
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

  @Override public boolean onBackPressedSupport() {
    if (mFromGoodsDetailDelegate) {
      getSupportDelegate().pop();
      return true;
    }
    return super.onBackPressedSupport();
  }
}
