package com.example.user.gumrukmasrafhesaplamasistemi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class giris extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris);

        Button gecis = (Button) findViewById(R.id.gecis);
        gecis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gec =new Intent(giris.this , MainActivity.class);
                startActivity(gec);
            }
        });
    }
}
