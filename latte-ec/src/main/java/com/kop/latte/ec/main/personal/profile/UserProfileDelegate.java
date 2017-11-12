package com.kop.latte.ec.main.personal.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.BindView;
import com.kop.latte.delegates.LatteDelegate;
import com.kop.latte.ec.R;
import com.kop.latte.ec.R2;
import com.kop.latte.ec.main.personal.list.ListAdapter;
import com.kop.latte.ec.main.personal.list.ListBean;
import com.kop.latte.ec.main.personal.list.ListItemType;
import com.kop.latte.ec.main.personal.settings.NameDelegate;
import java.util.ArrayList;
import java.util.List;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/11/9 14:54
 */
public class UserProfileDelegate extends LatteDelegate {

  @BindView(R2.id.rv_user_profile) RecyclerView mRvUserProfile;

  @Override public Object setLayout() {
    return R.layout.delegate_user_profile;
  }

  @Override public void onBindView(@Nullable Bundle savedInstanceState, @Nullable View rootView) {

  }

  @Override public void onLazyInitView(@Nullable Bundle savedInstanceState) {
    super.onLazyInitView(savedInstanceState);
    final ListBean image = new ListBean.Builder()
        .setId(1)
        .setItemType(ListItemType.ITEM_AVATAR)
        .setImageUrl("http://i9.qhimg.com/t017d891ca365ef60b5.jpg")
        .build();

    final ListBean name = new ListBean.Builder()
        .setId(2)
        .setItemType(ListItemType.ITEM_NORMAL)
        .setText("昵称")
        .setValue("测试")
        .setDelegate(new NameDelegate())
        .build();

    final ListBean gender = new ListBean.Builder()
        .setId(3)
        .setItemType(ListItemType.ITEM_NORMAL)
        .setText("性别")
        .setValue("未设置性别")
        .build();

    final ListBean birth = new ListBean.Builder()
        .setId(4)
        .setItemType(ListItemType.ITEM_NORMAL)
        .setText("生日")
        .setValue("未设置生日")
        .build();

    final List<ListBean> data = new ArrayList<>();
    data.add(image);
    data.add(name);
    data.add(gender);
    data.add(birth);

    LinearLayoutManager manager = new LinearLayoutManager(getContext());
    mRvUserProfile.setLayoutManager(manager);
    final ListAdapter adapter = new ListAdapter(data);
    mRvUserProfile.setAdapter(adapter);
    mRvUserProfile.addOnItemTouchListener(new UserProfileClickListener(this));
  }
}
