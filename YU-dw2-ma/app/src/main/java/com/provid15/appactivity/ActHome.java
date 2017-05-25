package com.provid15.appactivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;


import android.util.Log;
import android.view.MenuItem;

import android.widget.Toast;


import com.provid15.R;
import com.provid15.utils.MyData;
import com.startapp.android.publish.ads.nativead.NativeAdDetails;
import com.startapp.android.publish.ads.nativead.NativeAdPreferences;
import com.startapp.android.publish.ads.nativead.StartAppNativeAd;
import com.startapp.android.publish.adsCommon.Ad;
import com.startapp.android.publish.adsCommon.StartAppAd;
import com.startapp.android.publish.adsCommon.StartAppSDK;
import com.startapp.android.publish.adsCommon.adListeners.AdDisplayListener;
import com.startapp.android.publish.adsCommon.adListeners.AdEventListener;

import java.util.ArrayList;
import java.util.List;


public class ActHome extends AppCompatActivity {




    //Toolbar toolbar;
    AppBarLayout htab_appbar;
    ViewPager viewPager;
    TabLayout tabLayout;
    CollapsingToolbarLayout collapsingToolbarLayout;
    ViewPagerAdapter viewPagerAdapter;

    String strFrom="",strTitle = "",strData="";



    //for the start app ads
    /** StartAppAd object declaration */
    private StartAppAd startAppAd = new StartAppAd(this);

    /** StartApp Native Ad declaration */
    public StartAppNativeAd startAppNativeAd = new StartAppNativeAd(this);
    private NativeAdDetails nativeAd = null;


    /** Native Ad Callback */
    private AdEventListener nativeAdListener = new AdEventListener() {

        @Override
        public void onReceiveAd(Ad ad) {

            // Get the native ad
            ArrayList<NativeAdDetails> nativeAdsList = startAppNativeAd.getNativeAds();
            if (nativeAdsList.size() > 0){
                nativeAd = nativeAdsList.get(0);
            }

            // Verify that an ad was retrieved
            if (nativeAd != null){

                // When ad is received and displayed - we MUST send impression
                nativeAd.sendImpression(ActHome.this);
            }
        }

        @Override
        public void onFailedToReceiveAd(Ad ad) {

            // Error occurred while loading the native ad
            Log.e("---start app-","===Ads load fail==");
        }
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            StartAppSDK.init(this, MyData.startAppID, true);// 211313263 //TODO: Replace with your Application ID

            /**
             * Load Native Ad with the following parameters:
             * 1. Only 1 Ad
             * 2. Download ad image automatically
             * 3. Image size of 150x150px
             */
            startAppNativeAd.loadAd(
                    new NativeAdPreferences()
                            .setAdsNumber(1)
                            .setAutoBitmapDownload(true)
                            .setPrimaryImageSize(2),
                    nativeAdListener);


        setContentView(R.layout.act_home_new);


            getIntentData();
            initialization();
            setCommonData();
            setClickEvents();



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private  void getIntentData()
    {
        try {
        Bundle bundle = getIntent().getExtras();
        if(bundle !=null)
        {}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void initialization() {
        try {

            htab_appbar = (AppBarLayout) findViewById(R.id.htab_appbar);
            //toolbar = (Toolbar) findViewById(R.id.htab_toolbar);
            viewPager = (ViewPager) findViewById(R.id.htab_viewpager);
            tabLayout = (TabLayout) findViewById(R.id.htab_tabs);
            collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.htab_collapse_toolbar);



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setCommonData() {
        try {



            //setSupportActionBar(toolbar);
            setupViewPager(viewPager);
            htab_appbar.setExpanded(false);
            tabLayout.setTabTextColors(getResources().getColorStateList(R.color.tab_txt_color));
            tabLayout.setupWithViewPager(viewPager);
            collapsingToolbarLayout.setTitleEnabled(false);




            if(strData !=null && strData.equalsIgnoreCase("Follower") || strData.equalsIgnoreCase("Session")) {

                htab_appbar.setExpanded(false); // for the collapse mode open activity for the profile
                if(strData.equalsIgnoreCase("Session"))
                {
                    viewPager.setCurrentItem(1);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setClickEvents() {
        try {

            tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {

                    viewPager.setCurrentItem(tab.getPosition());

                    switch (tab.getPosition()) {
                        case 0:
                            //  showToast("One");

                            try{
                                // Show an Ad
                                startAppAd.showAd(new AdDisplayListener() {

                                    /**
                                     * Callback when Ad has been hidden
                                     * @param ad
                                     */
                                    @Override
                                    public void adHidden(Ad ad) {

                                    }

                                    /**
                                     * Callback when ad has been displayed
                                     * @param ad
                                     */
                                    @Override
                                    public void adDisplayed(Ad ad) {

                                    }

                                    /**
                                     * Callback when ad has been clicked
                                     * @param ad
                                     */
                                    @Override
                                    public void adClicked(Ad arg0) {

                                    }

                                    /**
                                     * Callback when ad not displayed
                                     * @param ad
                                     */
                                    @Override
                                    public void adNotDisplayed(Ad arg0) {

                                    }
                                });
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }


                            break;
                        case 1:
                            //showToast("Two");
                            try{
                                // Show an Ad
                                startAppAd.showAd(new AdDisplayListener() {

                                    /**
                                     * Callback when Ad has been hidden
                                     * @param ad
                                     */
                                    @Override
                                    public void adHidden(Ad ad) {

                                    }

                                    /**
                                     * Callback when ad has been displayed
                                     * @param ad
                                     */
                                    @Override
                                    public void adDisplayed(Ad ad) {

                                    }

                                    /**
                                     * Callback when ad has been clicked
                                     * @param ad
                                     */
                                    @Override
                                    public void adClicked(Ad arg0) {

                                    }

                                    /**
                                     * Callback when ad not displayed
                                     * @param ad
                                     */
                                    @Override
                                    public void adNotDisplayed(Ad arg0) {

                                    }
                                });
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }


                            break;
                        case 2:
                            //  showToast("Three");
                            break;
                    }
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });




        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public int adjustAlpha(int color, float factor) {
        int alpha = Math.round(Color.alpha(color) * factor);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha, red, green, blue);
    }


    private void setupViewPager(ViewPager viewPager) {
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFrag(new VideosFragment(getResources().getColor(R.color.clr_list_bg)), "Videos");
        viewPagerAdapter.addFrag(new DownloadFragment(getResources().getColor(R.color.clr_list_bg)), "Downloads");
        //adapter.addFrag(new MySessionsFragment(getResources().getColor(R.color.button_material_dark)), "MOUSE");
        viewPager.setAdapter(viewPagerAdapter);
    }


    static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    /**
     * Part of the activity's life cycle, StartAppAd should be integrated here.
     */
    @Override
    public void onResume() {
        super.onResume();
        startAppAd.onResume();
    }

    /**
     * Part of the activity's life cycle, StartAppAd should be integrated here
     * for the home button exit ad integration.
     */
    @Override
    public void onPause() {
        super.onPause();
        startAppAd.onPause();
    }




    boolean blnExit= false;
    /**
     * Part of the activity's life cycle, StartAppAd should be integrated here
     * for the back button exit ad integration.
     */
    @Override
    public void onBackPressed() {
        /*startAppAd.onBackPressed();
        super.onBackPressed();*/

        if(blnExit)
        {
            super.onBackPressed();
            return;
        }
        this.blnExit = true;
      //  startAppAd.onBackPressed();


        Toast.makeText(ActHome.this,"Please click BACK again to EXIT.",Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                blnExit = false;
            }
        },2000);
    }
    /*
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }*/
}