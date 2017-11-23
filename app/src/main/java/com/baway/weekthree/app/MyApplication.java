package com.baway.weekthree.app;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.baway.weekthree.gen.DaoMaster;
import com.baway.weekthree.gen.DaoSession;
import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by 郑文杰 on 2017/11/18.
 */

public class MyApplication extends Application {
    private static MyApplication instances;
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        instances = this;
        setDataBase();
    }

    public static MyApplication getInstances() {
        return instances;
    }

    private void setDataBase() {
        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(this, "week.db", null);
        SQLiteDatabase database = openHelper.getReadableDatabase();
        DaoMaster daoMaster = new DaoMaster(database);
        daoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
