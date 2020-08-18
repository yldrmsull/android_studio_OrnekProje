package com.android;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;

public class ProgressActivity extends Activity {
	private ProgressDialog progDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// show the dialog
		progDialog = ProgressDialog.show(ProgressActivity.this, "",
				"Islem Yapiliyor...", true);
	}

}