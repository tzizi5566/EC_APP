package com.kop.latte.ec.sign;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.OnClick;
import com.joanzapata.iconify.widget.IconTextView;
import com.kop.latte.delegates.LatteDelegate;
import com.kop.latte.ec.R;
import com.kop.latte.ec.R2;
import com.kop.latte.net.rx.RxRestClient;
import com.kop.latte.ui.loader.LatteLoader;
import com.kop.latte.util.log.LatteLogger;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/9/22 12:32
 */
public class SignInDelegate extends LatteDelegate {

  @BindView(R2.id.edit_sign_in_email) TextInputEditText mEditSignInEmail;
  @BindView(R2.id.edit_sign_in_password) TextInputEditText mEditSignInPassword;
  @BindView(R2.id.btn_sign_in) AppCompatButton mBtnSignIn;
  @BindView(R2.id.tv_link_sign_up) AppCompatTextView mTvLinkSignUp;
  @BindView(R2.id.icon_sign_in_wechat) IconTextView mIconSignInWechat;

  private ISignListener mISignListener;

  @Override public void onAttach(Activity activity) {
    super.onAttach(activity);
    if (activity instanceof ISignListener) {
      mISignListener = (ISignListener) activity;
    }
  }

  @OnClick(R2.id.btn_sign_in) void onClickSignIn() {
    if (checkForm()) {
      RxRestClient.builder()
          .loader(getContext())
          .url("user_profile.json")
          .params("email", mEditSignInEmail.getText().toString())
          .params("password", mEditSignInPassword.getText().toString())
          .build()
          .post()
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(new Observer<String>() {
            @Override public void onSubscribe(Disposable d) {

            }

            @Override public void onNext(String s) {
              LatteLogger.json("user_profile", s);
              Log.i("user_profile", s);
              SignHandler.onSignIn(s, mISignListener);
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
  }

  @OnClick(R2.id.icon_sign_in_wechat) void onClickWeChat() {
    Toast.makeText(getContext(), "微信登录", Toast.LENGTH_SHORT).show();
  }

  @OnClick(R2.id.tv_link_sign_up) void onClickLink() {
    getSupportDelegate().start(new SignUpDelegate(), SINGLETASK);
  }

  private boolean checkForm() {
    final String email = mEditSignInEmail.getText().toString();
    final String password = mEditSignInPassword.getText().toString();

    boolean isPass = true;

    if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
      mEditSignInEmail.setError("错误的邮箱格式");
      isPass = false;
    } else {
      mEditSignInEmail.setError(null);
    }

    if (password.isEmpty() || password.length() < 6) {
      mEditSignInPassword.setError("请填写至少6位数密码");
      isPass = false;
    } else {
      mEditSignInPassword.setError(null);
    }

    return isPass;
  }

  @Override public Object setLayout() {
    return R.layout.delegate_sign_in;
  }

  @Override public void onBindView(@Nullable Bundle savedInstanceState, @Nullable View rootView) {

  }
}
