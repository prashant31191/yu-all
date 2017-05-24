package com.arabtub;

import android.app.Activity;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.arabtub.api.AppBase;
import com.arabtub.api.IApiMethods;
import com.arabtub.api.YTrailerModel;

import com.arabtub.core.RxYoutube;
import com.arabtub.core.entity.FmtStreamMap;
import com.arabtub.utils.MyData;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

/**
 * Created by Admin on 11/16/2016.
 */

public class ActHome extends Activity {


    int color;
String TAG ="ActHome";

    ProgressDialog customProgressDialog;
    Bundle  bundle;
    String Tag = "YSearchFragment",pageToken="test";
  
    RecyclerView recyclerView;
    YListAdapter adapter;
    int positionSelected = 0;
    SearchView svSearchVideos;
    IApiMethods methods;
    private AutoCompleteTextView search_et;
ImageView ivSearch,ivClear,ivSearchBig;


    InterstitialAd mInterstitialAd;

   // private static final String BASE_URL = "https://www.googleapis.com";
    ArrayList<String> arrayList=new ArrayList<>();
    ArrayAdapter<String> adapterSearchHint;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.fragment_ytrailer);

        
        
        
        try
        {

           // MobileAds.initialize(getApplicationContext(),strBennerId);// "ca-app-pub-7349168533879629/4890448391");



            mInterstitialAd = new InterstitialAd(this);
            mInterstitialAd.setAdUnitId(MyData.strInterstitialId);//ca-app-pub-3940256099942544/1033173712

            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    requestNewInterstitial();

                }
            });

            requestNewInterstitial();


            final FrameLayout frameLayout = (FrameLayout) findViewById(R.id.dummyfrag_bg);
            frameLayout.setBackgroundColor(color);
            recyclerView = (RecyclerView) findViewById(R.id.rvRecent);
            svSearchVideos = (SearchView) findViewById(R.id.svSearchVideos);
            svSearchVideos.setVisibility(View.GONE);
            //llMRefLayout = (MaterialRefreshLayout) view.findViewById(R.id.llMRefLayout);
            search_et = (AutoCompleteTextView) findViewById(R.id.search_et);
            ivSearch = (ImageView)findViewById(R.id.ivSearch);
            ivClear = (ImageView)findViewById(R.id.ivClear);
            ivSearchBig = (ImageView)findViewById(R.id.ivSearchBig);

            ivClear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    search_et.setText("");
                }
            });
            ivSearchBig.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    search_et.requestFocus();
                }
            });
            ivSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String query = search_et.getText().toString();

                    if(query !=null && query.length() > 0) {
                        AppBase.arrayListItems = null;
                        adapter = null;
                        MyData.apiQ = query;
                       // getVideoSearchDataList();
                    }
                    else
                    {
                        Snackbar.make(search_et,"Please enter video name",Snackbar.LENGTH_SHORT).show();
                    }

                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    }
                    else
                    {
                        requestNewShowInterstitial();
                    }

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

            //List
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
            recyclerView.setLayoutManager(linearLayoutManager);

            //Grid 2x2
           // recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), 2));
            recyclerView.setHasFixedSize(true);



            svSearchVideos.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {

                    AppBase.arrayListItems = null;
                    adapter = null;
                    MyData.apiQ = query;
                   // getVideoSearchDataList();

                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });

            Log.i("--setUserVisibleHint--","==  YSearchFragment  ==");


          //  getVideoSearchDataList();
            if(AppBase.arrayListItems !=null && AppBase.arrayListItems.size() > 0)
            {
                adapter = new YListAdapter(ActHome.this, AppBase.arrayListItems);
                recyclerView.setAdapter(adapter);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    private void requestNewInterstitial() {



        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("C04B1BFFB0774708339BC273F8A43708")
                .build();

        mInterstitialAd.loadAd(adRequest);
    }


    private void requestNewShowInterstitial() {
        Log.i("====tag====","=11111====requestNewShowInterstitial=");

        mInterstitialAd = new InterstitialAd(this);
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

/*
here get video list will be display with image and name in list with grid in home page

    public void getVideoSearchDataList() {

        bundle = getIntent().getExtras();

        if (bundle != null) {
            if (bundle.getString("channelId") != null && bundle.getString("channelId").toString().length() > 1) {
                MyData.apiQ = bundle.getString("channelId");
            }
        }
        customProgressDialog =  new ProgressDialog(ActHome.this);
        customProgressDialog.setCancelable(true);
        customProgressDialog.setMessage("Please wait...!");

        Retrofit retrofit = new Retrofit.Builder()
                //https://www.googleapis.com/youtube/v3
                .baseUrl("https://www.googleapis.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        methods = retrofit.create(IApiMethods.class);

        if(MyData.apiQ == null || MyData.apiQ.length() < 1)
        {
            MyData.apiQ = "latest";
        }
        //Call temp_call = temp.getHomePageList("v1","the-next-web","latest","03ac4499ca9b42e9a5ecea60cf2839e3");
        Call temp_call = methods.getSearchVideo(MyData.apiPart, MyData.apiQ, MyData.apiType, MyData.apiKey, MyData.apiMaxResults);
        //getHomePageList("bbc-sport", "top", "03ac4499ca9b42e9a5ecea60cf2839e3");


        temp_call.enqueue(new Callback<YTrailerModel>() {
            @Override
            public void onResponse(Call<YTrailerModel> call, Response<YTrailerModel> response) {

                customProgressDialog.dismiss();

                YTrailerModel model = response.body();
                if (model == null) {
                    //404 or the response cannot be converted to User.
                    Log.e("Test---null response--", "==Something wrong=");
                    ResponseBody responseBody = response.errorBody();
                    if (responseBody != null) {
                        try {
                            Log.e("Test---error-", "" + responseBody.string());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    //200 sucess
                    Log.v(Tag, "Sucess---1");
                    Log.v(Tag, "Response"+response.toString());

                    YTrailerModel curators = (YTrailerModel) response.body();

                    if(curators !=null && curators.getNextPageToken() !=null)
                    {
                        pageToken = curators.getNextPageToken();
                    }
                    else
                    {
                        pageToken = "";
                    }
                    String strData = "";
                    strData = curators.getKind() + " \n ";
                    Log.v(Tag, "Sucess---1---getKind---" + strData);

                    if (curators.getArrayListItems() != null && curators.getArrayListItems().size() > 0) {
                        if (AppBase.arrayListItems != null && AppBase.arrayListItems.size() > 3) {
                            AppBase.arrayListItems.addAll(curators.getArrayListItems());
                        } else {
                            AppBase.arrayListItems = new ArrayList<>();
                            AppBase.arrayListItems = curators.getArrayListItems();
                        }

                        if (adapter != null && adapter.getItemCount() > 3) {
                            try {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapter.notifyDataSetChanged();
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            adapter = new YListAdapter(ActHome.this, AppBase.arrayListItems);
                            recyclerView.setAdapter(adapter);
                        }

                    }
                    else
                    {
                        Snackbar.make(recyclerView," No data found. ",Snackbar.LENGTH_SHORT).show();
                    }


                }

            }

            @Override
            public void onFailure(Call<YTrailerModel> call, Throwable t) {
                t.printStackTrace();
                customProgressDialog.dismiss();
            }
        });


        //methods.getSeachVideosTitle(part, q,  type,key, maxResults, callback);
        if (AppBase.arrayListItems != null && AppBase.arrayListItems.size() > 2) {
            Log.e("-apiCall-", "--there are some data---");
            adapter = new YListAdapter(ActHome.this,AppBase.arrayListItems);
            recyclerView.setAdapter(adapter);
        }
        else {
            MyData.apiQ = "latest";
            Log.e("-apiCall-", "part-->" + MyData.apiPart + "--q-->" + MyData.apiQ + "--type-->" + MyData.apiType + "--key-->" + MyData.apiKey + "--maxResults-->" + MyData.apiMaxResults);
            methods.getSeachVideosTrailer(MyData.apiPart, MyData.apiQ, MyData.apiType, MyData.apiKey, MyData.apiMaxResults);
          //  customProgressDialog.show();
        }

    }

*/











    int LastPositionAutoLoad = 40;


    public class YListAdapter  extends RecyclerView.Adapter<YListAdapter.ViewHolder> {

        ArrayList<YTrailerModel.Items> mArrayListItems;
        Context mContext;

        public YListAdapter(Context context, ArrayList<YTrailerModel.Items> arrayListItems ) {
            mArrayListItems = arrayListItems;
            mContext = context;
        }
        @Override
        public YListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.raw_ytrailer_fragment, parent, false);

            return new YListAdapter.ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final YListAdapter.ViewHolder holder, final int position) {

            final YTrailerModel.Items yTrailerModel_Items  = mArrayListItems.get(position);
            holder.tvTtile.setText(position+"-"+yTrailerModel_Items.getSnippet().getVideoTitle());
            holder.tvDesc.setText(yTrailerModel_Items.getSnippet().getVideoDescription());

            Log.e("--SET data--","-SET-video id--"+yTrailerModel_Items.getId().getVideoId());

            if(position > LastPositionAutoLoad)
            {
                LastPositionAutoLoad = LastPositionAutoLoad + 40;
                if(pageToken!=null && pageToken.length() > 2)
                {
                    Log.e("--SET data--","-SET-pageToken==="+pageToken);
                    positionSelected = position;
try {
    methods.getSeachVideosTrailerNextPage(MyData.apiPart, MyData.apiQ, MyData.apiType, MyData.apiKey, pageToken, MyData.apiMaxResults);
}
catch (Exception e)
{
    e.printStackTrace();
}
                   // customProgressDialog.show();
                }

            }

            if( yTrailerModel_Items !=null && yTrailerModel_Items.getId() !=null && yTrailerModel_Items.getId().getVideoId()!=null) {

                final String strImgUrl = "https://i.ytimg.com/vi/" + yTrailerModel_Items.getId().getVideoId() + "/maxresdefault.jpg";
                //Picasso.with(mContext).load(strImgUrl).placeholder(R.drawable.ic_launcher).into(holder.ivImage);


                Glide.with(mContext)
                        .load(strImgUrl)
                        .placeholder(R.color.colorPrimary)
                        .error(R.color.colorPrimaryDark)
                        .into(holder.ivImage);


                holder.cardItemLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("--click on image--", "--video id--" + yTrailerModel_Items.getId().getVideoId());
                        Intent intYouTubePlayerView = new Intent(ActHome.this, ActYouTubePlayer.class);
                        intYouTubePlayerView.putExtra("from", "ActSearchVidTitleList");
                        intYouTubePlayerView.putExtra("videoID", yTrailerModel_Items.getId().getVideoId());
                        mContext.startActivity(intYouTubePlayerView);
                    }
                });



                holder.ivDownload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      // print log -   Log.e("--click on image long--", "--video id--" + yTrailerModel_Items.getId().getVideoId());

                     /*
                     remove comment for the open dialog for select file type for Download file types

                     setVideoDownload(yTrailerModel_Items.getId().getVideoId());

                     */


                      /*
                       for the load full -screen ads

                       if (mInterstitialAd.isLoaded()) {
                            mInterstitialAd.show();
                        }
                        else
                        {
                            requestNewShowInterstitial();
                        }*/







                    }
                });

            }
            else
            {
                Log.e("No found","--title not found--");
            }
            //holder.year.setText(movie.getYear());
        }

        @Override
        public int getItemCount() {
            return mArrayListItems.size();
        }
        public class ViewHolder extends RecyclerView.ViewHolder {
            CardView cardItemLayout;
            TextView tvTtile;
            TextView tvDesc;
            ImageView ivImage;
            ImageView ivDownload;
            public ViewHolder(View view) {
                super(view);
                cardItemLayout = (CardView) view.findViewById(R.id.cardlist_item);
                tvTtile = (TextView) view.findViewById(R.id.tvTtile);
                ivImage = (ImageView) view.findViewById(R.id.ivImage);
                tvDesc = (TextView) view.findViewById(R.id.tvDesc);
                ivDownload = (ImageView)view.findViewById(R.id.ivDownload);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private ProgressDialog mProgressDialog;
    private DownloadManager downloadManager;


  //  public static final String YOUTUBE = "https://m.youtube.com/watch?v=DoTPz4In3NA";
    //private String loadUrl = YOUTUBE;
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




    // for the download videos

    private  void setVideoDownload(String mVideoId)
    {
        Log.i("=mVideoId=","===mVideoId="+mVideoId);
        showWaitDialog();
        //Call resolution
        RxYoutube.fetchYoutube(mVideoId, resultAction, errorAction);

        downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
    }



    //this popup used for the get file type from youtube video
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
                    setTitle("Select the video download type").
                    setIcon(R.drawable.ic_app_icon)
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
                                   //  remove comment to download in Andorid/data/XXX.XXXXX(app package name) store here all video and audios
                                    // request.setDestinationInExternalFilesDir(ActHome.this, Environment.DIRECTORY_DOWNLOADS, fileName);

                                    // heere you can set your own download location
                                    request.setDestinationInExternalPublicDir("/"+MyData.folderName,fileName);
                                    downloadManager.enqueue(request);

                                    if (mInterstitialAd.isLoaded()) {
                                        mInterstitialAd.show();
                                    }
                                    else
                                    {
                                        requestNewShowInterstitial();
                                    }
                                }
                            });
                        }
                    }).
                            setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dismissWaitDialog();
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









