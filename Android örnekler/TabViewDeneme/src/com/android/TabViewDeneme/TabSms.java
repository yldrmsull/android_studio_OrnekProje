package com.android.TabViewDeneme;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class TabSms extends Activity{
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    TextView textview = new TextView(this);
    textview.setPadding(55, 150, 0, 0);
    textview.setTextSize(30);
    textview.setText("SMS EKRANI");
    setContentView(textview);
  }
}
