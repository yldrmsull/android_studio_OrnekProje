package com.android.TuglaOyunu;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GorunumThread extends Thread {
    private Ekran anaEkran;
    private SurfaceHolder surfaceHolder;
    private boolean calismaDurumu = false;
    private long baslangicZamani;
    private long gecenSure;
    
    public GorunumThread(Ekran ekran) {
        anaEkran = ekran;
        surfaceHolder = anaEkran.getHolder();
    }
    
    @Override
    public void run() {
        Canvas canvas = null;
        while (calismaDurumu) {
            baslangicZamani = System.currentTimeMillis();
            canvas = surfaceHolder.lockCanvas();
            if (canvas != null) {
                anaEkran.animate(gecenSure);
                anaEkran.doDraw(canvas);
                gecenSure = System.currentTimeMillis() - baslangicZamani;
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }

    public void calismaDurumuAyarla(boolean yeniCalismaDurumu) {
        calismaDurumu = yeniCalismaDurumu;
    }
    
}
