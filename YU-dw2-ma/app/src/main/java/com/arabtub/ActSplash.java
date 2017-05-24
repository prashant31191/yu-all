package com.arabtub;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Window;
import android.view.WindowManager;

public class ActSplash extends Activity {

    // change time for splash screen visible
    private static final int SPLASH_DELAY = 1000;

    private static Handler mainThreadHandler = new Handler(Looper.getMainLooper());

    String TAG = "==ActSplash==";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);


        mainThreadHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(ActSplash.this,HomeTabs.class));//HomeTabs  FirstActivity
                 finish();
            }
        }, SPLASH_DELAY);


    }


}
