package com.android.TabViewDeneme;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

public class TabViewClass extends TabActivity
{
  public void onCreate(Bundle savedInstanceState)
  {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.tabview);

	Resources res = getResources();
	TabHost tabHost = getTabHost();
	TabHost.TabSpec spec;
	Intent intent;

	//Arama sekmesi icin tabSpec elde et, tabHost'a ekle
	intent = new Intent().setClass(this, TabArama.class);
	spec = tabHost.newTabSpec("arama").setIndicator("Arama",
         res.getDrawable(R.drawable.tabselector_arama)).setContent(intent);
	tabHost.addTab(spec);

	//SMS sekmesi icin tabSpec elde et, tabHost'a ekle
	intent = new Intent().setClass(this, TabSms.class);
    spec = tabHost.newTabSpec("sms").setIndicator("SMS",
	     res.getDrawable(R.drawable.tabselector_sms)).setContent(intent);
    tabHost.addTab(spec);

    //E-Posta sekmesi icin tabSpec elde et, tabHost'a ekle
    intent = new Intent().setClass(this, TabEposta.class);
    spec = tabHost.newTabSpec("eposta").setIndicator("E-Posta",
	     res.getDrawable(R.drawable.tabselector_eposta)).setContent(intent);
    tabHost.addTab(spec);
  }
}
