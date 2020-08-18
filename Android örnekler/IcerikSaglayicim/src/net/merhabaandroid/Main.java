package net.merhabaandroid;

import java.util.ArrayList;

import net.merhabaandroid.IcerikSaglayicim.Notlar;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts.People;
import android.provider.ContactsContract.Contacts;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Main extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        
        Button btn1 = (Button) findViewById(R.id.Button1);
        Button btn2 = (Button) findViewById(R.id.Button2);
        Button silDugmesi = (Button) findViewById(R.id.Button3);
        Button guncelleDugmesi = (Button) findViewById(R.id.GuncelleDugmesi);
        
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put(Notlar.NOTE, "İlk Not");
                Uri uri = getContentResolver().insert(IcerikSaglayicim.CONTENT_URI, values);
                Toast.makeText(Main.this, "Item Added",Toast.LENGTH_LONG).show();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String resultStr = "";
                Uri allTitles = IcerikSaglayicim.CONTENT_URI; //"content://"+ IcerikSaglayicim.AUTHORITY + "/"+ Notes.DATABASE_TABLE);
                String[] alanlar = new String[] {
                				Notlar.NOTE,
                				Notlar._ID
                };
                String filtre =  Notlar.NOTE +" = 'İlk Not' AND " + Notlar._ID +" =1 ";
                String sirala = Notlar._ID + " DESC ";
                Cursor c = managedQuery(allTitles, alanlar, null, null, sirala);
                if (c.moveToFirst()) {
                    do {
                        resultStr = c.getString(c.getColumnIndex(Notlar._ID))+ ", "+ c.getString(c.getColumnIndex(Notlar.NOTE));
                        Toast.makeText(Main.this, resultStr,Toast.LENGTH_SHORT).show();
                    } while (c.moveToNext());
                }
            }
        });
        
        silDugmesi.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
					getContentResolver().delete(IcerikSaglayicim.CONTENT_URI, Notlar._ID + "=" + "2", null);
			}
		});
        
        guncelleDugmesi.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ContentValues values = new ContentValues();
				values.put(Notlar.NOTE, "deneme");
				getContentResolver().update(IcerikSaglayicim.CONTENT_URI, values, null, null);
			}
		});
        
        
    }

    public void readContact() {

        ArrayList<String> contactList;
        contactList = new ArrayList<String>();

        String[] columns = new String[] { People.NAME, People.NUMBER };
        Uri mContacts = People.CONTENT_URI;
        Cursor mCur = managedQuery(mContacts, columns, null, null, People.NAME+ " ASC ");
        if (mCur.moveToFirst()) {
            do {
                contactList.add(mCur.getString(mCur.getColumnIndex(People.NAME)));
            } while (mCur.moveToNext());
        }
        Toast.makeText(this, contactList.size() + "", Toast.LENGTH_LONG).show();
    }
}

