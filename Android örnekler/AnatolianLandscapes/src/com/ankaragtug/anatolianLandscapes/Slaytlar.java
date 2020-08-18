
package com.ankaragtug.anatolianLandscapes;


import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ViewSwitcher.ViewFactory;

import com.ankaragtug.anatolianLandscapes.yardimci.DosyaOperator;

public class Slaytlar extends Activity  implements OnTouchListener, ViewFactory{
	private ImageView solOkResmi;
	private ImageView sagOkResmi;
	private Runnable okKaldirici = new Runnable() {
		@Override
		public void run() {
			solOkResmi.setVisibility(View.GONE);
			sagOkResmi.setVisibility(View.GONE);
		}
	};
	private int xBaslangicNoktasi;
	private int yBaslangicNoktasi;
	private ImageSwitcher resimDegistirici;
	private ConnectivityManager baglantiYoneticisi;
	private ProgressDialog ilerlemeDialogu;

	private int aktifResimIndeksi;
	private DosyaOperator dosyaOperator;
	private String[] buyukResimUrl;
	private LinearLayout header;
	private BroadcastReceiver duvarKagidiDinleyicisi;

	/** Aktivite yaratildiginda bu fonksiyon cagirilmaktadir*/
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);


		baglantiYoneticisi = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		dosyaOperator = new DosyaOperator(this);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.slaytlar);

		duvarKagidiDinleyicisi = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				Toast.makeText(getApplicationContext(), getString(R.string.wallpaperChanged), Toast.LENGTH_SHORT).show();
			}
		};

		IntentFilter filter = new IntentFilter(Intent.ACTION_WALLPAPER_CHANGED);
		registerReceiver(duvarKagidiDinleyicisi, filter);

		//		layout = (RelativeLayout) findViewById(R.id.layout);
		header  = (LinearLayout) findViewById(R.id.header);


		resimDegistirici = (ImageSwitcher) findViewById(R.id.imageSwitcher);
		aktifResimIndeksi = getIntent().getIntExtra(Grid.FILE_INDEX, 0);
		
		resimDegistirici.setFactory(this);
		buyukResimUrl = getResources().getStringArray(R.array.OriginalUrls);

		resimDegistirici.setOnTouchListener(this);
		Animation hyperspaceJump2 = AnimationUtils.loadAnimation(this, R.anim.karartmak);

		resimDegistirici.setInAnimation(hyperspaceJump2);
		resimDegistirici.setOutAnimation(hyperspaceJump2);

		solOkResmi = (ImageView) findViewById(R.id.leftArrow);
		solOkResmi.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				oncekiResmiGoster();				
			}
		});

		sagOkResmi = (ImageView) findViewById(R.id.rightArrow);
		sagOkResmi.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				sonrakiResmiGoster();
			}
		});

		Button setWallpaper = (Button) findViewById(R.id.setwallpaper);
		setWallpaper.setOnClickListener(new OnClickListener() {

			public void onClick(View view) {
				ilerlemeDialogu = ProgressDialog.show(Slaytlar.this, "", "Setting Wallpaper");
				
				Thread t = new Thread(){
					@Override
					public void run() {
						duvarKagidiDegistir(aktifResimIndeksi);
					}
				};
				t.start();
				

			}
		});

		resmiGoster(aktifResimIndeksi);

	}

	private void duvarKagidiDegistir(int resimIndeksi) {	
		final WallpaperManager wallpaperYoneticisi = WallpaperManager.getInstance(getApplicationContext());
		//final Drawable wallpaperDrawable = wallpaperManager.getDrawable();

		try {
			InputStream is = dosyaOperator.dosyaAl(resimIndeksi, false);
			if (is != null) {
				//		wallpaperManager.setBitmap(BitmapFactory.decodeStream(is));
				//		wallpaperManager.setResource(R.drawable.ww);
				
				Bitmap originalImage = BitmapFactory.decodeStream(is);
				
				
				double mOutputX = getWindowManager().getDefaultDisplay().getWidth()*2; 
				double mOutputY = getWindowManager().getDefaultDisplay().getHeight();
				
				Bitmap croppedImage = Bitmap.createBitmap((int)mOutputX, (int)mOutputY,
						Bitmap.Config.RGB_565);
				Canvas canvas = new Canvas(croppedImage);
				int originalWidth = originalImage.getWidth();
				int originalHeight = originalImage.getHeight();
				double ratio = (double)originalWidth / (double)originalHeight;
				int sourceWidth = originalWidth, 
					sourceHeight = originalHeight; 
				double wallPaperRatio = mOutputX / mOutputY;
				if (ratio > wallPaperRatio) {
					sourceWidth = (int)(sourceHeight * wallPaperRatio);
				}
				else if (ratio < wallPaperRatio) {
					sourceHeight = (int)(sourceWidth / wallPaperRatio);
				}
				
				Rect srcRect = new Rect(0, 0, sourceWidth, sourceHeight);
				Rect dstRect = new Rect(0, 0, (int)mOutputX, (int)mOutputY);
				canvas.drawBitmap(originalImage, srcRect, dstRect, null);
				
				wallpaperYoneticisi.setBitmap(croppedImage);

				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						ilerlemeDialogu.cancel();
						finish();						
					}
				});
				
			}					
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	private int resmiGoster(int resimIndeksi) {
		InputStream is = dosyaOperator.dosyaAl(resimIndeksi, false);
		if (is != null) {
			Drawable drawable = Drawable.createFromStream(is, DosyaOperator.dosyaIsmiAl(resimIndeksi, false));
			resimDegistirici.setImageDrawable(drawable);

		}
		else {
			NetworkInfo networkInfo = baglantiYoneticisi.getActiveNetworkInfo();
			boolean connected = false;
			if (networkInfo != null) {
				connected = networkInfo.isConnected();
			}		
			if (connected == true) {
				resimDegistirici.setImageResource(R.drawable.yukleniyor);
				new ResimIndir().execute(resimIndeksi);
			}
			else {
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
		return resimIndeksi;

	}

	@Override
	public View makeView() {
		ImageView i = new ImageView(this);
		i.setBackgroundColor(0xFF000000);
		i.setScaleType(ImageView.ScaleType.FIT_CENTER);
		i.setLayoutParams(new ImageSwitcher.LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));
		return i;
	}



	//	@Override
	public boolean onTouch(View v, MotionEvent event) {

		if (event.getAction() == MotionEvent.ACTION_DOWN){
			xBaslangicNoktasi = (int) event.getX();
			yBaslangicNoktasi = (int) event.getY();
		}
		else if (event.getAction() == MotionEvent.ACTION_UP) {
			int xBitisNoktasi = (int) event.getX();
			int yBitisNoktasi = (int) event.getY();

			try {
				if ( (Math.abs(xBitisNoktasi - xBaslangicNoktasi) - Math.abs(yBitisNoktasi - yBaslangicNoktasi)) > 0 ) {
					if ((xBitisNoktasi - xBaslangicNoktasi) > 10) {
						Log.i("touch", "previous image");
						oncekiResmiGoster();
					}
					else if ((xBitisNoktasi - xBaslangicNoktasi) < -10) {
						Log.i("touch", "next image");
						sonrakiResmiGoster();
					}
					else {
						Log.i("touch", "no move event");
						throw new Exception();
					}
				}
				else {
					Log.i("touch", "no move event");
					throw new Exception();
				}
			}
			catch (Exception e) {
				Log.i("touch", "no move event exception");
				solOkResmi.setVisibility(View.VISIBLE);
				sagOkResmi.setVisibility(View.VISIBLE);
				
				solOkResmi.removeCallbacks(okKaldirici);
				solOkResmi.postDelayed(okKaldirici, 2000);
			}

		}
		return true;
	}

	private void oncekiResmiGoster() {
		aktifResimIndeksi--;
		if (aktifResimIndeksi < 0) {
			aktifResimIndeksi = buyukResimUrl.length - 1;
		}
		resmiGoster(aktifResimIndeksi);
	}

	private void sonrakiResmiGoster() {
		aktifResimIndeksi++;
		if (aktifResimIndeksi >= buyukResimUrl.length) {
			aktifResimIndeksi = 0;		
		}
		resmiGoster(aktifResimIndeksi);
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

	@Override
	protected void onDestroy() {
		unregisterReceiver(duvarKagidiDinleyicisi);
		super.onDestroy();
	}


	protected class ResimIndir extends AsyncTask<Integer, Integer, Integer>
	{
		@Override
		protected Integer doInBackground(Integer... dataIndex) 
		{
			dosyaOperator.dosyaIndirVeKaydet(buyukResimUrl[dataIndex[0]], dosyaOperator.dosyaIsmiAl(dataIndex[0], false));

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
			resmiGoster(aktifResimIndeksi);
			super.onPostExecute(fileIndex);
		}
	} 
}
