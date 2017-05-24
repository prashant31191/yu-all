package com.arabtub.api;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Adminsss on 08-02-2016.
 */

public class SearchChannelList {


    @SerializedName("kind")
    String kind;

    @SerializedName("nextPageToken")
    String nextPageToken;

    public String getNextPageToken() {
        return nextPageToken;
    }

    @SerializedName("items")
    ArrayList<Items> arrayListItems = new ArrayList<>();


     public class Items
     {
         @SerializedName("snippet")
         Snippet snippet;

         public Snippet getSnippet() {
             return snippet;
         }

         public void setSnippet(Snippet snippet) {
             this.snippet = snippet;
         }
     }

    public class Snippet
    {

        @SerializedName("channelId")
        String channelId;
        @SerializedName("title")
        String title;
        @SerializedName("description")
        String description;

        @SerializedName("thumbnails")
        Thumbnails thumbnails;

        @SerializedName("channelTitle")
        String channelTitle;

        public String getChannelId() {
            return channelId;
        }

        public String getChannelTitle() {
            return channelTitle;
        }

        public String getDescription() {
            return description;
        }

        public String getTitle() {
            return title;
        }

        public Thumbnails getThumbnails() {
            return thumbnails;
        }

    }


    public class Thumbnails
    {
        @SerializedName("high")
        High high;

        public High getHigh() {
            return high;
        }

    }
    public class High
    {
        @SerializedName("url")
        String url;

        public String getUrl() {
            return url;
        }
    }






    public ArrayList<Items> getArrayListItems() {
        return arrayListItems;
    }

    public void setArrayListItems(ArrayList<Items> arrayListItems) {
        this.arrayListItems = arrayListItems;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }
}