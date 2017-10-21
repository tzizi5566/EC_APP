package com.kop.latte.delegates.web.event;

import android.support.annotation.NonNull;
import java.util.HashMap;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/10/13 15:38
 */
public class EventManager {

  private static final HashMap<String, Event> EVENTS = new HashMap<>();

  private EventManager() {

  }

  private static class Holder {
    private static final EventManager INSTANCE = new EventManager();
  }

  public static EventManager getInstance() {
    return Holder.INSTANCE;
  }

  public EventManager addEvent(@NonNull String name, @NonNull Event event) {
    EVENTS.put(name, event);
    return this;
  }

  public Event createEvent(@NonNull String action) {
    final Event event = EVENTS.get(action);
    if (event == null) {
      return new UndefineEvent();
    }
    return event;
  }
}
