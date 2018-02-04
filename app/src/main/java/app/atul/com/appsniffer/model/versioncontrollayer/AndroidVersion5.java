package app.atul.com.appsniffer.model.versioncontrollayer;

import android.app.Service;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

/**
 * Created by srinivasan.l on 6/20/2017.
 */

public class AndroidVersion5 implements AndroidVersionInterface {
    private static final String TAG = AndroidVersion5.class.getSimpleName()+"--->";
    String currentApp=null;

    /**
     * to obtain the foreground app that is running currently.
     * @param context
     * @return
     */
    @Override
    public String getForegroundRunningApp(Context context) {
        // boolean regStatusFlag = NetworkUtil.getRegistrationStatus(context);



            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                long time = System.currentTimeMillis();
                Log.d("currentApp--->","check");
                /*UsageStatsManager usm = (UsageStatsManager) context.getSystemService(USAGE_STATS_SERVICE);
                List<UsageStats> appList;

                appList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 1000 * 1000, time);

                if (appList != null && appList.size() > 0) {
                    SortedMap<Long, UsageStats> mySortedMap = new TreeMap<Long, UsageStats>();
                    for (UsageStats usageStats : appList) {
                        mySortedMap.put(usageStats.getLastTimeUsed(), usageStats);
                    }
                    if (mySortedMap!=null && !mySortedMap.isEmpty()) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);


                        currentApp = mySortedMap.get(mySortedMap.lastKey()).getPackageName();
                        //Toast.makeText(getApplicationContext(),
                        "package" + currentApp, Toast.LENGTH_SHORT).show();
                        Log.d(TAG, currentApp);
                    }*/
                UsageStatsManager mUsageStatsManager = (UsageStatsManager)
                        context.getSystemService(Service.USAGE_STATS_SERVICE);
                UsageEvents usageEvents = mUsageStatsManager.queryEvents(time - 1000 * 3600, time);
                UsageEvents.Event event = new UsageEvents.Event();
                while (usageEvents.hasNextEvent()) {
                    usageEvents.getNextEvent(event);
                    if(event.getEventType() == UsageEvents.Event.MOVE_TO_FOREGROUND) {
                        currentApp = event.getPackageName();
                        Log.d("currentApp--->",currentApp);
                    }
                }

            } else {

            }
            return currentApp;
        }

    }

