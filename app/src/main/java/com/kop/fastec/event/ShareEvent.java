package com.kop.fastec.event;

import cn.sharesdk.onekeyshare.OnekeyShare;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kop.latte.delegates.web.event.Event;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/11/15 19:13
 */
public class ShareEvent extends Event {

  @Override public String execute(String params) {
    final JSONObject object = JSON.parseObject(params).getJSONObject("params");
    final String title = object.getString("title");
    final String url = object.getString("url");
    final String imageUrl = object.getString("imageUrl");
    final String text = object.getString("text");

    OnekeyShare oks = new OnekeyShare();
    oks.disableSSOWhenAuthorize();
    oks.setTitle(title);
    oks.setText(text);
    oks.setImageUrl(imageUrl);
    oks.setUrl(url);
    // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
    oks.setTitleUrl(url);
    // site是分享此内容的网站名称，仅在QQ空间使用
    oks.setSite(title);
    // siteUrl是分享此内容的网站地址，仅在QQ空间使用
    oks.setSiteUrl(url);
    oks.show(getContext());
    return null;
  }
}
