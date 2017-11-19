package com.kop.latte.ec.detail;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.ArrayList;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/11/18 14:40
 */
public class TabPagerAdapter extends FragmentPagerAdapter {

  private final ArrayList<String> TAB_TITLES = new ArrayList<>();
  private final ArrayList<ArrayList<String>> PICTURES = new ArrayList<>();

  TabPagerAdapter(FragmentManager fm, JSONObject data) {
    super(fm);
    final JSONArray tabs = data.getJSONArray("tabs");
    int size = tabs.size();
    for (int i = 0; i < size; i++) {
      final JSONObject eachTab = tabs.getJSONObject(i);
      final String name = eachTab.getString("name");
      final JSONArray pictureUrls = eachTab.getJSONArray("pictures");
      final ArrayList<String> picturesArray = new ArrayList<>();
      int pictureSize = pictureUrls.size();
      for (int j = 0; j < pictureSize; j++) {
        picturesArray.add(pictureUrls.getString(j));
      }
      TAB_TITLES.add(name);
      PICTURES.add(picturesArray);
    }
  }

  @Override public Fragment getItem(int position) {
    if (position == 0) {
      return ImageDelegate.newInstance(PICTURES.get(0));
    } else if (position == 1) {
      return ImageDelegate.newInstance(PICTURES.get(1));
    }
    return null;
  }

  @Override public int getCount() {
    return TAB_TITLES.size();
  }

  @Override public CharSequence getPageTitle(int position) {
    return TAB_TITLES.get(position);
  }
}
