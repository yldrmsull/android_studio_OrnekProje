package com.merhaba.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class VeriErisim {

	private SQLiteDatabase db;
	private final Context context;
	private final VeriErisimYardimcisi dbYardimcisi;

	public static String KITAPID_KOLONU = "_id";
	public static String KITAPADI_KOLONU = "KITAPADI";
	public static String KITAPYILI_KOLONU = "YIL";
	public static String TABLO_ADI = "KITAPLAR";

	public VeriErisim(Context c) {
		context = c;
		dbYardimcisi = new VeriErisimYardimcisi(context);
		
	}

	public void veritabaniniKapat() {
		db.close();
	}

	public void veritabaninaBaglan() throws SQLiteException {
		try {
			db = dbYardimcisi.getWritableDatabase();
		} catch (SQLiteException ex) {

			db = dbYardimcisi.getReadableDatabase();
		}
	}

	public long kitapEkle(Kitap kitap) {
		try {
			ContentValues yeniContentValue = new ContentValues();

			yeniContentValue.put(KITAPADI_KOLONU, kitap.kitapAdi);
			yeniContentValue.put(KITAPYILI_KOLONU, kitap.yil);

			return db.insert(TABLO_ADI, null, yeniContentValue);
		} catch (SQLiteException ex) {

			return -1;
		}
	}

	public Cursor adaGoreKitapGetir(String arananKelime) {

		Cursor mCursor = db.query( TABLO_ADI, new String[] { KITAPID_KOLONU,
				KITAPADI_KOLONU, KITAPYILI_KOLONU }, KITAPADI_KOLONU + " like "
				+ "'%"+arananKelime+"%'", null, null, null, null);

		return mCursor;
	}
	
	public Cursor tumKitaplariGetir() {
		return db.query(TABLO_ADI, new String[] { KITAPID_KOLONU,
				KITAPADI_KOLONU, KITAPYILI_KOLONU }, null, null, null,
				null, null);
	}

	public int kitapGuncelle(Kitap kitap) {
		ContentValues cv = new ContentValues();
		cv.put(KITAPADI_KOLONU, kitap.kitapAdi);
		cv.put(KITAPYILI_KOLONU, kitap.yil);

		return db.update(TABLO_ADI, cv, KITAPID_KOLONU + "=?",
				new String[] { String.valueOf(kitap.id) });
	}

	public void kitapSil(Kitap kitap) {

		db.delete(TABLO_ADI, KITAPID_KOLONU + "=?",
				new String[] { String.valueOf(kitap.id) });
	}
}