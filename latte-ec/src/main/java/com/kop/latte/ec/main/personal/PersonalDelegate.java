package com.kop.latte.ec.main.personal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.BindView;
import butterknife.OnClick;
import com.joanzapata.iconify.widget.IconTextView;
import com.kop.latte.delegates.bottom.BottomItemDelegate;
import com.kop.latte.ec.R;
import com.kop.latte.ec.R2;
import com.kop.latte.ec.main.personal.address.AddressDelegate;
import com.kop.latte.ec.main.personal.list.ListAdapter;
import com.kop.latte.ec.main.personal.list.ListBean;
import com.kop.latte.ec.main.personal.list.ListItemType;
import com.kop.latte.ec.main.personal.order.OrderListDelegate;
import com.kop.latte.ec.main.personal.profile.UserProfileDelegate;
import com.kop.latte.ec.main.personal.settings.SettingsDelegate;
import de.hdodenhof.circleimageview.CircleImageView;
import java.util.ArrayList;
import java.util.List;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/11/6 17:00
 */
public class PersonalDelegate extends BottomItemDelegate {

  @BindView(R2.id.img_user_avatar) CircleImageView mImgUserAvatar;
  @BindView(R2.id.tv_all_order) AppCompatTextView mTvAllOrder;
  @BindView(R2.id.tv_all_account_arrow) IconTextView mTvAllAccountArrow;
  @BindView(R2.id.ll_pay) LinearLayoutCompat mLlPay;
  @BindView(R2.id.textView) AppCompatTextView mTextView;
  @BindView(R2.id.ll_receive) LinearLayoutCompat mLlReceive;
  @BindView(R2.id.ll_evaluate) LinearLayoutCompat mLlEvaluate;
  @BindView(R2.id.ll_after_market) LinearLayoutCompat mLlAfterMarket;
  @BindView(R2.id.rv_personal_setting) RecyclerView mRvPersonalSetting;

  public static final String ORDER_TYPE = "ORDER_TYPE";

  @Override public Object setLayout() {
    return R.layout.delegate_personal;
  }

  @OnClick(R2.id.tv_all_order)
  void onClickAllOrder() {
    startOrderListByType();
  }

  @OnClick(R2.id.img_user_avatar)
  void onClickAvatar() {
    getParentDelegate().getSupportDelegate().start(new UserProfileDelegate());
  }

  private void startOrderListByType() {
    final OrderListDelegate orderListDelegate = OrderListDelegate.newInstance("all");
    getParentDelegate().getSupportDelegate().start(orderListDelegate);
  }

  @Override public void onBindView(@Nullable Bundle savedInstanceState, @Nullable View rootView) {

  }

  @Override public void onLazyInitView(@Nullable Bundle savedInstanceState) {
    super.onLazyInitView(savedInstanceState);
    final ListBean address = new ListBean.Builder()
        .setId(1)
        .setDelegate(new AddressDelegate())
        .setItemType(ListItemType.ITEM_NORMAL)
        .setText("收货地址")
        .build();

    final ListBean system = new ListBean.Builder()
        .setId(2)
        .setDelegate(new SettingsDelegate())
        .setItemType(ListItemType.ITEM_NORMAL)
        .setText("系统设置")
        .build();

    final List<ListBean> data = new ArrayList<>();
    data.add(address);
    data.add(system);

    LinearLayoutManager manager = new LinearLayoutManager(getContext());
    mRvPersonalSetting.setLayoutManager(manager);
    final ListAdapter adapter = new ListAdapter(data);
    mRvPersonalSetting.setAdapter(adapter);
    mRvPersonalSetting.addOnItemTouchListener(new PersonalClickListener(this));
  }
}
