package com.merhaba.sqlite;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;

public class MerhabaSQLiteActivity extends ListActivity implements OnClickListener {
	
	private VeriErisim dbYardimcisi;
	private Cursor cursor;
	Button ekleButonu;
	Button sorgulaButonu;
	Button silmeButonu;
	Button guncellemeButonu;
	SimpleCursorAdapter kitaplar;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ana);
        this.getListView().setDividerHeight(2);
        butonlariIlkle();
        dbYardimcisi = new VeriErisim(this);
        dbYardimcisi.veritabaninaBaglan();
        kayitlariGetir();
    }
    
    private void butonlariIlkle()
    {
    	this.ekleButonu = (Button)this.findViewById(R.id.buttonEkle);
    	this.ekleButonu.setOnClickListener(this);
    	this.sorgulaButonu = (Button)this.findViewById(R.id.buttonSorgula);
    	this.sorgulaButonu.setOnClickListener(this);
    	this.guncellemeButonu = (Button)this.findViewById(R.id.buttonGuncelle);
    	this.guncellemeButonu.setOnClickListener(this);
    	this.silmeButonu = (Button)this.findViewById(R.id.buttonSil);
    	this.silmeButonu.setOnClickListener(this);
    }
    
    private void kayitlariGetir() {
		cursor = dbYardimcisi.tumKitaplariGetir();
		startManagingCursor(cursor);

		String[] from = new String[] { VeriErisim.KITAPID_KOLONU,
				VeriErisim.KITAPADI_KOLONU,VeriErisim.KITAPYILI_KOLONU};
		int[] to = new int[] {R.id.textViewId,R.id.textViewName,R.id.textViewYear };

		
		kitaplar = new SimpleCursorAdapter(this,
				R.layout.satir, cursor, from, to);
		setListAdapter(kitaplar);
	}

	@Override
	public void onClick(View v) {
		
		if(v == this.ekleButonu)
		{
			Kitap kitap1 = new Kitap( "Merhaba Android", 2009);
			Kitap kitap2 = new Kitap( "Hello", 2010);
			Kitap kitap3 = new Kitap( "Android", 2011);
			dbYardimcisi.kitapEkle(kitap1);
			dbYardimcisi.kitapEkle(kitap2);
			dbYardimcisi.kitapEkle(kitap3);
			cursor = dbYardimcisi.tumKitaplariGetir();
			kitaplar.changeCursor(cursor);		
		}
		else if( v == this.sorgulaButonu)
		{
			cursor = dbYardimcisi.adaGoreKitapGetir("Android");
			kitaplar.changeCursor(cursor);
		}
		else if( v == this.guncellemeButonu)
		{
			if(cursor != null && !cursor.moveToFirst())
				return;

	        while (cursor.isAfterLast() == false) {
	        	Kitap kitap = new Kitap(cursor.getInt(0), cursor.getString(1), cursor.getInt(2));
	        	if(kitap.kitapAdi.equals("Android"))
	        	{
	        		kitap.kitapAdi = "Android Updated";
	        		dbYardimcisi.kitapGuncelle(kitap);
	        	}
	        	cursor.moveToNext();
	        }      
	        cursor.requery();       
		}
		else if( v == this.silmeButonu)
		{
			if(cursor != null && !cursor.moveToFirst())
				return;

	        while (cursor.isAfterLast() == false) {
	        	Kitap kitap = new Kitap(cursor.getInt(0), cursor.getString(1), cursor.getInt(2));
	        	if(kitap.kitapAdi.equals("Android Updated"))
	        	{
	        		dbYardimcisi.kitapSil(kitap);
	        	}
	        	cursor.moveToNext();
	        }
	        
	        cursor.requery();
		}
		
	}

	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (dbYardimcisi != null) {
			dbYardimcisi.veritabaniniKapat();
		}
	}
}