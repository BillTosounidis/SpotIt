package uom.android.dev;

import android.app.Application;
import android.os.SystemClock;

/**
 * Created by vasil on 15-Nov-17.
 */

public class SleepOnStart extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SystemClock.sleep(4000);
    }
}
