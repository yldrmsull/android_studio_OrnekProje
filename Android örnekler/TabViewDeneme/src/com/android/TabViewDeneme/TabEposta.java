package com.android.TabViewDeneme;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class TabEposta extends Activity{
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    TextView textview = new TextView(this);
    textview.setPadding(30, 150, 0, 0);
    textview.setTextSize(30);
    textview.setText("E-POSTA EKRANI");
    setContentView(textview);
  }
}
