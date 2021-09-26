package com.choudhary.videoplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    ArrayList<Model> arrayList;
    RecyclerView recyclerView;
    Adapter adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        arrayList = new ArrayList<>();
        adapter = new Adapter(this,arrayList);


        recyclerView.setAdapter(adapter);

        Dexter.withContext(this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        readAllVideos();

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();



    }


    public void readAllVideos(){

        String[] projection = { MediaStore.Video.VideoColumns.DATA ,MediaStore.Video.Media.DISPLAY_NAME};
        Cursor cursor = this.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
        try {
            cursor.moveToFirst();
            do{

                String VideoPath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));

                File file = new File(VideoPath);
                int file_size = Integer.parseInt(String.valueOf(file.length()/1024));

                int sizeInMb = file_size/1024;

                Log.v("Videos2", VideoPath +"size__" + String.valueOf(sizeInMb)+"MB");
                 String timeDuration =  getVideoDuration(VideoPath);
                arrayList.add(new Model(VideoPath,timeDuration,String.valueOf(sizeInMb)+"MB"));
            }while(cursor.moveToNext());

            cursor.close();
            adapter.notifyDataSetChanged();


        } catch (Exception e) {
            e.printStackTrace();
        }




    }



    String getVideoDuration(String filePATH){

        MediaMetadataRetriever retriever = new MediaMetadataRetriever();

        File file1 = new File(filePATH);
//use one of overloaded setDataSource() functions to set your data source
        retriever.setDataSource(this, Uri.fromFile(file1));
        String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        long timeInMillisec = Long.parseLong(time );

        long lenght  = TimeUnit.MILLISECONDS.toSeconds(timeInMillisec)  ;


        int min = (int) (lenght/60);
        int sec = (int)lenght - min*60;

        String timeDurationofVideo = String.valueOf(min) +":" + String.valueOf(sec);

        retriever.release();
        return timeDurationofVideo;
    }


    // Belows methods are for other testing purpose

//    private void getAllShownImagesPath() {
//        Uri uri;
//        Cursor cursor;
//        int column_index_data, column_index_folder_name;
//        ArrayList<String> listOfAllImages = new ArrayList<String>();
//        String absolutePathOfImage = null;
//        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
//
//        String[] projection = { MediaStore.MediaColumns.DATA,
//                MediaStore.Images.Media.BUCKET_DISPLAY_NAME };
//
//        cursor = this.getContentResolver().query(uri, projection, null,
//                null, null);
//
//        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
//        column_index_folder_name = cursor
//                .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
//        while (cursor.moveToNext()) {
//            absolutePathOfImage = cursor.getString(column_index_data);
//
//            Log.v("Images", absolutePathOfImage);
//
//            listOfAllImages.add(absolutePathOfImage);
//        }
//
//    }




//    void getAllInstalledApp()  {
//        final PackageManager pm = getPackageManager();
//
//        ArrayList<Drawable>  iconlist = new ArrayList<>();
////get a list of installed apps.
//        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
//
//        Log.v("SIZE",String.valueOf(packages.size()));
//
//        for (ApplicationInfo packageInfo : packages) {
//            Log.d("APPINFO", "Installed package :" + packageInfo.packageName);
//            try {
//                iconlist.add(this.getPackageManager().getApplicationIcon(packageInfo.packageName));
//            }
//            catch (Exception e){
//
//            }
//
//            Log.d("APPINFO", "Source dir : " + packageInfo.sourceDir);
//            Log.d("APPINFO", "Launch Activity :" + pm.getLaunchIntentForPackage(packageInfo.packageName));
//        }
//
//
////        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
////        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
////        List<ResolveInfo> pkgAppsList = this.getPackageManager().queryIntentActivities( mainIntent, 0);
//
//
//
//    }
}