package app.atul.com.appsniffer.model.versioncontrollayer;

import android.content.Context;

/**
 * Created by srinivasan.l on 1/28/2018.
 */

public interface AndroidPermissionHandlerInterface {
   public boolean checkUsageStats(Context ctx);
    public boolean askUsageStats(Context ctx);
}
