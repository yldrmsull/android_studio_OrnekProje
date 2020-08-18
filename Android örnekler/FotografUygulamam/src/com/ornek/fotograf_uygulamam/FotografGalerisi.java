package com.ornek.fotograf_uygulamam;

import java.io.File;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class FotografGalerisi extends Activity {
	
	private File fotografKlasoru;
	private String[] fotograflarim;
	private Gallery fotoGalerim;

	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		
	    getWindow().requestFeature(Window.FEATURE_NO_TITLE);
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );
	  
		setContentView(R.layout.fotograflarim);
		
		fotoGalerim = (Gallery) findViewById(R.id.gallery_fotograflarim);
		
		fotografKlasoru = new File(getFilesDir().getAbsolutePath(), FotografCekKaydet.fotoKlasorAdi);
		fotograflarim = fotografKlasoru.list();
		
		fotoGalerim.setAdapter(new BaseAdapter() {
			
			public View getView(int position, View convertView, ViewGroup parent) {
				 ImageView imageView;
				   if (convertView == null) 
				   {  
			            imageView = new ImageView(getApplicationContext());
			            imageView.setLayoutParams(new Gallery.LayoutParams(Gallery.LayoutParams.FILL_PARENT , Gallery.LayoutParams.FILL_PARENT));
			            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER); 
			            BitmapFactory.Options options = new BitmapFactory.Options();
			      
			            options.inSampleSize = 1;
			            Bitmap bm = BitmapFactory.decodeFile(fotografKlasoru.getAbsolutePath()+"/"+fotograflarim[position], options);
			            imageView.setImageBitmap(bm);
			            //imageView.setImageURI(Uri.parse(fotografKlasoru.getAbsolutePath()+"/"+fotograflarim[position]));
			        } 
				   else 
				   {
			            imageView = (ImageView) convertView;
			       }
			       
			        return imageView;
			}
			
			public long getItemId(int position) {
				return position;
			}
			
			public Object getItem(int position) {
				return null;
			}
			
			public int getCount() {
				return fotograflarim.length;
			}
		});
		

	}

}
