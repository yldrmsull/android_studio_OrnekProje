package net.merhabaandroid;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Main extends Activity {
    private BroadcastReceiver zamanDinleyicim;

	private BroadcastReceiver ozelYayinAlicim = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Log.i("Ozel Yayin Alicim", "Yayin alindi...");
		}
	};

    
    public static final String YAYIN_ADI = "net.merhabaandroid.yayin_adi";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        zamanDinleyicim = new ZamanDinleyici();
        IntentFilter filtre = new IntentFilter(Intent.ACTION_TIME_CHANGED);
        registerReceiver(zamanDinleyicim, filtre);
        
        Button yayinGonderici = (Button) findViewById(R.id.yayinGonderici);
        yayinGonderici.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setAction(YAYIN_ADI);
				sendBroadcast(intent);
			}
		});
        IntentFilter ozelFiltrem = new IntentFilter(YAYIN_ADI);
		registerReceiver(ozelYayinAlicim, ozelFiltrem);
    }
    
    @Override
    protected void onDestroy() {
    	unregisterReceiver(zamanDinleyicim);
    	unregisterReceiver(ozelYayinAlicim);
    	super.onDestroy();
    }    

}