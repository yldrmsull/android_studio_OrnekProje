package com.example.user.gumrukmasrafhesaplamasistemi;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import java.text.NumberFormat;




public class MainActivity extends AppCompatActivity {
    private static final NumberFormat numbercurrency = NumberFormat.getCurrencyInstance();
    private static final NumberFormat numberpercent = NumberFormat.getPercentInstance();

    private TextView amountTextView;
    private TextView percentLabelTextView;
    private TextView ordinoTextView;
    private TextView kdvTextView;
    private TextView sigortaTextView;
    private TextView dvergiTextView;
    private TextView dgiderTextView;
    private TextView totalTextView,totaltext;
    private double billAmount = 0.0;
    private double percent = 0.10;
    private Button hesapla;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        amountTextView = (TextView) findViewById(R.id.amountTextView);
        percentLabelTextView = (TextView) findViewById(R.id.percentLabelTextView);
        ordinoTextView = (TextView) findViewById(R.id.ordinoTextView);
        kdvTextView=(TextView) findViewById(R.id.kdvTextView);
        sigortaTextView = (TextView) findViewById(R.id.sigortaTextView);
        dvergiTextView = (TextView) findViewById(R.id.dvergiTextView);
        dgiderTextView = (TextView) findViewById(R.id.dgiderTextView);
        totalTextView = (TextView) findViewById(R.id.totalTextView);
        totaltext = (TextView) findViewById(R.id.totalLabelTextView);
        ordinoTextView.setText(numbercurrency.format(200));
        sigortaTextView.setText(numbercurrency.format(300));
        dvergiTextView.setText(numbercurrency.format(8));
        dgiderTextView.setText(numbercurrency.format(Math.random()*80+120));
        totalTextView.setText(numbercurrency.format(0));
        hesapla =(Button) findViewById(R.id.hesap);
        totaltext.setTextColor(Color.TRANSPARENT);
        totalTextView.setTextColor(Color.TRANSPARENT);
        totalTextView.setBackgroundColor(Color.TRANSPARENT);


        EditText amountEditText = (EditText) findViewById(R.id.amountEditText);
        amountEditText.setTextColor(Color.TRANSPARENT);
        amountEditText.addTextChangedListener(amountEditTextWatcher);



        SeekBar percentSeekBar = (SeekBar) findViewById(R.id.percentSeekBar);
        percentSeekBar.setOnSeekBarChangeListener(seekBarListener);
    }




    private void gumruk() {
        // format percent and display in percentTextView
        percentLabelTextView.setText(numberpercent.format(percent));
        double ordino=200,sigorta=300,dvergi=8,dgider=Math.random() *80+120;
        double KDVDEGERİ = billAmount * percent;
        final double total = billAmount + KDVDEGERİ+ordino+sigorta+dvergi+dgider;

        // display tip and total formatted as currency
        kdvTextView.setText(numbercurrency.format(KDVDEGERİ));
        ordinoTextView.setText(numbercurrency.format(ordino));
        sigortaTextView.setText(numbercurrency.format(sigorta));
        dgiderTextView.setText(numbercurrency.format(dgider));
        dvergiTextView.setText(numbercurrency.format(dvergi));
        hesapla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                totaltext.setTextColor(Color.BLUE);
                totalTextView.setBackgroundColor(Color.BLACK);
                totalTextView.setTextColor(Color.BLUE);
                totalTextView.setText(numbercurrency.format(total));
            }
        });
        //totalTextView.setText(numbercurrency.format(total));
    }

    // listener object for the SeekBar's progress changed events
    private final OnSeekBarChangeListener seekBarListener =new OnSeekBarChangeListener() {
        // update percent, then call calculate
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            percent = progress / 100.0;
            percentLabelTextView.setText(numberpercent.format(percent));
            gumruk();
        }
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) { }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) { }
    };


    // listener object for the EditText's text-changed events
    private final TextWatcher amountEditTextWatcher = new TextWatcher() {


        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            try { // get bill amount and display currency formatted value

                billAmount = Double.parseDouble(s.toString()) / 100;
                amountTextView.setText(numbercurrency.format(billAmount));
            } catch (NumberFormatException e) { // if s is empty or non-numeric
                amountTextView.setText("");
                billAmount = 0.0;
            }

            gumruk();
        }
        @Override
        public void afterTextChanged(Editable s) { }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
    };

 }









































