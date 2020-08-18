package com.android.audio;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;

public class main extends Activity {

	private MediaPlayer muzikCalar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

		if (muzikCalar != null) 
			muzikCalar.release();
		muzikCalar = MediaPlayer.create(this, R.raw.beyaz);
        if (muzikCalar != null)
        	muzikCalar.start();
    }
}
