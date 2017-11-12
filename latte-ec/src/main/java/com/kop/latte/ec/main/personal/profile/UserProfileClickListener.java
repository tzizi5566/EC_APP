package com.kop.latte.ec.main.personal.profile;

import android.content.DialogInterface;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.kop.latte.delegates.LatteDelegate;
import com.kop.latte.ec.R;
import com.kop.latte.ec.main.personal.list.ListBean;
import com.kop.latte.ui.date.DateDialogUtil;
import com.kop.latte.util.callback.CallbackManager;
import com.kop.latte.util.callback.CallbackType;
import com.kop.latte.util.callback.IGlobalCallback;
import com.kop.latte.util.log.LatteLogger;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/11/9 15:20
 */
public class UserProfileClickListener extends SimpleClickListener {

  private final LatteDelegate DELEGATE;
  private String[] mGenders = new String[] { "男", "女", "保密" };

  private static final RequestOptions OPTIONS =
      new RequestOptions()
          .diskCacheStrategy(DiskCacheStrategy.ALL)
          .centerCrop()
          .dontAnimate();

  UserProfileClickListener(LatteDelegate delegate) {
    this.DELEGATE = delegate;
  }

  @Override public void onItemClick(BaseQuickAdapter adapter, final View view, int position) {
    final ListBean bean = (ListBean) baseQuickAdapter.getData().get(position);
    final int id = bean.getId();
    switch (id) {
      case 1:
        CallbackManager.getInstance()
            .addCallback(CallbackType.ON_CROP, new IGlobalCallback<Uri>() {
              @Override public void executeCallback(Uri uri) {
                LatteLogger.d(uri);
                final ImageView avatar = (ImageView) view.findViewById(R.id.img_arrow_avatar);
                Glide.with(DELEGATE)
                    .load(uri)
                    .apply(OPTIONS)
                    .into(avatar);

                //RestClient.builder()
                //    .url("")
                //    .file(uri.getPath())
                //    .success(new ISuccess() {
                //      @Override public void onSuccess(String response) {
                //        LatteLogger.d("ON_CROP_UPLOAD", response);
                //        final String path = JSON.parseObject(response).getJSONObject("result")
                //            .getString("path");
                //
                //        //通知服务器更新信息
                //        RestClient.builder()
                //            .url("user_profile.json")
                //            .params("avatar", path)
                //            .loader(DELEGATE.getContext())
                //            .success(new ISuccess() {
                //              @Override
                //              public void onSuccess(String response) {
                //                //获取更新后的用户信息，然后更新本地数据库
                //                //没有本地数据的APP，每次打开APP都请求API，获取信息
                //              }
                //            })
                //            .build()
                //            .post();
                //      }
                //    })
                //    .build()
                //    .upload();
              }
            });
        DELEGATE.startCameraWithCheck();
        break;

      case 2:
        final LatteDelegate nameDelegate = bean.getDelegate();
        DELEGATE.getSupportDelegate().start(nameDelegate);
        break;

      case 3:
        getGenderDialog(new DialogInterface.OnClickListener() {
          @Override public void onClick(DialogInterface dialog, int which) {
            final AppCompatTextView textView =
                (AppCompatTextView) view.findViewById(R.id.tv_arrow_value);
            textView.setText(mGenders[which]);
            dialog.cancel();
          }
        });
        break;

      case 4:
        final DateDialogUtil dialogUtil = new DateDialogUtil();
        dialogUtil.setDateListener(new DateDialogUtil.IDateListener() {
          @Override public void onDateChange(String date) {
            final AppCompatTextView textView =
                (AppCompatTextView) view.findViewById(R.id.tv_arrow_value);
            textView.setText(date);
          }
        });
        dialogUtil.showDialog(DELEGATE.getContext());
        break;

      default:

        break;
    }
  }

  private void getGenderDialog(DialogInterface.OnClickListener listener) {
    final AlertDialog.Builder builder = new AlertDialog.Builder(DELEGATE.getContext());
    builder.setSingleChoiceItems(mGenders, 0, listener).show();
  }

  @Override public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

  }

  @Override public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

  }

  @Override public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

  }
}
