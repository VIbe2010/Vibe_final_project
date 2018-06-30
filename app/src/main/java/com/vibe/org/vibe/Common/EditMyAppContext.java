package com.vibe.org.vibe.Common;

import android.app.Application;
import android.content.Context;

/**
 * Created by Aman on 09/10/2017.
 */

public class EditMyAppContext extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        EditMyAppContext.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return EditMyAppContext.context;
    }
}