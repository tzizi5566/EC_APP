package com.kop.latte.ec.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import butterknife.BindView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kop.latte.delegates.LatteDelegate;
import com.kop.latte.ec.R;
import com.kop.latte.ec.R2;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/11/17 15:49
 */
public class GoodsInfoDelegate extends LatteDelegate {

  @BindView(R2.id.tv_goods_info_title) AppCompatTextView mTvGoodsInfoTitle;
  @BindView(R2.id.tv_goods_info_desc) AppCompatTextView mTvGoodsInfoDesc;
  @BindView(R2.id.tv_goods_info_price) AppCompatTextView mTvGoodsInfoPrice;

  public static final String ARG_GOODS_INFO = "ARG_GOODS_INFO";

  public static GoodsInfoDelegate newInstance(String goodsInfo) {
    Bundle args = new Bundle();
    args.putString(ARG_GOODS_INFO, goodsInfo);
    GoodsInfoDelegate fragment = new GoodsInfoDelegate();
    fragment.setArguments(args);
    return fragment;
  }

  @Override public Object setLayout() {
    return R.layout.delegate_goods_info;
  }

  @Override public void onBindView(@Nullable Bundle savedInstanceState, @Nullable View rootView) {

  }

  @Override public void onLazyInitView(@Nullable Bundle savedInstanceState) {
    super.onLazyInitView(savedInstanceState);
    final Bundle bundle = getArguments();
    final String goodsData = bundle.getString(ARG_GOODS_INFO);
    JSONObject data = JSON.parseObject(goodsData);
    final String name = data.getString("name");
    final String desc = data.getString("description");
    final double price = data.getDouble("price");

    mTvGoodsInfoTitle.setText(name);
    mTvGoodsInfoDesc.setText(desc);
    mTvGoodsInfoPrice.setText(String.valueOf(price));
  }
}
