package com.kop.latte.ec.main.personal.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;
import butterknife.BindView;
import com.kop.latte.delegates.LatteDelegate;
import com.kop.latte.ec.R;
import com.kop.latte.ec.R2;
import com.kop.latte.ec.main.personal.address.AddressDelegate;
import com.kop.latte.ec.main.personal.list.ListAdapter;
import com.kop.latte.ec.main.personal.list.ListBean;
import com.kop.latte.ec.main.personal.list.ListItemType;
import com.kop.latte.util.callback.CallbackManager;
import com.kop.latte.util.callback.CallbackType;
import java.util.ArrayList;
import java.util.List;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/11/12 15:27
 */
public class SettingsDelegate extends LatteDelegate {

  @BindView(R2.id.rv_settings) RecyclerView mRvSettings;

  @Override public Object setLayout() {
    return R.layout.delegate_settings;
  }

  @Override public void onBindView(@Nullable Bundle savedInstanceState, @Nullable View rootView) {

  }

  @Override public void onLazyInitView(@Nullable Bundle savedInstanceState) {
    super.onLazyInitView(savedInstanceState);
    final ListBean push = new ListBean.Builder()
        .setId(1)
        .setDelegate(new AddressDelegate())
        .setItemType(ListItemType.ITEM_SWITCH)
        .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
          @SuppressWarnings("unchecked") @Override
          public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
              CallbackManager.getInstance()
                  .getCallback(CallbackType.OPEN_PUSH)
                  .executeCallback(null);
              Toast.makeText(getContext(), "打开推送！", Toast.LENGTH_SHORT).show();
            } else {
              CallbackManager.getInstance()
                  .getCallback(CallbackType.STOP_PUSH)
                  .executeCallback(null);
              Toast.makeText(getContext(), "关闭推送！", Toast.LENGTH_SHORT).show();
            }
          }
        })
        .setText("消息推送")
        .build();

    final ListBean about = new ListBean.Builder()
        .setId(2)
        .setItemType(ListItemType.ITEM_NORMAL)
        .setDelegate(new AboutDelegate())
        .setText("关于")
        .build();

    final List<ListBean> data = new ArrayList<>();
    data.add(push);
    data.add(about);

    LinearLayoutManager manager = new LinearLayoutManager(getContext());
    mRvSettings.setLayoutManager(manager);
    final ListAdapter adapter = new ListAdapter(data);
    mRvSettings.setAdapter(adapter);
    mRvSettings.addOnItemTouchListener(new SettingsClickListener(this));
  }
}
