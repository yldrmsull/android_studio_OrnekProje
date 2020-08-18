package com.android.FormGenel;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import android.widget.ToggleButton;

public class FormGenel extends Activity {
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final Button dugme = (Button)findViewById(R.id.dugme);
        dugme.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
			    Toast.makeText(FormGenel.this, "Dügmeye bastiniz", Toast.LENGTH_SHORT).show();
			}
		});

        final EditText duzKutusu = (EditText)findViewById(R.id.duzenlemeKutusu);
        duzKutusu.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                    (keyCode == KeyEvent.KEYCODE_ENTER)) {
                  Toast.makeText(FormGenel.this, duzKutusu.getText(), Toast.LENGTH_SHORT).show();
                  return true;
                }
                return false;
            }
        });

        final CheckBox onayKutusu = (CheckBox)findViewById(R.id.onayKutusu);
        onayKutusu.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    Toast.makeText(FormGenel.this, "Onay Kutusu Seçili", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(FormGenel.this, "Onay Kutusu Seçili Degil", Toast.LENGTH_SHORT).show();
                }
            }
        });
        
        final RadioButton birinciSecenek = (RadioButton) findViewById(R.id.birinciSecenek);
        final RadioButton ikinciSecenek = (RadioButton) findViewById(R.id.ikinciSecenek);
        OnClickListener secenekBekle = new OnClickListener() {
            public void onClick(View v) {
                RadioButton secilenDugme = (RadioButton) v;
                Toast.makeText(FormGenel.this, secilenDugme.getText(), Toast.LENGTH_SHORT).show();
            }
        };
        birinciSecenek.setOnClickListener(secenekBekle);
        ikinciSecenek.setOnClickListener(secenekBekle);
        
        final ToggleButton ciftDurumluDugme = (ToggleButton) findViewById(R.id.ciftDurumluDugme);
        ciftDurumluDugme.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (ciftDurumluDugme.isChecked()) {
                    Toast.makeText(FormGenel.this, "Çift Durumlu Dügme Seçili", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(FormGenel.this, "Çift Durumlu Dügme Seçili Degil", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
