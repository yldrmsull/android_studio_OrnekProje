package com.ornek.HaritaVeGps;


import java.util.Calendar;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class Harita extends MapActivity {
	/**
	 * GPS aktif değil ise kullanıcıya göstereceğimiz ileti kutusunun numarası
	 */
    private final int GPS_AKTIF_HALE_GETIRILMELI = 0;
	/**
	 * GPS alıcısını yönetmemizi sağlayacak konumYöneticisi
	 */
    private LocationManager konumYoneticisi;
    /**
     * GPS alıcısından veri geldiğinde haberdar olmamızı sağlayacak konumDinleyicisi
     */
    private LocationListener konumDinleyicisi;
    /**
     * Ekranda gözüken haritayı yönetmemizi sağlayacak haritaGorunumu
     */
	private MapView haritaGorunumu;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);   
        /* 
         * harita görünümünün referansını kendi haritaGorunumu nesnemize atıyoruz. 
         */
        haritaGorunumu = (MapView) findViewById(R.id.mapview);
        /* 
         * harita zum kontrollerini etkin hale getiriyoruz. 
         */
        haritaGorunumu.setBuiltInZoomControls(true);    
        /* 
         * haritanın merkezini ayarlamak için Türkiye üzerinde bir nokta giriyoruz 26-45, 36-42 arasında ;)
         */
        GeoPoint nokta = new GeoPoint((int)(38 * 1e6), (int)(36 * 1e6));
        /* haritanın merkezini ayarlıyoruz. */
        haritaGorunumu.getController().setCenter(nokta);
        
        /*
         * konumDinleyicisi nesnemizi ilkliyoruz. Bu nesnemizin uygun şekilde
         * konum değişikliklerini alabilmesi için konumYoneticisi nesnemize kayıt ettirmeliyiz.
         * Ayrıca biz konum bilgisini GPS alıcısını kullanarak öğreneceğiz,
         * İlerleyen bölümlerde hem konumDinleyicisi nesnemizi kayıt ettireceğiz hem de konum bilgisini
         * GPS alıcısını kullanarak hesaplamasını isteyeceğiz. 
         */
        konumDinleyicisi = new LocationListener() {	
        	/**
        	 * Konum bilgisini sunan alıcının durumu değiştiğinde çağrılan fonksiyon.
        	 */
			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {
				//Bu örnekte burada herhangi bir işlem yapmamız gerekmiyor.
			}	
			/**
			 * Konum bilgisini sunan alıcı etkin hale getirildiğinde çağrılan fonksiyon.
			 */
			@Override
			public void onProviderEnabled(String provider) {
				//Bu örnekte burada herhangi bir işlem yapmamız gerekmiyor.
			}	
			/**
			 * Konum bilgisisni sunan alıcı pasif hale getirildiğinde çağrılan fonksiyon.
			 */
			@Override
			public void onProviderDisabled(String provider) {
				//Bu örnekte burada herhangi bir işlem yapmamız gerekmiyor.
			}		
			/**
			 * Her konum değiştiğinde çağrılan fonksiyon.
			 */
			@Override
			public void onLocationChanged(Location location) 
			{
				/* Uygulama "Konumumu Haritada Göster" menü elemanı seçildiğinde
				 * kendini konumYoneticisine kendini kayıt ettiriyor, konum bilgisini hesaplatıyor
				 * ve konum bilgisi geldiğinde konumYoneticisindeki kaydını siliyor.
				 */
				konumYoneticisi.removeUpdates(konumDinleyicisi);
				if (location != null) 
				{
					final GeoPoint nokta = new GeoPoint((int)(location.getLatitude() * 1e6), (int)(location.getLongitude() * 1e6));
				    Overlay konumum = new Overlay(){
				    	@Override
				    	public boolean draw(android.graphics.Canvas canvas, MapView mapView, boolean shadow, long when) {
				    		 super.draw(canvas, mapView, shadow);  
				       
				             Point screenPts = new Point();
				             mapView.getProjection().toPixels(nokta, screenPts);		
				          
				             Bitmap bmp = BitmapFactory.decodeResource(
				                 getResources(), R.drawable.icon);            
				             canvas.drawBitmap(bmp, screenPts.x, screenPts.y, null);    
				    		return true;
				    	};
				    	
				    };				    
						
			        List<Overlay> mapOverlays = haritaGorunumu.getOverlays();
			        mapOverlays.clear();
			        mapOverlays.add(konumum);
			        
			        haritaGorunumu.getController().setCenter(nokta);
			        
					Toast.makeText(getApplicationContext(), 
							"Konum bilgisi hesaplandi", 
							Toast.LENGTH_LONG).show();
				}
				else {
					Toast.makeText(getApplicationContext(), 
									R.string.gps_konum_bilgisi_hesaplanamadi, 
									Toast.LENGTH_LONG).show();
					
				}				
			}
		};            
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
   	 	MenuInflater inflater = getMenuInflater();
   	    inflater.inflate(R.menu.harita_menu , menu);
   	    return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	
    	switch (item.getItemId()){
    		case R.id.konumumu_harita_goster:
    			/* sistem de çalışan konum yöneticisinin referansını alıyoruz.*/
    			konumYoneticisi = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    			/* GPS alıcısının etkin olup olmadığın kontrol ediyoruz. */ 
    			 if (konumYoneticisi.isProviderEnabled(LocationManager.GPS_PROVIDER) == true)
    			 {
    				 /* GPS etkin ise bir aşağıdaki gibi konumDinleyici'sini konumYoneticisine
    				  * kayıt ettiriyoruz. */
    					Thread locationUpdates = new Thread() {

							public void run() {
    					          Looper.prepare();    					          
    					          konumYoneticisi.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, 
    										konumDinleyicisi);    					          
    					          Looper.loop();
    					      }    					
    					};		    								  
    					locationUpdates.start();
    			 }
    			 else {
    				 /* GPS etkin değil ise kullanıcıya bilgi veriyoruz. */
    				 showDialog(GPS_AKTIF_HALE_GETIRILMELI);
    			 }
    			 break;
    	
    	}
    	return super.onOptionsItemSelected(item);
    }

    /**
     * MapActivity sınıfında tanımlanmış abstract fonksiyon
     * olduğundan burada kodlamamız gerekiyor. Bu uygulamada herhangi bir
     * rota göstermediğimiz için false dönüyoruz.
     */
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id)
		{
		case GPS_AKTIF_HALE_GETIRILMELI:
			/* GPS kapalı ise kullanıcıya bilgi veren ileti kutusunun 
			   oluşturulması*/
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			/* aşağıdaki satır için res/values/strings.xml dosyasında 
			 * gps_aktif_hale_getiriniz adında bir karakter dizisi tanımlamalıyız 
			 */
			builder.setMessage(R.string.gps_aktif_hale_getiriniz)
			.setNeutralButton(R.string.tamam, new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
				}
			});
			return builder.create();
		}
		return super.onCreateDialog(id);
	}
	

}