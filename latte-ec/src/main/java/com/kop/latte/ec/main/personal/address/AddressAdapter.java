package com.kop.latte.ec.main.personal.address;

import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import com.kop.latte.ec.R;
import com.kop.latte.net.rx.RxRestClient;
import com.kop.latte.ui.recycler.MultipleFields;
import com.kop.latte.ui.recycler.MultipleItemEntity;
import com.kop.latte.ui.recycler.MultipleRecyclerAdapter;
import com.kop.latte.ui.recycler.MultipleViewHolder;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/11/11 15:27
 */
public class AddressAdapter extends MultipleRecyclerAdapter {

  /**
   * Same as QuickAdapter#QuickAdapter(Context,int) but with
   * some initialization data.
   *
   * @param data A new list is created out of this one to avoid mutable list
   */
  protected AddressAdapter(List<MultipleItemEntity> data) {
    super(data);
    addItemType(AddressItemType.ITEM_ADDRESS, R.layout.item_address);
  }

  @Override protected void convert(final MultipleViewHolder holder, MultipleItemEntity entity) {
    super.convert(holder, entity);
    switch (holder.getItemViewType()) {
      case AddressItemType.ITEM_ADDRESS:
        final int id = entity.getField(MultipleFields.ID);
        final String name = entity.getField(MultipleFields.NAME);
        final String phone = entity.getField(AddressItemFields.PHONE);
        final String address = entity.getField(AddressItemFields.ADDRESS);
        final boolean isDefault = entity.getField(MultipleFields.TAG);

        final AppCompatTextView nameText = holder.getView(R.id.tv_address_name);
        final AppCompatTextView phoneText = holder.getView(R.id.tv_address_phone);
        final AppCompatTextView addressText = holder.getView(R.id.tv_address_address);
        final AppCompatTextView deleteText = holder.getView(R.id.tv_address_delete);

        nameText.setText(name);
        phoneText.setText(phone);
        addressText.setText(address);

        deleteText.setOnClickListener(new View.OnClickListener() {
          @Override public void onClick(View v) {
            RxRestClient.builder()
                .url("address.json")
                .params("id", id)
                .build()
                .post()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                  @Override public void onSubscribe(Disposable d) {

                  }

                  @Override public void onNext(String s) {
                    remove(holder.getLayoutPosition());
                  }

                  @Override public void onError(Throwable e) {

                  }

                  @Override public void onComplete() {

                  }
                });
          }
        });
        break;

      default:

        break;
    }
  }
}
