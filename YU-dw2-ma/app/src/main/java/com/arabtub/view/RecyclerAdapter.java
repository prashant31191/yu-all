package com.arabtub.view;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.arabtub.R;

import java.util.ArrayList;

/**
 * Created by Sourav on 17-11-2016.
 */
public class RecyclerAdapter extends BaseAdapter{

    private Activity activity;
    private ArrayList<String> arrayList;
    private ArrayList<String> thumb_arrayList;
    private static LayoutInflater layoutInflater=null;

    public RecyclerAdapter(Activity activity, ArrayList<String> arrayList,ArrayList<String> thumb_arrayList){
        this.activity=activity;
        this.arrayList=arrayList;
        this.thumb_arrayList=thumb_arrayList;

        layoutInflater=(LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public static class Viewholder{
        ImageView thumbnail;
        TextView title;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v=view;
        Viewholder viewholder;

        if(view==null){
            v=layoutInflater.inflate(R.layout.recyclerview_item,null);
            viewholder = new Viewholder();
            viewholder.title = (TextView) v.findViewById(R.id.title_tv);
            viewholder.thumbnail=(ImageView)v.findViewById(R.id.thumbnail);

            /************  Set holder with LayoutInflater ************/
            v.setTag( viewholder );
        }
        else
            viewholder=(Viewholder)v.getTag();

        viewholder.title.setText(arrayList.get(i));
        Glide.with(activity).load(thumb_arrayList.get(i)).into(viewholder.thumbnail);

        return v;
    }
}
