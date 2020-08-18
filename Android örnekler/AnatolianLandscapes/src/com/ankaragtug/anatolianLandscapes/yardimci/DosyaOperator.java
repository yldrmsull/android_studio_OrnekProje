package com.ankaragtug.anatolianLandscapes.yardimci;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;

public class DosyaOperator {
	private Context mContext;

	public DosyaOperator(Context context) {
		mContext = context;
	}
	
	public boolean dosyaIndirVeKaydet(String dosyaUrl, String dosyaIsmi){
		URL myFileUrl =null; 
		InputStream is = null;
		FileOutputStream fos = null;
		boolean result = true;
		try {
			myFileUrl= new URL(dosyaUrl);
			HttpURLConnection conn= (HttpURLConnection)myFileUrl.openConnection();
			conn.setDoInput(true);
			conn.connect();
			is = conn.getInputStream();
			byte[] buffer = new byte[1024];
			int readLength;
			fos = mContext.openFileOutput(dosyaIsmi, Context.MODE_PRIVATE);
			while ((readLength=is.read(buffer, 0 , buffer.length)) > 0 ){
				fos.write(buffer, 0, readLength);
			}
		} catch (MalformedURLException e) {
			result = false;
			e.printStackTrace();
		} catch (IOException e) {
			result = false;
			e.printStackTrace();
		}
		finally {
			try {
				if (fos != null) fos.close();
				if (is != null)  is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return result;
	}
	
	public InputStream dosyaAl(int dosyaIndeks, boolean kucukResimmi) {
		FileInputStream fis = null;
		String fileName = dosyaIsmiAl(dosyaIndeks, kucukResimmi);		

		try {
			fis = mContext.openFileInput(fileName);
		} catch (FileNotFoundException e) {
		}
		return fis;
	}

	public static String dosyaIsmiAl(int dosyaIndeks, boolean kucukResimmi) {
		String fileName;
		if (kucukResimmi == true) { 
			fileName = String.valueOf(dosyaIndeks) + "_thumb";
		}
		else { 
			fileName = String.valueOf(dosyaIndeks);
		}
		return fileName;
	}
	

}
