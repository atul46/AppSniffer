package app.atul.com.appsniffer.model;

import android.app.Service;
import android.app.usage.UsageStats;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import app.atul.com.appsniffer.model.versioncontrollayer.AndroidVerionFactory;
import app.atul.com.appsniffer.model.versioncontrollayer.AndroidVersionInterface;

/**
 * Created by srinivasan.l on 4/30/2017.
 */

public class ForegroundAppSnifferService extends Service {
    public static  long NOTIFY_INTERVAL=5000; // 5 seconds can be configurable
    // run on another Thread to avoid crash
    private final Handler mHandler = new Handler();
    private static final String TAG = ForegroundAppSnifferService.class.getSimpleName()+"--->";
    private Timer mTimer;
    Context ctx;
    private String serviceName=null;
    private static String foregroundApp="";

    @Override
    public void onCreate() {

        ctx=this.getApplicationContext();



        serviceName = "ForegroundAppSnifferService";

        if (this.mTimer != null) {
            this.mTimer.cancel();
        } else {
            // recreate new
            this.mTimer = new Timer();
        }

        // schedule task
        Log.d("SnifferService--->","Inside");
        this.mTimer.scheduleAtFixedRate(new ForegroundAppSnifferService.AppSnifferTask(), 0,
                ForegroundAppSnifferService.NOTIFY_INTERVAL);


        super.onCreate();

    }



    class AppSnifferTask extends TimerTask {
        public String currentApp = "NULL";

        @Override
        public void run() {
            // run on another thread
            //int threadCount=Thread.activeCount();
            boolean post = ForegroundAppSnifferService.this.mHandler.post(new Runnable() {


                @Override
                public void run() {
                    List<UsageStats> appList;// initialization of list to store usagestats
                    AndroidVersionInterface androidVersionInterface =
                            AndroidVerionFactory.getPackageAccessAbstractionObject();
                    if (androidVersionInterface != null) {
                        currentApp = androidVersionInterface.getForegroundRunningApp(ctx);
                        Toast.makeText(getApplicationContext(), "CurrentApp-->" + currentApp,
                                Toast.LENGTH_SHORT)
                                .show();
                    }
                    //passing the currentApp to a static variable


                    if (currentApp != null && !foregroundApp.equals(currentApp)) {
                       /*checks there is a change in the foregound app,
                       if conditon passes ,falls into this set statements
                       **/
                        Toast.makeText(getApplicationContext(), "AppSniffer packageStop-->" + foregroundApp,
                                Toast.LENGTH_SHORT)
                                .show();
                        Log.d(TAG, "previous App in foreground is: " + foregroundApp);

                        if (currentApp.equals("com.netflix.mediaclient") ||
                                currentApp.equals("com.whatsapp")) {


                            // to get internal sessionId
                            Toast.makeText(getApplicationContext(), "AppSniffer package-->" + currentApp,
                                    Toast.LENGTH_SHORT)
                                    .show();
                        }


                        foregroundApp = currentApp;
                    }
                }
            });

        }
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
       //to restart if crashes
        return Service.START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}