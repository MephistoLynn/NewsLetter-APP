package com.newspaper.model.Constant;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import test.greendao.gen.DaoMaster;
import test.greendao.gen.DaoSession;

/**
 * Created by mephisto- on 2016/10/18.
 */

public class MyApplication extends Application {

    /**
     * 编写自己的Application，管理全局状态信息，比如Context
     */
    @SuppressLint("StaticFieldLeak")
    private static Context context;
    //private AppComponent appComponent;

    @Override
    public void onCreate() {
        //获取Context
        context = getApplicationContext();
/*        setupCompoent();*/

//初始化GreenDao
        setupGreenDao();
        super.onCreate();
    }



    private static DaoSession daoSession;

    private void setupGreenDao() {
        DaoMaster.DevOpenHelper dbHelper = new DaoMaster.DevOpenHelper(context,DataBase.databaseName, null);
        DaoMaster daoMaster = new DaoMaster(dbHelper.getWritableDb());
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }



    //返回appContext
    public static Context getContext() {
        return context;
    }

/*    private void setupCompoent() {
        appComponent = DaggerAppComponent.builder()

                .appModule(new AppModule())
                .build();

    }*/

}
