package com.kop.latte.ec.detail;

import android.animation.ArgbEvaluator;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.ContentFrameLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.OnClick;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.daimajia.androidanimations.library.YoYo;
import com.joanzapata.iconify.widget.IconTextView;
import com.kop.latte.delegates.LatteDelegate;
import com.kop.latte.ec.R;
import com.kop.latte.ec.R2;
import com.kop.latte.ec.main.cart.ShopCartDelegate;
import com.kop.latte.net.rx.RxRestClient;
import com.kop.latte.ui.animation.BezierAnimation;
import com.kop.latte.ui.animation.BezierUtil;
import com.kop.latte.ui.banner.BannerCreator;
import com.kop.latte.ui.widget.CircleTextView;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;
import me.yokeyword.fragmentation.anim.DefaultVerticalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/10/7 20:06
 */
public class GoodsDetailDelegate extends LatteDelegate implements
    AppBarLayout.OnOffsetChangedListener, ViewTreeObserver.OnGlobalLayoutListener,
    BezierUtil.AnimationListener {

  public static GoodsDetailDelegate newInstance(int goodsId) {
    Bundle args = new Bundle();
    args.putInt(ARG_GOODS_ID, goodsId);
    GoodsDetailDelegate fragment = new GoodsDetailDelegate();
    fragment.setArguments(args);
    return fragment;
  }

  @BindView(R2.id.detail_banner) ConvenientBanner<String> mDetailBanner;
  @BindView(R2.id.frame_goods_info) ContentFrameLayout mFrameGoodsInfo;
  @BindView(R2.id.icon_goods_back) IconTextView mIconGoodsBack;
  @BindView(R2.id.tv_detail_title_text) AppCompatTextView mTvDetailTitleText;
  @BindView(R2.id.goods_detail_toolbar) Toolbar mGoodsDetailToolbar;
  @BindView(R2.id.collapsing_toolbar_detail) CollapsingToolbarLayout mCollapsingToolbarDetail;
  @BindView(R2.id.tab_layout) TabLayout mTabLayout;
  @BindView(R2.id.app_bar_detail) AppBarLayout mAppBarDetail;
  @BindView(R2.id.view_pager) ViewPager mViewPager;
  @BindView(R2.id.icon_shop_cart) IconTextView mIconShopCart;
  @BindView(R2.id.rl_add_shop_cart) RelativeLayout mRlAddShopCart;
  @BindView(R2.id.tv_shopping_cart_amount) CircleTextView mTvShoppingCartAmount;

  public static final String ARG_GOODS_ID = "ARG_GOODS_ID";
  private int mGoodsId = -1;
  private int mStartColor;
  private int mEndColor;
  private int mOffset;
  private String mGoodsThumbUrl;
  private int mShopCount;
  private ArgbEvaluator mArgbEvaluator;

  private static final RequestOptions OPTIONS =
      new RequestOptions()
          .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
          .centerCrop()
          .dontTransform()
          .override(60, 60);

  @OnClick(R2.id.rl_add_shop_cart)
  void onClickAddShopCart() {
    final CircleImageView circleImageView = new CircleImageView(getContext());
    Glide.with(this)
        .load(mGoodsThumbUrl)
        .apply(OPTIONS)
        .into(circleImageView);

    BezierAnimation.addCart(this, mRlAddShopCart, mIconShopCart, circleImageView, this);
  }

  @OnClick(R2.id.icon_goods_back)
  void onClickBack() {
    getSupportDelegate().pop();
  }

  @OnClick(R2.id.rl_shop_cart)
  void onClickToShopCartDelegate() {
    getSupportDelegate().start(ShopCartDelegate.newInstance(true));
  }

  private void setShopCarCount(JSONObject data) {
    mGoodsThumbUrl = data.getString("thumb");
    if (mShopCount == 0) {
      mTvShoppingCartAmount.setVisibility(View.GONE);
    }
  }

  @Override public Object setLayout() {
    return R.layout.delegate_goods_detail;
  }

  @Override public void onBindView(@Nullable Bundle savedInstanceState, @Nullable View rootView) {
    mArgbEvaluator = new ArgbEvaluator();
  }

  @Override public void onLazyInitView(@Nullable Bundle savedInstanceState) {
    super.onLazyInitView(savedInstanceState);
    Bundle bundle = getArguments();
    if (bundle != null) {
      mGoodsId = bundle.getInt(ARG_GOODS_ID);
    }

    mCollapsingToolbarDetail.setContentScrimColor(
        ContextCompat.getColor(getContext(), R.color.app_main));
    mAppBarDetail.addOnOffsetChangedListener(this);

    mStartColor = Color.BLACK;
    mEndColor = Color.WHITE;
    mTvShoppingCartAmount.setCircleBackground(Color.RED);
    initData();
    initTabLayout();
  }

  private void initPager(JSONObject data) {
    final TabPagerAdapter adapter = new TabPagerAdapter(getChildFragmentManager(), data);
    mViewPager.setAdapter(adapter);
  }

  private void initTabLayout() {
    mTabLayout.setTabMode(TabLayout.MODE_FIXED);
    mTabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getContext(), R.color.app_main));
    mTabLayout.setTabTextColors(ColorStateList.valueOf(Color.BLACK));
    mTabLayout.setBackgroundColor(Color.WHITE);
    mTabLayout.setupWithViewPager(mViewPager);
  }

  private void initData() {
    RxRestClient.builder()
        .url("goods_detail_data_1.json")
        .params("goods_id", mGoodsId)
        .build()
        .get()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<String>() {
          @Override public void onSubscribe(Disposable d) {

          }

          @Override public void onNext(String s) {
            final JSONObject data = JSON.parseObject(s).getJSONObject("data");
            initBanner(data);
            initGoodsInfo(data);
            initPager(data);
            setShopCarCount(data);
          }

          @Override public void onError(Throwable e) {
            Toast.makeText(_mActivity, e.getMessage(), Toast.LENGTH_SHORT).show();
          }

          @Override public void onComplete() {

          }
        });
  }

  private void initGoodsInfo(JSONObject data) {
    final String goodsData = data.toJSONString();
    getSupportDelegate().loadRootFragment(R.id.frame_goods_info,
        GoodsInfoDelegate.newInstance(goodsData));
  }

  private void initBanner(JSONObject data) {
    final JSONArray array = data.getJSONArray("banners");
    final List<String> images = new ArrayList<>();
    int size = array.size();
    for (int i = 0; i < size; i++) {
      images.add(array.getString(i));
    }
    BannerCreator.setDefault(mDetailBanner, images, null);
  }

  @Override public FragmentAnimator onCreateFragmentAnimator() {
    return new DefaultVerticalAnimator();
  }

  @Override public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
    if (mOffset != 0) {
      float percent = (float) verticalOffset / mOffset;
      int currentColor = (int) mArgbEvaluator.evaluate(percent, mStartColor, mEndColor);
      mTvDetailTitleText.setTextColor(currentColor);
      mIconGoodsBack.setTextColor(currentColor);
    }
  }

  @Override public void onGlobalLayout() {
    mOffset =
        mGoodsDetailToolbar.getHeight() - (mDetailBanner.getHeight() + mFrameGoodsInfo.getHeight());
  }

  @Override public void onResume() {
    super.onResume();
    mFrameGoodsInfo.getViewTreeObserver().addOnGlobalLayoutListener(GoodsDetailDelegate.this);
  }

  @Override public void onPause() {
    super.onPause();
    mFrameGoodsInfo.getViewTreeObserver().removeOnGlobalLayoutListener(this);
  }

  @Override public void onAnimationEnd() {
    YoYo.with(new ScaleUpAnimator())
        .duration(500)
        .playOn(mIconShopCart);
    mShopCount++;
    mTvShoppingCartAmount.setVisibility(View.VISIBLE);
    mTvShoppingCartAmount.setText(String.valueOf(mShopCount));

    //RxRestClient.builder()
    //    .url("add_shop_cart_count.json")
    //    .params("count", mShopCount)
    //    .build()
    //    .post()
    //    .subscribeOn(Schedulers.io())
    //    .observeOn(AndroidSchedulers.mainThread())
    //    .subscribe(new Observer<String>() {
    //      @Override public void onSubscribe(Disposable d) {
    //
    //      }
    //
    //      @Override public void onNext(String s) {
    //
    //      }
    //
    //      @Override public void onError(Throwable e) {
    //        Toast.makeText(_mActivity, e.getMessage(), Toast.LENGTH_SHORT).show();
    //      }
    //
    //      @Override public void onComplete() {
    //
    //      }
    //    });
  }
}
