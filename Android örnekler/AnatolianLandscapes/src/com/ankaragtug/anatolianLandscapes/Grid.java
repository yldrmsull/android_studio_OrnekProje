/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ankaragtug.anatolianLandscapes;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.ankaragtug.anatolianLandscapes.yardimci.ResimAdaptor;

/**
 * A grid that displays a set of framed photos.
 *
 */
public class Grid extends Activity {


	public static String FILE_INDEX = "FILE_INDEX"; 

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.grid);

		GridView g = (GridView) findViewById(R.id.myGrid);
		ResimAdaptor adapter = new ResimAdaptor(this, true);
		if (adapter.tumDosyalarIndirildimi() == false) 
		{
			ConnectivityManager conManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = conManager.getActiveNetworkInfo();
			boolean connected = false;
			if (networkInfo != null) {
				connected = networkInfo.isConnected();
			}	

			if (connected == false) {
				new AlertDialog.Builder(this)       
				.setMessage(R.string.not_connected_to_network)
				.setPositiveButton(R.string.Ok, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {						
						finish();
					}
				})        
				.create().show(); 
			}
		}
		g.setAdapter(adapter);

		g.setOnItemClickListener(new OnItemClickListener() {


			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
				Intent i = new Intent(Grid.this, Slaytlar.class);
				i.putExtra(FILE_INDEX, position);
				startActivity(i);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch(item.getItemId()) {
		case R.id.about_menu_item:
			new AlertDialog.Builder(this)       
			.setMessage(R.string.aboutApp).setTitle(R.string.about)
			.setPositiveButton(R.string.Ok, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					dialog.cancel();
				}
			})        
			.create().show();
			break;
		}
		return true;
	}

}
