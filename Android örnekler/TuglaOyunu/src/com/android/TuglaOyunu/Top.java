package com.android.TuglaOyunu;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Top {
    private float x, y;
    
    private int hizX, hizY;
    
    public static int ilkHizX = 3;
    public static int ilkHizY = -3;
    
    private Bitmap bitmap;
    
    private boolean hareketli = false;
    
    public Top(Resources res, int ilkX, int ilkY) {
        bitmap = BitmapFactory.decodeResource(res, R.drawable.top);
        x = ilkX;
        y = ilkY;
        hizX = ilkHizX;
        hizY = ilkHizY;
    }
    
    public void doDraw(Canvas canvas) {
        canvas.drawBitmap(bitmap, x, y, null);
    }
    
    public void animate(long gecenSure) {
       	x += hizX * (gecenSure / 15f);
       	y += hizY * (gecenSure / 15f);
       	ekranKenarCarpismasi();
    }

    private void ekranKenarCarpismasi() {
        if (x <= 0) {
            hizX = -hizX;
            x = 0;
        } else if (x + bitmap.getWidth() >= Ekran.genislik) {
            hizX = -hizX;
            x = Ekran.genislik - bitmap.getWidth();
        }

        if (y <= 0) {
            hizY = -hizY;
            y = 0;
        }
    }
    
    public int getHizX() {
    	return hizX;
    }

    public int getHizY() {
    	return hizY;
    }
    
    public void setHizX(int yeniHiz) {
    	hizX = yeniHiz;
    }

    public void setHizY(int yeniHiz) {
    	hizY = yeniHiz;
    }

    public float getX() {
    	return x;
    }
    
    public float getY() {
    	return y;
    }
    
    public void setX(float yeniX) {
    	x = yeniX;
    }
    
    public void setY(float yeniY) {
    	y = yeniY;
    }
    
    public int getGenislik() {
    	return bitmap.getWidth();
    }

    public int getYukseklik() {
    	return bitmap.getHeight();
    }
    
    public void durdur() {
    	hareketli = false;
    }

    public void kostur() {
    	hareketli = true;
    }
    
    public boolean hareketlimi() {
    	return hareketli;
    }
}
