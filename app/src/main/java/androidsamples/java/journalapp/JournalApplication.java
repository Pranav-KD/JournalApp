package androidsamples.java.journalapp;

import android.app.Application;
import android.util.Log;

public class JournalApplication extends Application {
    private final String TAG = "ApplicationCreation";
    @Override
    public void onCreate() {
        Log.d(TAG, " Application created, initialising Repository");
        super.onCreate();
        JournalRepository.init(this);
    }
}
