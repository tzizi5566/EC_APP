package com.kop.latte.delegates.web.event;

import com.kop.latte.util.log.LatteLogger;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/10/13 15:48
 */
public class UndefineEvent extends Event {

  @Override public String execute(String params) {
    LatteLogger.e("UndefineEvent", params);
    return null;
  }
}
