package app.atul.com.appsniffer.model.versioncontrollayer;

import android.os.Build;

/**
 * Created by srinivasan.l on 1/28/2018.
 */

public class AndroidPermissionHandlerFactory {
    public static AndroidPermissionHandlerInterface getAndroidPermissionversionObject() {
        if (Build.VERSION.SDK_INT <= 21) {
            return new AndroidPermissionHandlerImpl( );
        } else if (Build.VERSION.SDK_INT == 22) {
            return new AndroidPermissionHandlerImpl( );
        }  else if (Build.VERSION.SDK_INT == 23) {
            return  new AndroidPermissionHandlerImpl( );
        } else if (Build.VERSION.SDK_INT == 24 || Build.VERSION.SDK_INT == 25) {
            return  new AndroidPermissionHandlerImpl( );
        } else {
            return null;
        }
    }
}
