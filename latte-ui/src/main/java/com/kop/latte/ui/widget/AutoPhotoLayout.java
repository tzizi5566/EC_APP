package com.kop.latte.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import com.blankj.utilcode.util.ConvertUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.joanzapata.iconify.widget.IconTextView;
import com.kop.latte.delegates.LatteDelegate;
import com.kop.latte.ui.R;
import java.util.ArrayList;
import java.util.List;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/11/13 15:23
 */
public class AutoPhotoLayout extends LinearLayoutCompat {

  private int mCurrentNum;
  private int mMaxNum;
  private int mMaxLineNum;
  private IconTextView mIconTextView;
  private LayoutParams mParams;
  private int mDelegateId;
  private AppCompatImageView mImageView;
  private float mImageMargin;
  private LatteDelegate mDelegate;
  private List<View> mLineViews;
  private AlertDialog mDialog;
  public static final String ICON_TEXT = "{fa-plus}";
  private float mIconSize;

  public static final List<List<View>> ALL_VIEWS = new ArrayList<>();
  public static final List<Integer> LINE_HEIGHTS = new ArrayList<>();

  private boolean mIsOnceInitOnMeasure;
  private boolean mIsOnceInitOnLayout;

  private static final RequestOptions OPTIONS =
      new RequestOptions()
          .diskCacheStrategy(DiskCacheStrategy.NONE)
          .centerCrop()
          .dontTransform();

  public AutoPhotoLayout(Context context) {
    this(context, null);
  }

  public AutoPhotoLayout(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public AutoPhotoLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    final TypedArray typedArray =
        context.obtainStyledAttributes(attrs, R.styleable.AutoPhotoLayout);
    mMaxNum = typedArray.getInt(R.styleable.AutoPhotoLayout_max_count, 1);
    mMaxLineNum = typedArray.getInt(R.styleable.AutoPhotoLayout_line_count, 3);
    mImageMargin =
        typedArray.getDimension(R.styleable.AutoPhotoLayout_item_margin, ConvertUtils.sp2px(0f));
    mIconSize =
        typedArray.getDimension(R.styleable.AutoPhotoLayout_icon_size, ConvertUtils.sp2px(20f));
    typedArray.recycle();
  }

  public final void setDelegate(LatteDelegate delegate) {
    this.mDelegate = delegate;
  }

  public final void onCrop(Uri uri) {
    createImageView();
    Glide.with(mDelegate)
        .load(uri)
        .apply(OPTIONS)
        .into(mImageView);
  }

  private void createImageView() {
    mImageView = new AppCompatImageView(getContext());
    mImageView.setId(mCurrentNum);
    mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
    mImageView.setLayoutParams(mParams);
    mImageView.setOnClickListener(new OnClickListener() {
      @Override public void onClick(View v) {
        //获取要删除的图片ID
        mDelegateId = v.getId();
        mDialog.show();
        final Window window = mDialog.getWindow();
        if (window != null) {
          window.setContentView(R.layout.dialog_image_click_panel);
          window.setGravity(Gravity.BOTTOM);
          window.setWindowAnimations(R.style.anim_panel_up_from_bottom);
          window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
          final WindowManager.LayoutParams params = window.getAttributes();
          params.width = WindowManager.LayoutParams.MATCH_PARENT;
          params.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
          params.dimAmount = 0.5f;
          window.setAttributes(params);
          window.findViewById(R.id.dialog_image_clicked_btn_delete).setOnClickListener(
              new OnClickListener() {
                @Override public void onClick(View v) {
                  //得到要删除的图片
                  final AppCompatImageView imageView = findViewById(mDelegateId);
                  //设置图片逐渐消失的动画
                  final AlphaAnimation animation = new AlphaAnimation(1, 0);
                  animation.setDuration(500);
                  animation.setRepeatCount(0);
                  animation.setFillAfter(true);
                  animation.setStartOffset(0);
                  imageView.setAnimation(animation);
                  animation.start();
                  AutoPhotoLayout.this.removeView(imageView);
                  mCurrentNum -= 1;
                  //当前数据达到上限时隐藏添加按钮，不足时显示
                  if (mCurrentNum < mMaxNum) {
                    mIconTextView.setVisibility(VISIBLE);
                  }
                  mDialog.cancel();
                }
              });
          window.findViewById(R.id.dialog_image_clicked_btn_undetermined).setOnClickListener(
              new OnClickListener() {
                @Override public void onClick(View v) {
                  mDialog.cancel();
                }
              });

          window.findViewById(R.id.dialog_image_clicked_btn_cancel).setOnClickListener(
              new OnClickListener() {
                @Override public void onClick(View v) {
                  mDialog.cancel();
                }
              });
        }
      }
    });
    //添加子View的时候传入位置
    addView(mImageView, mCurrentNum);
    mCurrentNum++;
    //当添加数据大于mMaxNum时，自动隐藏添加按钮
    if (mCurrentNum >= mMaxNum) {
      mIconTextView.setVisibility(GONE);
    }
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
    int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
    int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
    int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
    //wrap_content
    int width = 0;
    int height = 0;
    //记录每一行的宽度与高度
    int lineWidth = 0;
    int lineHeight = 0;
    //得到内部元素个数
    int cCount = getChildCount();
    for (int i = 0; i < cCount; i++) {
      final View child = getChildAt(i);
      //测量子View的宽和高
      measureChild(child, widthMeasureSpec, heightMeasureSpec);
      //得到LayoutParams
      final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
      //获取子View占据的宽度
      final int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
      //获取子View占据的高度
      final int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
      //换行
      if (lineWidth + childWidth > sizeWidth - getPaddingLeft() - getPaddingRight()) {
        //对比得到最大宽度
        width = Math.max(width, lineWidth);
        //重置lineWidth
        lineWidth = childWidth;
        height += lineHeight;
        lineHeight = childHeight;
      } else {
        //未换行
        //叠加行宽
        lineWidth += childWidth;
        //得到当前最大的高度
        lineHeight = Math.max(lineHeight, childHeight);
      }
      //最后一个子控件
      if (i == cCount - 1) {
        width = Math.max(lineWidth, width);
        //判断是否超过最大拍照限制
        height += lineHeight;
      }
    }
    setMeasuredDimension(
        modeWidth == MeasureSpec.EXACTLY ? sizeWidth : width + getPaddingLeft() + getPaddingRight(),
        modeHeight == MeasureSpec.EXACTLY ? sizeHeight
            : height + getPaddingTop() + getPaddingBottom());
    //设置一行所有图片的宽高
    final int imageSideLen = sizeWidth / mMaxLineNum;
    //只初始化一次
    if (!mIsOnceInitOnMeasure) {
      mParams = new LayoutParams(imageSideLen, imageSideLen);
      mIsOnceInitOnMeasure = true;
    }
  }

