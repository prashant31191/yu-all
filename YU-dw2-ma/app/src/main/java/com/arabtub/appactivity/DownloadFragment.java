package com.arabtub.appactivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.arabtub.R;
import com.arabtub.utils.MyData;


public class DownloadFragment  extends Fragment {
    int color;

    public DownloadFragment() {
    }

    @SuppressLint("ValidFragment")
    public DownloadFragment(int color) {
        this.color = color;
    }

    FrameLayout flDownloads,flVideos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_download, container, false);
        try
        {
            flDownloads = (FrameLayout) view.findViewById(R.id.flDownloads);
            flDownloads.setVisibility(View.VISIBLE);


            flVideos = (FrameLayout) view.findViewById(R.id.flVideos);
            flVideos.setVisibility(View.GONE);





            MobileAds.initialize(getActivity(), MyData.strBennerId);
        AdView mAdView2 = (AdView) view.findViewById(R.id.adView);
        AdRequest adRequest2 = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("C04B1BFFB0774708339BC273F8A43708")
                .build();
        mAdView2.loadAd(adRequest2);


           /* if(ActHome.startAppNativeAd != null)
            {
                *//**
                 * Load Native Ad with the following parameters:
                 * 1. Only 1 Ad
                 * 2. Download ad image automatically
                 * 3. Image size of 150x150px
                 *//*
                ActHome.startAppNativeAd.loadAd(
                        new NativeAdPreferences()
                                .setAdsNumber(1)
                                .setAutoBitmapDownload(true)
                                .setPrimaryImageSize(2),
                        ActHome.nativeAdListener);
            }*/
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return view;
    }
}