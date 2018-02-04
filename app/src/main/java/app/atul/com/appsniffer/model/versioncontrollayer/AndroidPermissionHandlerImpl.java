package app.atul.com.appsniffer.model.versioncontrollayer;

import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import java.util.List;

/**
 * Created by srinivasan.l on 1/28/2018.
 */

public class AndroidPermissionHandlerImpl implements AndroidPermissionHandlerInterface {


    /**
     * @param ctx :Application context
     * @return    : Return true or false value.
     */
    @Override
    public boolean askUsageStats(Context ctx) {
        boolean checkPermission = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            checkPermission = CheckAccessPermission(ctx);
            if (!checkPermission) {
                Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(intent);

            }  else{
                String c="Error";
                // Log.d(TAG,c);
            }
        }

        return checkPermission;
    }
    public boolean checkUsageStats(Context context){
        Log.d("current Version", String.valueOf(Build.VERSION.SDK_INT));
        if (Build.VERSION.SDK_INT < 21 ) { // for android version 6.
            return true;
        }

        final AppOpsManager appOpsManager = (AppOpsManager) context.
                getSystemService(Context.APP_OPS_SERVICE);
        Log.d("AppsOpsMAnager",String.valueOf(appOpsManager));
        if (appOpsManager == null) {
            return false;
        }
        // boolean appOpsAllow = false;
        final int mode = appOpsManager.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                android.os.Process.myUid(), context.getPackageName());
        if (mode != AppOpsManager.MODE_ALLOWED) {
            if (PackageManager.PERMISSION_GRANTED == 0) {
                return false;
            }
            if (PackageManager.PERMISSION_DENIED == 1) {
                readUsageHistoryAccess(context);

            }
        }

        // Verify that access is possible. Some devices "lie" and
        // return MODE_ALLOWED even when it's not.
        final long now = System.currentTimeMillis();
        final UsageStatsManager mUsageStatsManager = (UsageStatsManager) context.
                getSystemService(Context.USAGE_STATS_SERVICE);
        final List<UsageStats> stats = mUsageStatsManager.
                queryUsageStats(UsageStatsManager.INTERVAL_DAILY, now - 1000 * 10, now);

        return (stats != null && !stats.isEmpty());

    }

    private void readUsageHistoryAccess(Context ctx) {
        Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
        ctx.startActivity(intent);
    }

    /**
     * doIHavePermission():- check whether user has a permission or not...
     * @param ctx         : Application context
     * @return            :Return the boolean value.
     */

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private boolean CheckAccessPermission(Context ctx){
        final UsageStatsManager usageStatsManager = (UsageStatsManager) ctx.
                getSystemService(Context.USAGE_STATS_SERVICE);
        final List<UsageStats> queryUsageStats = usageStatsManager.
                queryUsageStats(UsageStatsManager.INTERVAL_DAILY, 0, System.currentTimeMillis());
        return !queryUsageStats.isEmpty();
    }
}
