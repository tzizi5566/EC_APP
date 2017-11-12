package com.kop.latte.ui.date;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/11/9 16:47
 */
public class DateDialogUtil {

  private String mDate;

  public interface IDateListener {

    void onDateChange(String date);
  }

  private IDateListener mDateListener;

  public void setDateListener(IDateListener dateListener) {
    mDateListener = dateListener;
  }

  public void showDialog(Context context) {
    final Calendar calendar = Calendar.getInstance();
    final SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault());
    mDate = format.format(calendar.getTime());

    final LinearLayout ll = new LinearLayout(context);
    final DatePicker picker = new DatePicker(context);
    final LinearLayout.LayoutParams params =
        new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT);
    picker.setLayoutParams(params);
    picker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
          @Override
          public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            final Calendar calendar = Calendar.getInstance();
            calendar.set(year, monthOfYear, dayOfMonth);
            final SimpleDateFormat format =
                new SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault());
            mDate = format.format(calendar.getTime());
          }
        });

    ll.addView(picker);

    new AlertDialog.Builder(context)
        .setTitle("选择日期")
        .setView(ll)
        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
          @Override public void onClick(DialogInterface dialog, int which) {
            if (mDateListener != null) {
              mDateListener.onDateChange(mDate);
            }
          }
        })
        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
          @Override public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
          }
        })
        .show();
  }
}
