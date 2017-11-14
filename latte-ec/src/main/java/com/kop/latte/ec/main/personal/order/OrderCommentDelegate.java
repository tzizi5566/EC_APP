package com.kop.latte.ec.main.personal.order;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.kop.latte.delegates.LatteDelegate;
import com.kop.latte.ec.R;
import com.kop.latte.ec.R2;
import com.kop.latte.ui.widget.AutoPhotoLayout;
import com.kop.latte.ui.widget.StarLayout;
import com.kop.latte.util.callback.CallbackManager;
import com.kop.latte.util.callback.CallbackType;
import com.kop.latte.util.callback.IGlobalCallback;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/11/13 13:37
 */
public class OrderCommentDelegate extends LatteDelegate {

  public static OrderCommentDelegate newInstance(String thumb) {
    Bundle args = new Bundle();
    args.putString(IMAGE_THUMB, thumb);
    OrderCommentDelegate fragment = new OrderCommentDelegate();
    fragment.setArguments(args);
    return fragment;
  }

  @BindView(R2.id.img_order_comment) AppCompatImageView mImgOrderComment;
  @BindView(R2.id.tv_comment_title) TextView mTvCommentTitle;
  @BindView(R2.id.custom_star_layout) StarLayout mCustomStarLayout;
  @BindView(R2.id.et_order_comment) AppCompatEditText mEtOrderComment;
  @BindView(R2.id.custom_auto_photo_layout) AutoPhotoLayout mCustomAutoPhotoLayout;

  public static final String IMAGE_THUMB = "IMAGE_THUMB";

  private static final RequestOptions OPTIONS =
      new RequestOptions()
          .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
          .centerCrop()
          .dontTransform();

  @OnClick(R2.id.top_tv_comment_commit)
  public void onClickSubmit() {
    Toast.makeText(getContext(), mCustomStarLayout.getStarCount() + "", Toast.LENGTH_SHORT).show();
  }

  @Override public Object setLayout() {
    return R.layout.delegate_order_comment;
  }

  @Override public void onBindView(@Nullable Bundle savedInstanceState, @Nullable View rootView) {

  }

  @Override public void onLazyInitView(@Nullable Bundle savedInstanceState) {
    super.onLazyInitView(savedInstanceState);
    Bundle bundle = getArguments();
    String thumb = bundle.getString(IMAGE_THUMB);
    Glide.with(this)
        .load(thumb)
        .apply(OPTIONS)
        .into(mImgOrderComment);

    mCustomAutoPhotoLayout.setDelegate(this);
    CallbackManager.getInstance()
        .addCallback(CallbackType.ON_CROP, new IGlobalCallback<Uri>() {
          @Override public void executeCallback(@Nullable Uri args) {
            mCustomAutoPhotoLayout.onCrop(args);
          }
        });
  }
}
