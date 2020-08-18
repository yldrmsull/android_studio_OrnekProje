package com.android.listview;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class myListView extends ListActivity {
    static final String[] COUNTRIES = new String[] { "Turkiye", "Amerika", "Almanya","Hindistan", "Suriye", "Irak"};
	/** Called when the activity is first created. */
	
    static final int DIALOG_PAUSED_ID = 0;
    static final int DIALOG_GAMEOVER_ID = 1;
    protected Dialog onCreateDialog(int id) {   
        switch(id) 
        {
        	case DIALOG_GAMEOVER_ID:
        		AlertDialog.Builder builder = new AlertDialog.Builder(this);
        		builder.setMessage("Cikmak istediginizden emin misiniz?")
        		       .setCancelable(false)
        		       .setPositiveButton("Yes", new DialogInterface.OnClickListener() 
        		       {
        		           public void onClick(DialogInterface dialog, int id) {
        		                myListView.this.finish();
        		           }
        		       })
        		       .setNegativeButton("No", new DialogInterface.OnClickListener() {
        		           public void onClick(DialogInterface dialog, int id) { 
        		        	   dialog.cancel();           }
        		       });
        		AlertDialog alert = builder.create();
        		return alert;
        	//break;
        	case DIALOG_PAUSED_ID:
            break;
        	default:
            return null;
        }
        return null;
    }

    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Yasam Dongusu", "onCreate cagrildi.");
        setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, COUNTRIES));
        
        android.widget.ListView l = getListView();
        l.setTextFilterEnabled(true);
        l.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
					long arg3) {
				Toast.makeText(getApplicationContext(),  ((TextView)view).getText(),
						Toast.LENGTH_SHORT).show();
				
			}
		});
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.game_menu, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.new_game:
        	Intent i = new Intent(this, activity2.class);
        	startActivity(i);
        	//Toast.makeText(this, "New game", Toast.LENGTH_SHORT).show();
            return true;
        case R.id.quit:
        	showDialog(DIALOG_GAMEOVER_ID);
             return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
    
	protected void onStart() {
		Log.i("Yasam Dongusu", "onStart cagrildi.");	
		super.onStart();
	}	
	protected void onResume() {
		Log.i("Yasam Dongusu", "onResume cagrildi.");
		super.onResume();
	}
	protected void onPause() {
		Log.i("Yasam Dongusu", "onPause cagrildi.");
		super.onPause();
	}
	protected void onStop() {
		Log.i("Yasam Dongusu", "onStop cagrildi.");
		super.onStop();
	}
	protected void onDestroy() {
		Log.i("Yasam Dongusu", "onDestroy cagrildi.");
		super.onDestroy();
	}
	protected void onRestart() {
		Log.i("Yasam Dongusu", "onRestart cagrildi.");
		super.onRestart();
	}

}