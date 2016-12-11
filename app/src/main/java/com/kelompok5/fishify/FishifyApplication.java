package com.kelompok5.fishify;

import android.app.Application;

import com.kelompok5.fishify.model.DaoMaster;
import com.kelompok5.fishify.model.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * Created by bradhawk on 11/20/2016.
 */

public class FishifyApplication extends Application {

    private static FishifyApplication app;
    private static DaoSession daoSession;

    public static FishifyApplication getInstance() {
        return app;
    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if(app == null) app = this;

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "fishify-db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

}