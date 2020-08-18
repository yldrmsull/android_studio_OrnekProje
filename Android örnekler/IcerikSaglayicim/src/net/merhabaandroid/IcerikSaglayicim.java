package net.merhabaandroid;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;
import android.util.Log;

public class IcerikSaglayicim extends ContentProvider {
    public static final Uri CONTENT_URI = Uri.parse("content://net.merhabaandroid.iceriksaglayicim");    
    private SQLiteDatabase veritabani;    

    public static final class Notlar implements BaseColumns {
        public static final String TABLO_ADI = "notlar";
        public static final String ICERIK_TIPI = "vnd.android.cursor.dir/net.merhabaandroid.notes";
        public static final String _ID = "_id";
        public static final String NOTE = "note";
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        SQLiteOperator operator = new SQLiteOperator(context);
        veritabani = operator.getWritableDatabase();
        return (veritabani == null) ? false : true;
    }

    @Override
    public String getType(Uri uri) {
    	return Notlar.ICERIK_TIPI;
    }
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long rowID = veritabani.insert(Notlar.TABLO_ADI, "", values);
        if (rowID > 0) {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
            return _uri;
        }
        throw new SQLException("Veri eklenemedi");
    }
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,String[] selectionArgs, String sortOrder) {
        Cursor c = veritabani.query(Notlar.TABLO_ADI, projection, selection, selectionArgs, null, null, sortOrder);
        return c;
    }
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
        count = veritabani.delete(Notlar.TABLO_ADI, selection,selectionArgs);
        return count;
    }
    @Override
    public int update(Uri uri, ContentValues values, String selection,String[] selectionArgs) {
        int count = 0;
        count = veritabani.update(Notlar.TABLO_ADI, values, selection,selectionArgs);
        return count;
    }
    
    public class SQLiteOperator extends SQLiteOpenHelper {
        private static final String VERITABANI_ADI = "DEMODB";
        private static final int VERSIYON = 1;
        private static final String TABLO_OLUSTURMA_KODU = "CREATE TABLE "+ Notlar.TABLO_ADI + " (_id INTEGER NOT NULL CONSTRAINT USER_PK PRIMARY KEY AUTOINCREMENT, note TEXT)";
        public SQLiteOperator(Context context) {
            super(context, VERITABANI_ADI, null, VERSIYON);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(TABLO_OLUSTURMA_KODU);
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
}