  @Override protected void onLayout(boolean changed, int l, int t, int r, int b) {
    ALL_VIEWS.clear();
    LINE_HEIGHTS.clear();
    //获取当前ViewGroup的宽度
    final int width = getWidth();
    int lineWidth = 0;
    int lineHeight = 0;

    if (!mIsOnceInitOnLayout) {
      mLineViews = new ArrayList<>();
      mIsOnceInitOnLayout = true;
    }

    final int cCount = getChildCount();
    for (int i = 0; i < cCount; i++) {
      final View child = getChildAt(i);
      final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
      final int childWidth = child.getMeasuredWidth();
      final int childHeight = child.getMeasuredHeight();
      //如果需要换行
      if (childWidth + lineWidth + lp.leftMargin + lp.rightMargin
          > width - getPaddingLeft() - getPaddingRight()) {
        //记录lineHeight
        LINE_HEIGHTS.add(lineHeight);
        //记录当前一行的Views
        ALL_VIEWS.add(mLineViews);
        //重置宽和高
        lineWidth = 0;
        lineHeight = childHeight + lp.topMargin + lp.bottomMargin;
        //重置View集合
        mLineViews = new ArrayList<>();
      }
      lineWidth += childWidth + lp.leftMargin + lp.rightMargin;
      lineHeight = Math.max(lineHeight, lineHeight + lp.topMargin + lp.bottomMargin);
      mLineViews.add(child);
    }
    //处理最后一行
    LINE_HEIGHTS.add(lineHeight);
    ALL_VIEWS.add(mLineViews);
    //设置子View位置
    int left = getPaddingLeft();
    int top = getPaddingTop();
    //行数
    final int lineNum = ALL_VIEWS.size();
    for (int i = 0; i < lineNum; i++) {
      //当前行所有的View
      mLineViews = ALL_VIEWS.get(i);
      lineHeight = LINE_HEIGHTS.get(i);
      final int size = mLineViews.size();
      for (int j = 0; j < size; j++) {
        final View child = mLineViews.get(j);
        //判断child的状态
        if (child.getVisibility() == GONE) {
          continue;
        }
        final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
        //设置子View的边距
        final int lc = (int) (left + lp.leftMargin + mImageMargin);
        final int tc = (int) (top + lineHeight + lp.topMargin + mImageMargin);
        final int rc = (int) (lc + child.getMeasuredWidth() - mImageMargin);
        final int bc = (int) (tc + child.getMeasuredHeight() - mImageMargin);
        //为子View进行布局
        child.layout(lc, tc, rc, bc);
        left += child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
      }
      left = getPaddingLeft();
    }
    mIconTextView.setLayoutParams(mParams);
    mIsOnceInitOnLayout = false;
  }

  private void initAddIcon() {
    mIconTextView = new IconTextView(getContext());
    mIconTextView.setText(ICON_TEXT);
    mIconTextView.setGravity(Gravity.CENTER);
    mIconTextView.setTextSize(mIconSize);
    mIconTextView.setBackgroundResource(R.drawable.border_text);
    mIconTextView.setOnClickListener(new OnClickListener() {
      @Override public void onClick(View v) {
        mDelegate.startCameraWithCheck();
      }
    });
    addView(mIconTextView);
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    initAddIcon();
    mDialog = new AlertDialog.Builder(getContext()).create();
  }
}
