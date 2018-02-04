package app.atul.com.appsniffer.model.versioncontrollayer;

import android.os.Build;

/**
 * Created by srinivasan.l on 6/21/2017.
 */

public class AndroidVerionFactory {

    public static AndroidVersionInterface getPackageAccessAbstractionObject() {
        if (Build.VERSION.SDK_INT < 21) {
            return new AndroidVersionBelow5( );
        } else if (Build.VERSION.SDK_INT == 21) {
            return new AndroidVersion5( );
        } else if (Build.VERSION.SDK_INT == 22) {
            return new AndroidVersion5_1( );
        } else if (Build.VERSION.SDK_INT == 23) {
            return new AndroidVersion6( );
        } else if (Build.VERSION.SDK_INT == 24 || Build.VERSION.SDK_INT == 25) {
            return new AndroidVersion7( );
        } else {
            return null;
        }
    }
}
