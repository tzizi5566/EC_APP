package com.kop.latte.ec.database;

import android.content.Context;
import org.greenrobot.greendao.database.Database;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/10/4 16:43
 */
public class DatabaseManager {

  private DaoSession mDaoSession;
  private UserProfileDao mUserProfileDao;

  private DatabaseManager() {
  }

  public DatabaseManager init(Context context) {
    initDao(context);
    return this;
  }

  private static final class Holder {
    private static final DatabaseManager INSTANCE = new DatabaseManager();
  }

  public static DatabaseManager getInstance() {
    return Holder.INSTANCE;
  }

  private void initDao(Context context) {
    final ReleaseOpenHelper helper = new ReleaseOpenHelper(context, "fast_ec.db");
    final Database database = helper.getWritableDb();
    mDaoSession = new DaoMaster(database).newSession();
    mUserProfileDao = mDaoSession.getUserProfileDao();
  }

  public final UserProfileDao getDao() {
    return mUserProfileDao;
  }
}
