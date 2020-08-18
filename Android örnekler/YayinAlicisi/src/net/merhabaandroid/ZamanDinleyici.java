package net.merhabaandroid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ZamanDinleyici extends BroadcastReceiver{
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i("Zaman Dinleyici", "Zaman degisti...");
	}
}
