package com.kop.latte.ec.sign;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kop.latte.app.AccountManager;
import com.kop.latte.ec.database.DatabaseManager;
import com.kop.latte.ec.database.UserProfile;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/10/4 16:52
 */
public class SignHandler {

  public static void onSignIn(String response, ISignListener signListener) {
    final JSONObject profileJson = JSON.parseObject(response).getJSONObject("data");
    final Long userId = profileJson.getLong("userId");
    final String name = profileJson.getString("name");
    final String avatar = profileJson.getString("avatar");
    final String gender = profileJson.getString("gender");
    final String address = profileJson.getString("address");

    final UserProfile userProfile = new UserProfile(userId, name, avatar, gender, address);
    DatabaseManager.getInstance().getDao().insertOrReplace(userProfile);

    //已经注册并登录成功
    AccountManager.setSignState(true);
    signListener.onSignInSuccess();
  }

  public static void onSignUp(String response, ISignListener signListener) {
    final JSONObject profileJson = JSON.parseObject(response).getJSONObject("data");
    final Long userId = profileJson.getLong("userId");
    final String name = profileJson.getString("name");
    final String avatar = profileJson.getString("avatar");
    final String gender = profileJson.getString("gender");
    final String address = profileJson.getString("address");

    final UserProfile userProfile = new UserProfile(userId, name, avatar, gender, address);
    DatabaseManager.getInstance().getDao().insertOrReplace(userProfile);

    //已经注册并登录成功
    AccountManager.setSignState(true);
    signListener.onSignUpSuccess();
  }
}
