package com.ankaragtug.anatolianLandscapes.yardimci;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageView;

import com.ankaragtug.anatolianLandscapes.R;

public class ResimAdaptor extends BaseAdapter {
	private Context kontekst;
	private String[] kucukResimUrl;
	private String[] buyukResimUrl;
	private Bitmap[] bmap;
	private Bitmap yukleniyorBmap;
	private DosyaOperator dosyaOperator;
	private ArrayList<Integer> bekleyenResimler = new ArrayList<Integer>();
	private ResimYakalama resimYakalayici = null;
	private boolean kucukResimModumu = true;

	public ResimAdaptor(Context c, boolean modeThumb) {
		kontekst = c;
		this.kucukResimModumu = modeThumb;
		dosyaOperator = new DosyaOperator(kontekst);
		kucukResimUrl = kontekst.getResources().getStringArray(R.array.ThumbUrls);
		buyukResimUrl = kontekst.getResources().getStringArray(R.array.OriginalUrls);
		bmap = new Bitmap[kucukResimUrl.length];
		yukleniyorBmap = BitmapFactory.decodeResource(kontekst.getResources(), R.drawable.yukleniyor);

	}
	
	public boolean tumDosyalarIndirildimi() {
		int length = kucukResimUrl.length;
		for (int i = 0; i < length; i++) {
			File file = kontekst.getFileStreamPath(dosyaOperator.dosyaIsmiAl(i, true));
			if (file.exists() == true) {
				file = kontekst.getFileStreamPath(dosyaOperator.dosyaIsmiAl(i, false));
				if (file.exists() == false) {
					return false;
				}				
			}
			else {
				return false;
			}
		}
		return true;		
	}

	public int getCount() {
		return kucukResimUrl.length;
	}

	public Object getItem(int position) {
		return null;
	}
	
	public long getItemId(int position) {
		return position;
	}

	// Adaptör tarafýndan refaransý verilen herbir nesne için yeni bir ImageView yaratýr
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;
		if (convertView == null) 
		{  
			//Daha once degerlendirilmediyse, bazý deðiþkenleri ilklendir
			imageView = new ImageView(kontekst);
			if (kucukResimModumu == true) {
				imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
				imageView.setPadding(8, 8, 8, 8);
			}
			else {
				Gallery.LayoutParams layoutparams = new Gallery.LayoutParams(Gallery.LayoutParams.FILL_PARENT, Gallery.LayoutParams.WRAP_CONTENT);
				imageView.setLayoutParams(layoutparams);
			}
			
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		} 
		else {
		
			imageView = (ImageView) convertView;
		}
		if (bmap[position] == null) {
			
			InputStream is = dosyaOperator.dosyaAl(position, this.kucukResimModumu);
			if (is == null && bekleyenResimler.contains((Integer) position) == false) {
				bekleyenResimler.add(position);
				if (resimYakalayici == null || resimYakalayici.getStatus() == AsyncTask.Status.FINISHED) {
					resimYakalayici = new ResimYakalama();
					resimYakalayici.execute(position);
				}
			}
			else{
				bmap[position] = BitmapFactory.decodeStream(is);
			}
		}
		
		if (bmap[position] != null) {
			imageView.setImageBitmap(bmap[position]);
		}
		else {
			imageView.setImageBitmap(yukleniyorBmap);
		}
		return imageView;
	}

	/**
	 * sub-class of AsyncTask
	 */
	protected class ResimYakalama extends AsyncTask<Integer, Integer, Integer>
	{
		@Override
		protected Integer doInBackground(Integer... dataIndex) 
		{
			String url = kucukResimUrl[dataIndex[0]];
			if (ResimAdaptor.this.kucukResimModumu == false) {
				url = buyukResimUrl[dataIndex[0]];
			}
			dosyaOperator.dosyaIndirVeKaydet(url, dosyaOperator.dosyaIsmiAl(dataIndex[0], ResimAdaptor.this.kucukResimModumu));

			return dataIndex[0];
		}

		@Override
		protected void onPreExecute() 
		{
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(Integer fileIndex) 
		{
			InputStream is = dosyaOperator.dosyaAl(fileIndex, ResimAdaptor.this.kucukResimModumu);
			bmap[fileIndex] = BitmapFactory.decodeStream(is);
			notifyDataSetChanged();
			bekleyenResimler.remove(fileIndex);
			if (bekleyenResimler.size() > 0) {
				resimYakalayici = new ResimYakalama();
				resimYakalayici.execute(bekleyenResimler.get(0));
			}
			super.onPostExecute(fileIndex);
		}
	} 



}
