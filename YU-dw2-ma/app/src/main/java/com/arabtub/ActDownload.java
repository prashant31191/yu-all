package com.arabtub;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.arabtub.utils.MyData;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Admin on 11/19/2016.
 */

public class ActDownload extends Activity {
    String TAG = "==ActDownload===";

    ListView listDownload;

    ArrayList<String> arrayListStrFileName = new ArrayList<>();
    String path = Environment.getExternalStorageDirectory().toString() + "/"+ MyData.folderName;

    private static Handler mainThreadHandler = new Handler(Looper.getMainLooper());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_mydownloads);

        listDownload = (ListView) findViewById(R.id.listDownload);

        if (Build.VERSION.SDK_INT >= 23) {
           /* if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted");
                setDataDownloads();
            }*/
        } else
        {
            Log.v(TAG, "Permission is granted");
            setDataDownloads();
        }


    }

    private void setDataDownloads() {
        try {
            arrayListStrFileName = new ArrayList<>();


            Log.d("Files", "Path: " + path);
            File directory = new File(path);
            File[] files = directory.listFiles();
            Log.d("Files", "Size: " + files.length);
            for (int i = 0; i < files.length; i++) {
                Log.d("Files", "FileName:" + files[i].getName());
                arrayListStrFileName.add(files[i].getName());
            }

            if(arrayListStrFileName !=null && arrayListStrFileName.size() > 0)
            {
                DownloadFileAdapter downloadFileAdapter = new DownloadFileAdapter(ActDownload.this,arrayListStrFileName);
                listDownload.setAdapter(downloadFileAdapter);
            }
            else
            {
                Toast.makeText(ActDownload.this,"Oops..! Not yes any download files.",Toast.LENGTH_SHORT).show();
            }



        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private class DownloadFileAdapter extends BaseAdapter
    {
        Context mContext;
        ArrayList<String> mArrayListStrFiles;
        private LayoutInflater inflater;

        public DownloadFileAdapter( Context context, ArrayList<String> arrayListStrFiles)
        {
            mContext = context;
            mArrayListStrFiles = arrayListStrFiles;
            inflater = LayoutInflater.from(context);

        }

        @Override
        public int getCount() {
            return mArrayListStrFiles.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int position, View converView, ViewGroup viewGroup) {
            ViewHolder holder;
            if(converView == null)
            {
                converView=inflater.inflate(R.layout.raw_dowload_list, null);
                holder = new ViewHolder();

                holder.tvTtile = (TextView)converView.findViewById(R.id.tvTtile);
                converView.setTag(holder);
            }
            else
            {
                holder = (ViewHolder)converView.getTag();
            }
            holder.tvTtile.setText(arrayListStrFileName.get(position).toString());

            holder.tvTtile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("==file path is =="+path+arrayListStrFileName.get(position).toString());


                    String strFilePath = path+arrayListStrFileName.get(position).toString();
                    File filestrFilePath = new File(path+File.separator+arrayListStrFileName.get(position).toString());

                    MimeTypeMap myMime = MimeTypeMap.getSingleton();
                    Intent newIntent = new Intent(Intent.ACTION_VIEW);

                   String mimeType = myMime.getMimeTypeFromExtension(getFileExt(strFilePath));
                    System.out.println("==filestrFilePath=="+filestrFilePath);

                    if (Build.VERSION.SDK_INT >= 23) {
                        Uri myUri = FileProvider.getUriForFile(ActDownload.this, BuildConfig.APPLICATION_ID + ".provider", filestrFilePath);
                        newIntent.setDataAndType(myUri, mimeType);
                    }
                    else {
                        newIntent.setDataAndType(Uri.parse("file://" + filestrFilePath.getAbsolutePath()), mimeType);
                    }


                    try {
                        startActivity(newIntent);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(ActDownload.this, "No handler for this type of file.", Toast.LENGTH_LONG).show();
                    }
                    catch (Exception e) {
                        // TODO: handle exception
                        System.out.println("=============Myexp=====");
                        e.printStackTrace();
                    }
                }
            });
            return converView;

        }

        public  String getFileExt(String fileName) {
            return fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
        }

        class ViewHolder
        {
            TextView tvTtile;
            ImageView img;
        }
    }












    public  boolean isStorageReadPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                setDataDownloads();
                return true;
            } else {

                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
            return true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
            //resume tasks needing this permission
        }
        else
        {
            Toast.makeText(ActDownload.this,"Please allow permission.",Toast.LENGTH_SHORT).show();
            isStorageReadPermissionGranted();
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
        Toast.makeText(ActDownload.this,"Please click BACK again to EXIT.",Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                blnExit = false;
            }
        },2000);
    }
}
