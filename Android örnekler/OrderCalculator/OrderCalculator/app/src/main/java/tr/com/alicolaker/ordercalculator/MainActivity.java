package tr.com.alicolaker.ordercalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.security.PublicKey;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat = NumberFormat.getPercentInstance();
    TextView corbaFiyat,yemekFiyat,tatliFiyat,icecekFiyat,bahsisFiyat,toplamFiyat,hisseTextView;
    Spinner corbaSpinner,yemekSpinner,tatliSpinner,icecekSpinner;
    private double hisse = 0.15;
    private double toplamTutar = 0.0;
    double corbaTutari,yemekTutari,tatliTutari,icecekTutari;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SeekBar percentSeekBar = (SeekBar) findViewById(R.id.hisseSeekBar);
        percentSeekBar.setOnSeekBarChangeListener(seekBarListener);

        corbaFiyat = (TextView) findViewById(R.id.corbaFiyat);
        yemekFiyat = (TextView) findViewById(R.id.yemekFiyat);
        tatliFiyat = (TextView) findViewById(R.id.tatliFiyat);
        icecekFiyat = (TextView) findViewById(R.id.icecekFiyat);
        bahsisFiyat = (TextView) findViewById(R.id.bahsisFiyat);
        toplamFiyat = (TextView) findViewById(R.id.toplamFiyat);
        hisseTextView = (TextView) findViewById(R.id.hisseTextView);
        bahsisFiyat.setText(currencyFormat.format(0));
        toplamFiyat.setText(currencyFormat.format(0));
        corbaSpinner = (Spinner) findViewById(R.id.corbaSpinner);
        yemekSpinner = (Spinner) findViewById(R.id.yemekSpinner);
        tatliSpinner = (Spinner) findViewById(R.id.tatliSpinner);
        icecekSpinner = (Spinner) findViewById(R.id.iceckSpinner);


        corbaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        corbaFiyat.setText(currencyFormat.format(0));
                        hesaplama();
                        break;
                    case 1:
                        corbaFiyat.setText(currencyFormat.format(4));
                        corbaTutari = 4;
                        hesaplama();
                        break;
                    case 2:
                        corbaFiyat.setText(currencyFormat.format(5));
                        corbaTutari= 5;
                        hesaplama();
                        break;
                    case 3:
                        corbaFiyat.setText(currencyFormat.format(3));
                        corbaTutari= 3;
                        hesaplama();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        yemekSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        yemekFiyat.setText(currencyFormat.format(0));
                        hesaplama();
                        break;
                    case 1:
                        yemekFiyat.setText(currencyFormat.format(6));
                        yemekTutari = 6;
                        hesaplama();
                        break;
                    case 2:
                        yemekFiyat.setText(currencyFormat.format(5));
                        yemekTutari = 5;
                        hesaplama();
                        break;
                    case 3:
                        yemekFiyat.setText(currencyFormat.format(12));
                        yemekTutari = 12;
                        hesaplama();
                        break;
                    case 4:
                        yemekFiyat.setText(currencyFormat.format(18));
                        yemekTutari = 18;
                        hesaplama();
                        break;
                    case 5:
                        yemekFiyat.setText(currencyFormat.format(7));
                        yemekTutari = 7;
                        hesaplama();
                        break;
                    case 6:
                        yemekFiyat.setText(currencyFormat.format(16));
                        yemekTutari = 12;
                        hesaplama();
                        break;
                    case 7:
                        yemekFiyat.setText(currencyFormat.format(16));
                        yemekTutari = 12;
                        hesaplama();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        tatliSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        tatliFiyat.setText(currencyFormat.format(0));
                        hesaplama();
                        break;
                    case 1:
                        tatliFiyat.setText(currencyFormat.format(7));
                        tatliTutari = 7;
                        hesaplama();
                        break;
                    case 2:
                        tatliFiyat.setText(currencyFormat.format(7));
                        tatliTutari = 7;
                        hesaplama();
                        break;
                    case 3:
                        tatliFiyat.setText(currencyFormat.format(7));
                        tatliTutari = 7;
                        hesaplama();
                        break;
                    case 4:
                        tatliFiyat.setText(currencyFormat.format(7));
                        tatliTutari = 7;
                        hesaplama();
                        break;
                    case 5:
                        tatliFiyat.setText(currencyFormat.format(7));
                        tatliTutari = 7;
                        hesaplama();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        icecekSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        icecekFiyat.setText(currencyFormat.format(0));
                        hesaplama();
                        break;
                    case 1:
                        icecekFiyat.setText(currencyFormat.format(1));
                        icecekTutari = 1;
                        hesaplama();
                        break;
                    case 2:
                        icecekFiyat.setText(currencyFormat.format(1));
                        icecekTutari = 1;
                        hesaplama();
                        break;
                    case 3:
                        icecekFiyat.setText(currencyFormat.format(2));
                        icecekTutari = 2;
                        hesaplama();
                        break;
                    case 4:
                        icecekFiyat.setText(currencyFormat.format(1));
                        icecekTutari = 1;
                        hesaplama();
                        break;
                    case 5:
                        icecekFiyat.setText(currencyFormat.format(1.5));
                        icecekTutari = 1.5;
                        hesaplama();
                        break;
                    case 6:
                        icecekFiyat.setText(currencyFormat.format(0.75));
                        icecekTutari = 0.75;
                        hesaplama();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void hesaplama()
    {
        hisseTextView.setText(percentFormat.format(hisse));
        toplamTutar = corbaTutari + yemekTutari + tatliTutari + icecekTutari;
        double tip = toplamTutar * hisse;
        double total = toplamTutar + tip;

        bahsisFiyat.setText(currencyFormat.format(tip));
        toplamFiyat.setText(currencyFormat.format(total));
    }

    private final SeekBar.OnSeekBarChangeListener seekBarListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            hisse = progress / 100.0;
            hesaplama();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) { }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) { }

    };
}
