package com.arabtub;

import android.app.TabActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.TabHost;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.arabtub.utils.MyData;

public class HomeTabs extends TabActivity {
    /** Called when the activity is first created. */

    String TAG = "====HomeTabs===";
    int requestCode = 105;
    boolean isRead = true;
    boolean isWrite = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_screen);
        try
        {


          // Remove bellow comment for adding permoission to app for download.
            // isStorageWritePermissionGranted();



            // setDataTabs(); add below line comment when you remove comment for add permission on app.
             setDataTabs();





            MobileAds.initialize(getApplicationContext(), MyData.strBennerId);
            AdView mAdView2 = (AdView) findViewById(R.id.adView);
            AdRequest adRequest2 = new AdRequest.Builder()
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    .addTestDevice("C04B1BFFB0774708339BC273F8A43708")
                    .build();
            mAdView2.loadAd(adRequest2);
        }
        catch (Exception e){
            e.printStackTrace();
        }


    }


    private  void setDataTabs()
    {
        TabHost tabHost = getTabHost();
        Intent intent = new Intent().setClass(this, ActHome.class);
        TabHost.TabSpec spec = tabHost.newTabSpec("tabId").setIndicator("Videos", getResources().getDrawable(R.drawable.ic_app_icon));
        spec.setContent(intent);
        tabHost.addTab(spec);

        Intent intent1 = new Intent().setClass(this, ActDownload.class);
        TabHost.TabSpec spec1 = tabHost.newTabSpec("tabId").setIndicator("Downloads", getResources().getDrawable(R.drawable.ic_convert));
        spec1.setContent(intent1);
        tabHost.addTab(spec1);
        tabHost.setCurrentTab(0);
    }




    public  boolean isStorageWritePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted-isStorageWritePermissionGranted");
                isWrite = true;
                setDataTabs();
                return true;
            } else {
                isWrite = false;
                requestCode = 105;
                Log.v(TAG,"Permission is revoked-isStorageWritePermissionGranted");
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestCode);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted-isStorageWritePermissionGranted");
            setDataTabs();
            isWrite = true;
            return true;
        }
    }

    public  boolean isStorageReadPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                isRead = true;
                Log.v(TAG,"Permission is granted-isStorageReadPermissionGranted");
                setDataTabs();
                return true;
            } else {
                isRead = false;
                requestCode = 106;
                Log.v(TAG,"Permission is revoked-isStorageReadPermissionGranted");
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, requestCode);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted-isStorageReadPermissionGranted");
            setDataTabs();
            isRead = true;
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            Log.v(TAG,"====requestCode==="+requestCode+"Permission: "+permissions[0]+ "was "+grantResults[0]);
            //resume tasks needing this permission
            if(requestCode == 105)
            {
                isWrite = true;
                isStorageReadPermissionGranted();
            }
            else if(requestCode == 106)
            {
                isRead = true;
                setDataTabs();
            }
        }
        else
        {
            Log.v(TAG,"==Please allow==requestCode==="+requestCode);
            Toast.makeText(HomeTabs.this,"Please allow permission.",Toast.LENGTH_SHORT).show();

            if(requestCode == 105)
            {
                isWrite = false;
                isStorageWritePermissionGranted();
            }
            else if(requestCode == 106)
            {
                isRead = false;
                isStorageReadPermissionGranted();
            }

        }
    }




    boolean blnExit= false;

    @Override
    public void onBackPressed() {


        if(blnExit)
        {
            super.onBackPressed();
            return;
        }
        this.blnExit = true;
        Toast.makeText(HomeTabs.this,"Please click BACK again to EXIT.",Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                blnExit = false;
            }
        },2000);
    }

}