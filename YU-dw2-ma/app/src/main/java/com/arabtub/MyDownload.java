package com.arabtub;/*
 * Copyright (C) 2015 Baidu, Inc. All Rights Reserved.
 */

import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.widget.ProgressBar;
import android.widget.VideoView;

import com.arabtub.core.RxYoutube;
import com.arabtub.core.YoutubeUtils;
import com.arabtub.core.entity.FmtStreamMap;
import com.arabtub.utils.LogUtil;
import com.arabtub.view.YouTuBeWebView;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

public class MyDownload extends AppCompatActivity {

    private ProgressDialog mProgressDialog;
    private DownloadManager downloadManager;
    private YouTuBeWebView myWebView;
    private WebChromeClient mWebChromeClient;
    private VideoView mVideoView = null;
    private WebChromeClient.CustomViewCallback mCustomViewCallback = null;
    private String mVideoId;
    private String mCurrentUrl;
    private LayoutInflater layoutInflater;
    private View videoView;
    public static final String YOUTUBE = "https://m.youtube.com/watch?v=DoTPz4In3NA";
    private String loadUrl = YOUTUBE;
    private FloatingActionButton fab;
    private Action1<Throwable> errorAction = new Action1<Throwable>() {
        @Override
        public void call(Throwable throwable) {
            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
            }
        }
    };
    private Action1<List<FmtStreamMap>> resultAction = new Action1<List<FmtStreamMap>>() {
        @Override
        public void call(List<FmtStreamMap> fmtStreamMaps) {
            showDialog(fmtStreamMaps);
        }
    };
    private ProgressBar mLoadingProgressBar;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myWebView = (YouTuBeWebView) findViewById(R.id.webview);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        mLoadingProgressBar = (ProgressBar) findViewById(R.id.progress);
        bundle = getIntent().getExtras();

        mVideoId = "DoTPz4In3NA";
        if (bundle != null) {

            if (bundle.getString("channelId") != null && bundle.getString("channelId").toString().length() > 1) {
                mVideoId =  bundle.getString("channelId");
            }
        }
        Log.i("=mVideoId=","===mVideoId="+mVideoId);
        fab.performClick();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWaitDialog();
                //Call resolution
                RxYoutube.fetchYoutube(mVideoId, resultAction, errorAction);
            }
        });
        downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
//            super.onBackPressed();
            if (myWebView.canGoBack()) {
                myWebView.goBack();
            } else {
                super.onBackPressed();
            }
        }
    }

    private void showDialog(final List<FmtStreamMap> result) {
        if (result != null && result.size() > 0) {
            List<String> streamArrays = new ArrayList<String>();
            for (int i = 0; i < result.size(); i++) {
                final String streamType = result.get(i).getStreamString();
                streamArrays.add(streamType);
            }
            String[] item1 = new String[streamArrays.size()];
            streamArrays.toArray(item1);

            Dialog alertDialog = new AlertDialog.Builder(this).
                    setTitle("Select the download type").
                    setIcon(R.mipmap.ic_launcher)
                    .setItems(item1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            final FmtStreamMap fmtStreamMap = result.get(which);
                            RxYoutube.parseDownloadUrl(fmtStreamMap, new Action1<String>() {
                                @Override
                                public void call(String s) {
                                    dismissWaitDialog();
                                    String fileName = fmtStreamMap.title + "." + fmtStreamMap.extension;
                                    Uri uri = Uri.parse(s);
                                    DownloadManager.Request request = new DownloadManager.Request(uri);
                                    request.setDestinationInExternalFilesDir(MyDownload.this,Environment.DIRECTORY_MOVIES, fileName);
                                    downloadManager.enqueue(request);
                                }
                            });
                        }
                    }).
                            setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).
                            create();
            alertDialog.show();
        }
    }


    private void dismissWaitDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }


    protected void showWaitDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog.show(this, "Loading...", "Please wait...", true, true);
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.setOnCancelListener(new ProgressDialog.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                }
            });
        } else {
            mProgressDialog.show();
        }
    }
    private void updateButtonUI() {
        if (myWebView == null) {
            return;
        }
        String urlx = myWebView.getUrl();
        if (!TextUtils.isEmpty(urlx)) {
            mVideoId = YoutubeUtils.extractVideoId(urlx);
            LogUtil.d("mVideoId:" + mVideoId);
        }
        if (!TextUtils.isEmpty(mVideoId)) {
            mCurrentUrl = urlx;
        }
        fab.setEnabled(!TextUtils.isEmpty(mVideoId));
    }
}
