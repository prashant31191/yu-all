package com.provid15.appactivity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.provid15.R;
import com.provid15.utils.MyData;

import java.util.ArrayList;

/**
 * Created by Prashant on 10-12-2016.
 */
public class ActDownloadList extends AppCompatActivity {

    InterstitialAd mInterstitialAd;
    ArrayList<SongListModel> arrayListSongListModel = new ArrayList<>();
RecyclerView recyclerView;

    private AutoCompleteTextView search_et;
    ImageView ivSearch,ivClear,ivSearchBig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.fragment_ytrailer);
            recyclerView = (RecyclerView) findViewById(R.id.rvRecent);

            search_et = (AutoCompleteTextView) findViewById(R.id.search_et);
            ivSearch = (ImageView)findViewById(R.id.ivSearch);
            ivClear = (ImageView)findViewById(R.id.ivClear);
            //List
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
            recyclerView.setLayoutManager(linearLayoutManager);



            //Grid 2x2
            // recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), 2));
            recyclerView.setHasFixedSize(true);
            setSongData();
            setAdapterData();

            mInterstitialAd = new InterstitialAd(this);
            mInterstitialAd.setAdUnitId(MyData.strInterstitialId);//ca-app-pub-3940256099942544/1033173712

            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    requestNewInterstitial();

                }
            });

            requestNewInterstitial();

            ivClear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ivSearch.performClick();
                    search_et.setText("");
                }
            });

            ivSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String query = search_et.getText().toString();

                    search_et.setText("");

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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setSongData() {
        arrayListSongListModel.add(new SongListModel((R.drawable.images2), "Heathens", "Suicide Sqaud"));
        arrayListSongListModel.add(new SongListModel((R.drawable.images), "Starboy", "Starboy"));
        arrayListSongListModel.add(new SongListModel((R.drawable.images1), "Don't Wanna Know", "BackStreet"));

        arrayListSongListModel.add(new SongListModel((R.drawable.images3), "The Greatest", "The Greatest"));
        arrayListSongListModel.add(new SongListModel((R.drawable.images4), "Black Beatles", "sremmLife 2"));
        arrayListSongListModel.add(new SongListModel((R.drawable.images5), "Side to Side(feat. Nicki Minaj)", "Dangerous Woman"));
        arrayListSongListModel.add(new SongListModel((R.drawable.images6), "Can't Stop the Feeling!", "Trolls"));
        arrayListSongListModel.add(new SongListModel((R.drawable.images7), "This Is What You Came For", "ToyBot"));
        arrayListSongListModel.add(new SongListModel((R.drawable.images8), "Rockabe(feat. Sean Paul)", "Sean Paul"));
        arrayListSongListModel.add(new SongListModel((R.drawable.images9), "One Dance", "Jimmy"));
        arrayListSongListModel.add(new SongListModel((R.drawable.images10), "Treat You Better", "Treat You Better"));
        arrayListSongListModel.add(new SongListModel((R.drawable.images11), "Don't Let Me Down", "Don't Let Me Down"));
        arrayListSongListModel.add(new SongListModel((R.drawable.images12), "Say You Won't Let Go", "Back from the Edge"));
        arrayListSongListModel.add(new SongListModel((R.drawable.images13), "In the Name of Love", "The Love"));
        arrayListSongListModel.add(new SongListModel((R.drawable.images14), "Mercy", "Illuminate"));
        arrayListSongListModel.add(new SongListModel((R.drawable.images15), "Love Me Now", "Darkness and Light"));
        arrayListSongListModel.add(new SongListModel((R.drawable.images16), "We Don't Talk Anymore", "Nine Track Mind"));
        arrayListSongListModel.add(new SongListModel((R.drawable.images17), "Perfect Strangers", "Runway"));
        arrayListSongListModel.add(new SongListModel((R.drawable.images18), "Dancing on My Own", "The Chaser"));
        arrayListSongListModel.add(new SongListModel((R.drawable.images19), "Pillowtalk", "Mind of Mine"));
        arrayListSongListModel.add(new SongListModel((R.drawable.images20), "Need Me", "Anti"));
        arrayListSongListModel.add(new SongListModel((R.drawable.images21), "That's My Girl", "The Girls"));
        arrayListSongListModel.add(new SongListModel((R.drawable.images22), "Million Reasons", "Joanne"));


    }
    private void setAdapterData()
    {
        YListAdapter  adapter = new YListAdapter(ActDownloadList.this, arrayListSongListModel);
        recyclerView.setAdapter(adapter);
    }


    public class SongListModel {
        int intSongImage;
        String strSongTitle;
        String strSinger;

        public SongListModel(int intSongImage, String strSongTitle, String strSinger) {
            this.intSongImage = intSongImage;
            this.strSongTitle = strSongTitle;
            this.strSinger = strSinger;
        }
    }


    public class YListAdapter extends RecyclerView.Adapter<YListAdapter.ViewHolder> {

        ArrayList<SongListModel> mArrayListItems;
        Context mContext;

        public YListAdapter(Context context, ArrayList<SongListModel> arrayListItems) {
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

            final SongListModel yTrailerModel_Items = mArrayListItems.get(position);
            holder.tvTtile.setText(position + "-" + yTrailerModel_Items.strSongTitle);
            holder.tvDesc.setText(yTrailerModel_Items.strSinger);


            Glide.with(mContext)
                    .load(yTrailerModel_Items.intSongImage)
                    .placeholder(R.color.colorPrimary)
                    .error(R.color.colorPrimaryDark)
                    .into(holder.ivImage);


            holder.cardItemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("--click on image--", "--video id--" );

                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    }
                    else
                    {
                        requestNewShowInterstitial();
                    }
                }
            });


            /*holder.ivDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });*/
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
            ivDownload = (ImageView) view.findViewById(R.id.ivDownload);
        }
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


}
