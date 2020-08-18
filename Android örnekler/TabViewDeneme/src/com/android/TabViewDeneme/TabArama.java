package com.android.TabViewDeneme;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class TabArama extends Activity {
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    TextView textview = new TextView(this);
    textview.setPadding(45, 150, 0, 0);
    textview.setTextSize(30);
    textview.setText("ARAMA EKRANI");
    setContentView(textview);
  }
}
