package com.arabtub.appactivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.arabtub.R;
import com.arabtub.utils.MyData;


public class VideosFragment extends Fragment {
    int color;

    public VideosFragment() {
    }

    @SuppressLint("ValidFragment")
    public VideosFragment(int color) {
        this.color = color;
    }



    private AutoCompleteTextView search_et;
    ImageView ivSearch,ivClear;
    FrameLayout flDownloads,flVideos;
    TextView tvVideos,tv3gp,tvMp3,tvPopularSongs;
    InterstitialAd mInterstitialAd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_download, container, false);
        try
        {
         flDownloads = (FrameLayout) view.findViewById(R.id.flDownloads);
        flDownloads.setVisibility(View.GONE);


         flVideos = (FrameLayout) view.findViewById(R.id.flVideos);
        flVideos.setVisibility(View.VISIBLE);



        search_et = (AutoCompleteTextView) view.findViewById(R.id.search_et);
        ivSearch = (ImageView)view.findViewById(R.id.ivSearch);
        ivClear = (ImageView)view.findViewById(R.id.ivClear);
            tvVideos = (TextView)view.findViewById(R.id.tvVideos);
            tv3gp = (TextView)view.findViewById(R.id.tv3gp);
            tvMp3 = (TextView)view.findViewById(R.id.tvMp3);
            tvPopularSongs = (TextView)view.findViewById(R.id.tvPopularSongs);

        ivClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_et.setText("");
                tvMp3.performClick();
            }
        });

        search_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    ivSearch.performClick();
                    return true;
                }
                return false;
            }
        });

        search_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //  getVideosuggetions();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        search_et.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                ivSearch.performClick();

            }
        });

            ivSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    search_et.setText("");
                    tvMp3.performClick();
                }
            });

            tvVideos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tvMp3.performClick();
                }
            });
            tv3gp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tvMp3.performClick();
                }
            });
            tvMp3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intActDownloadList = new Intent(getActivity(),ActDownloadList.class);
                    startActivity(intActDownloadList);

                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    }
                    else
                    {
                        requestNewShowInterstitial();
                    }
                }
            });

            tvPopularSongs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intActDownloadList = new Intent(getActivity(),ActDownloadList.class);
                    startActivity(intActDownloadList);

                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    }
                    else
                    {
                        requestNewShowInterstitial();
                    }
                }
            });

           /* ivSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("====tag====","=====ivSearch  click==");
                }
            });*/

            mInterstitialAd = new InterstitialAd(getActivity());
            mInterstitialAd.setAdUnitId(MyData.strInterstitialId);//ca-app-pub-3940256099942544/1033173712

            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    requestNewInterstitial();

                }
            });

            requestNewInterstitial();
    }
    catch (Exception e)
    {
        e.printStackTrace();
    }


        /*
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.dummyfrag_scrollableview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        adapter = new MySessionAdapter(getActivity().getBaseContext(),list);
        recyclerView.setAdapter(adapter);*/

        return view;
    }


    //Admob

    private void requestNewInterstitial() {



        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("C04B1BFFB0774708339BC273F8A43708")
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

    private void requestNewShowInterstitial() {
        Log.i("====tag====","=11111====requestNewShowInterstitial=");

        mInterstitialAd = new InterstitialAd(getActivity());
        mInterstitialAd.setAdUnitId(MyData.strInterstitialId);//ca-app-pub-3940256099942544/1033173712

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
            }
        });


        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("C04B1BFFB0774708339BC273F8A43708")
                .build();

        mInterstitialAd.loadAd(adRequest);

        // Set an AdListener.
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                Log.i("====onAdLoaded====","=11111====onAdLoaded=");
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }
            }

            @Override
            public void onAdClosed() {
                // Proceed to the next level.
                Log.i("====onAdClosed====","=11111====onAdClosed=");
            }
        });

    }

}