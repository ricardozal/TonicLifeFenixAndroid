package mx.com.bigtechsolutions.toniclifefenix.commons;

import android.app.Application;
import android.content.Context;

public class MyFenixApp extends Application {

    private static MyFenixApp instance;

    public static MyFenixApp getInstance()
    {
        return instance;
    }

    public static Context getContext()
    {
        return instance;
    }

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();

    }
}
