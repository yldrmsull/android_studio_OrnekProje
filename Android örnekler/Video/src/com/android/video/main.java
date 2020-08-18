package com.android.video;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class main extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        VideoView videoCalar = new VideoView(this);
        videoCalar.setMediaController(new MediaController(this));
        videoCalar.setVideoURI(Uri.parse("android.resource://com.android.Video/" + R.raw.video1));
        //videoCalar.setVideoPath("/sdcard/video1.mp4");
        videoCalar.requestFocus();
        setContentView(videoCalar);

        videoCalar.start(); 
    }
}
