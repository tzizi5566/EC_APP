package com.kop.fastec;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;
import com.jaeger.library.StatusBarUtil;
import com.kop.latte.activities.ProxyActivity;
import com.kop.latte.delegates.LatteDelegate;
import com.kop.latte.ec.launcher.LauncherDelegate;
import com.kop.latte.ec.main.EcBottomDelegate;
import com.kop.latte.ec.sign.ISignListener;
import com.kop.latte.ec.sign.SignInDelegate;
import com.kop.latte.ui.launcher.ILauncherListener;
import com.kop.latte.ui.launcher.OnLauncherFinishTag;

public class MainActivity extends ProxyActivity implements ISignListener, ILauncherListener {

  private static final String TAG = "MainActivity";

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    StatusBarUtil.setTransparentForImageViewInFragment(this, null);
  }

  @Override public LatteDelegate setRootDelegate() {
    return new LauncherDelegate();
  }

  @Override public void onSignInSuccess() {
    Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
    getSupportDelegate().startWithPop(new EcBottomDelegate());
  }

  @Override public void onSignUpSuccess() {
    Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
  }

  @Override public void onLauncherFinish(OnLauncherFinishTag tag) {
    switch (tag) {
      case SIGNED:
        //Toast.makeText(this, "启动结束，用户登录了", Toast.LENGTH_SHORT).show();
        getSupportDelegate().startWithPop(new EcBottomDelegate());
        break;

      case NOT_SIGNED:
        //Toast.makeText(this, "启动结束，用户没登录", Toast.LENGTH_SHORT).show();
        getSupportDelegate().startWithPop(new SignInDelegate());
        break;

      default:
        break;
    }
  }
}