/*
this function use for the auto suggestion when type n search text

    private void getVideosuggetions() {


        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        Youtubeapi youtubeapi = retrofit.create(Youtubeapi.class);


        Call<Example> call = youtubeapi.getVideos("video", "snippet", "relevance", search_et.getText().toString(),15, MyData.apiKey);


        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                if(response!=null && response.body() !=null && response.body().getItems() !=null && response.body().getItems().size() > 0) {
                    List<Item> list = response.body().getItems();
                    arrayList.clear();
                    for (int i = 0; i < list.size(); i++) {

                        String title = "";
                        title = list.get(i).getSnippet().getTitle();
                        arrayList.add(title);
                    }

                    suggestions();
                }
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {

            }
        });


    }


*/






    public void  suggestions(){
        String[] stockArr = new String[arrayList.size()];
        stockArr = arrayList.toArray(stockArr);

        adapterSearchHint = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, stockArr);
        search_et.setThreshold(1);
        search_et.setAdapter(adapterSearchHint);
        adapterSearchHint.notifyDataSetChanged();
    }



    boolean blnExit= false;

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        if(blnExit)
        {
            super.onBackPressed();
            return;
        }
        this.blnExit = true;

        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
        else
        {
            requestNewShowInterstitial();
        }

        Toast.makeText(ActHome.this,"Please click BACK again to EXIT.",Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                blnExit = false;
            }
        },2000);
    }
}