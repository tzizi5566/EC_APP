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
import butterknife.BindView;
import butterknife.OnClick;
import com.kop.latte.delegates.LatteDelegate;
import com.kop.latte.ec.R;
import com.kop.latte.ec.R2;
import com.kop.latte.net.RestClient;
import com.kop.latte.net.callback.ISuccess;
import com.kop.latte.util.log.LatteLogger;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/9/22 11:13
 */
public class SignUpDelegate extends LatteDelegate {

  @BindView(R2.id.edit_sign_up_name) TextInputEditText mEditSignUpName;
  @BindView(R2.id.edit_sign_up_email) TextInputEditText mEditSignUpEmail;
  @BindView(R2.id.edit_sign_up_phone) TextInputEditText mEditSignUpPhone;
  @BindView(R2.id.edit_sign_up_password) TextInputEditText mEditSignUpPassword;
  @BindView(R2.id.edit_sign_up_re_password) TextInputEditText mEditSignUpRePassword;
  @BindView(R2.id.btn_sign_up) AppCompatButton mBtnSignUp;
  @BindView(R2.id.tv_link_sign_in) AppCompatTextView mTvLinkSignIn;

  private ISignListener mISignListener;

  @Override public void onAttach(Activity activity) {
    super.onAttach(activity);
    if (activity instanceof ISignListener) {
      mISignListener = (ISignListener) activity;
    }
  }

  @OnClick(R2.id.btn_sign_up) void onClickSignUp() {
    if (checkForm()) {
      RestClient.builder()
          .url("user_profile.json")
          .params("name", mEditSignUpName.getText().toString())
          .params("email", mEditSignUpEmail.getText().toString())
          .params("phone", mEditSignUpPhone.getText().toString())
          .params("password", mEditSignUpPassword.getText().toString())
          .success(new ISuccess() {
            @Override public void onSuccess(String response) {
              LatteLogger.json("user_profile", response);
              Log.i("user_profile", response);
              SignHandler.onSignUp(response, mISignListener);
            }
          })
          .build()
          .post();
    }
  }

  @OnClick(R2.id.tv_link_sign_in) void onClickLink() {
    start(new SignInDelegate(), SINGLETASK);
  }

  private boolean checkForm() {
    final String name = mEditSignUpName.getText().toString();
    final String email = mEditSignUpEmail.getText().toString();
    final String phone = mEditSignUpPhone.getText().toString();
    final String password = mEditSignUpPassword.getText().toString();
    final String rePassword = mEditSignUpRePassword.getText().toString();

    boolean isPass = true;

    if (name.isEmpty()) {
      mEditSignUpName.setError("请输入姓名");
      isPass = false;
    } else {
      mEditSignUpName.setError(null);
    }

    if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
      mEditSignUpEmail.setError("错误的邮箱格式");
      isPass = false;
    } else {
      mEditSignUpEmail.setError(null);
    }

    if (phone.isEmpty() || phone.length() != 11) {
      mEditSignUpPhone.setError("手机号码错误");
      isPass = false;
    } else {
      mEditSignUpPhone.setError(null);
    }

    if (password.isEmpty() || password.length() < 6) {
      mEditSignUpPassword.setError("请填写至少6位数密码");
      isPass = false;
    } else {
      mEditSignUpPassword.setError(null);
    }

    if (rePassword.isEmpty() || rePassword.length() < 6 || !(rePassword.equals(password))) {
      mEditSignUpRePassword.setError("密码验证错误");
      isPass = false;
    } else {
      mEditSignUpRePassword.setError(null);
    }

    return isPass;
  }

  @Override public Object setLayout() {
    return R.layout.delegate_sign_up;
  }

  @Override public void onBindView(@Nullable Bundle savedInstanceState, @Nullable View rootView) {

  }
}
