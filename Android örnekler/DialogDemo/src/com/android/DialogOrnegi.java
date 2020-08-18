package com.android;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.os.Bundle;
import android.view.KeyEvent;

public class DialogOrnegi extends Activity {
	
	static final int ISLEM_YAPILIYOR_DIALOG = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        showDialog(ISLEM_YAPILIYOR_DIALOG);
    }
    
    protected Dialog onCreateDialog(int id) {
		Dialog dialog = null;
		if (id == ISLEM_YAPILIYOR_DIALOG) {
			dialog=new Dialog(DialogOrnegi.this);
			dialog.setTitle("Islem yapiliyor...");
			OnKeyListener keyListener=new DialogInterface.OnKeyListener(){				
				public boolean onKey(DialogInterface di,int i,KeyEvent ev){					
					removeDialog(ISLEM_YAPILIYOR_DIALOG);						 
					return true;				 
				}
			};
			dialog.setOnKeyListener(keyListener);			
		}
		return dialog;
    }  
}