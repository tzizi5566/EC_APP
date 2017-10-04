package com.kop.latte.ec.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/10/4 16:36
 */
@Entity(nameInDb = "USER_PROFILE")
public class UserProfile {

  @Id
  private long userId = 0;
  private String name = null;
  private String avatar = null;
  private String gender = null;
  private String address = null;

  @Generated(hash = 1202698052)
  public UserProfile(long userId, String name, String avatar, String gender,
      String address) {
    this.userId = userId;
    this.name = name;
    this.avatar = avatar;
    this.gender = gender;
    this.address = address;
  }

  @Generated(hash = 968487393)
  public UserProfile() {
  }

  public long getUserId() {
    return this.userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAvatar() {
    return this.avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public String getGender() {
    return this.gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getAddress() {
    return this.address;
  }

  public void setAddress(String address) {
    this.address = address;
  }
}
