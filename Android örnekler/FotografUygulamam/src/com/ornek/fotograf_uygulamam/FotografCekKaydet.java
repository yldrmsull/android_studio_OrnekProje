package com.ornek.fotograf_uygulamam;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class FotografCekKaydet extends Activity {
	
	private SurfaceView yuzey;
	private SurfaceHolder yuzeyKalibi;
	private Camera kamera;
	private ImageView fotoCekmeDugmesi;
	
	public static final String fotoKlasorAdi = "fotograf_dizini";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView(R.layout.kamera_onizleme);
       
        yuzey = (SurfaceView) findViewById(R.id.kamera_onizleme);
        yuzeyKalibi = yuzey.getHolder();
        yuzeyKalibi.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        
        
        yuzeyKalibi.addCallback(new SurfaceHolder.Callback() {			
			public void surfaceDestroyed(SurfaceHolder arg0) {
				 kamera.stopPreview();	         
	             kamera.release(); 				
			}			
			public void surfaceCreated(SurfaceHolder holder) {
				kamera = Camera.open();		
				
				try {
					kamera.setPreviewDisplay(holder);
				} catch (IOException e) {
					e.printStackTrace();
				}  
				kamera.startPreview();	
			}
			
			public void surfaceChanged(SurfaceHolder holder, int forwat, int w, int h) {		
			}
		});   
        
        fotoCekmeDugmesi = (ImageView) findViewById(R.id.foto_cekme_dugmesi); 
        
        fotoCekmeDugmesi.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				kamera.takePicture(null, null, null, new Camera.PictureCallback() {
					public void onPictureTaken(byte[] data, Camera camera) {
						fotografKaydet(data);
						Intent i = new Intent(getApplicationContext(), FotografGalerisi.class);
						startActivity(i);
						
					}
				});
				
			}
		});
        
    }
    
    
    
  private void fotografKaydet(byte[] data)
  {  	
	    /* getFilesDir() fonksiyonu uygulamanın dosyalarını tuttuğu dizini(klasoru)
	       bize döndürüyor. Bu dizin her uygulamaya için ayrıdır. 
	  	*/
	  	File fileDir = getFilesDir();
	  	/* fotograf klasörü için nesne oluşturuyoruz. Nesneyi oluştururken
	  	 * ilk parametre olarak klasorun olacağı dizinin tam adresini giriyoruz,
	  	 *  ikinci parametre olarak klasorun adını giriyoruz. 
	  	 */
	  	File fotografKlasoru = new File(fileDir.getAbsolutePath(), fotoKlasorAdi);
	  	
	  	/* Fotoğraf klasörünün var olup olmadığını kontrol ediyoruz.*/
	  	if (fotografKlasoru.exists() == false) {
	  		/* Eğer klasör önceden oluşturulmadı ise, aşağıdaki satırla klasörü oluşturuyoruz */
	  		fotografKlasoru.mkdir();
	  	}
	  	
	  	/* Fotoğrafları tarih ve zaman kullanarak isimlendireceğimiz için 
	  	 * takvim nesnesinin referansını alıyoruz
	  	 */	  	
	  	Calendar calendar = Calendar.getInstance();
	  	/* Fotoğrafın dosya adını olustuyurouz. Yıl, Ay, Gun, Saat, Dakika ve Saniyeden olusuyor.*/
	  	String filename = calendar.get(Calendar.YEAR) + "_" 
	  						+ (calendar.get(Calendar.MONTH) + 1) + "_"
	  						+ calendar.get(Calendar.DAY_OF_MONTH)+ "_" 
	  						+ calendar.get(Calendar.HOUR) + "_" 
	  						+ calendar.get(Calendar.MINUTE)+ "_"
	  						+ calendar.get(Calendar.SECOND)+ ".jpg";
	  	
	  	// Fotoğraf dosyasını olusturuyoruz. 
	  	File fotograf = new File(fotografKlasoru.getAbsolutePath(), filename);
	  
	  	try {
	  		/* Aşağıdaki 3 satırla fotoğraf dosyasını yazmak için açıp, veriyi yazıp
	  		   dosyayı kapatıyoruz. */
	  		FileOutputStream fos = new FileOutputStream(fotograf);
			fos.write(data);
			fos.close();    	
	  	} catch (IOException e) {
				e.printStackTrace();
		}
  }
    
    
}