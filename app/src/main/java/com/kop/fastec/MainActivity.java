package com.kop.fastec;

import com.kop.latte.activities.ProxyActivity;
import com.kop.latte.delegates.LatteDelegate;

public class MainActivity extends ProxyActivity {

  @Override public LatteDelegate setRootDelegate() {
    return new ExampleDelegate();
  }
}
