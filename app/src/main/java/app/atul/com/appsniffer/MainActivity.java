package app.atul.com.appsniffer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import app.atul.com.appsniffer.model.ForegroundAppSnifferService;
import app.atul.com.appsniffer.model.versioncontrollayer.AndroidPermissionHandlerFactory;
import app.atul.com.appsniffer.model.versioncontrollayer.AndroidPermissionHandlerInterface;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        Log.d("MAinACtivity-->","OnResume");
        checkUsageStatsPermission();
        super.onResume();
    }

    public void startForegroundSniffingService(View v){
        Log.d("MAinACtivity-->","buttomcall");
        Intent intent1 = new Intent(getApplicationContext(), ForegroundAppSnifferService.class);
         startService(intent1);
    }

    private void checkUsageStatsPermission() {
        AndroidPermissionHandlerInterface androidVersionObj= AndroidPermissionHandlerFactory.
                getAndroidPermissionversionObject();
         androidVersionObj.checkUsageStats(getApplicationContext());
        androidVersionObj.askUsageStats(getApplicationContext());

    }
}
