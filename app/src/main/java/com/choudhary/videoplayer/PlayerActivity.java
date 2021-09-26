package com.choudhary.videoplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.halilibo.bvpkotlin.BetterVideoPlayer;

public class PlayerActivity extends AppCompatActivity {

    BetterVideoPlayer betterVideoPlayer;
    String Videopath ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_player);


        betterVideoPlayer = findViewById(R.id.better_Videoplayer);

        Videopath = getIntent().getStringExtra("VIDEO_URL");

        Uri uri = Uri.parse(Videopath);

        betterVideoPlayer.setSource(uri);
       





    }

    @Override
    protected void onPause() {
        super.onPause();
        betterVideoPlayer.pause();
    }
}