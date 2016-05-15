package in.asphire.demoapp;


import android.app.Application;

import com.parse.Parse;

public class AndroidApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();


        Parse.enableLocalDatastore(this);
        Parse.initialize(this,"DAKLRtjeduPDT3UnV2ziI5ZYVJwc051m4b3AhEnA","wKHi5lCCPPoOvOU5qPBasmdkkE4WCYRRvh19lEWN");
    }
}
