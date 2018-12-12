package com.apka.inzynierska;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class zalogowany extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_zalogowany);

        Intent intent=getIntent();

        final Intent katliceum=new Intent(zalogowany.this,katliceum.class);
        final Intent dodajplik=new Intent(zalogowany.this,dodajplik.class);

        final Button licb = (Button) findViewById(R.id.licb);
        final Button techb = (Button) findViewById(R.id.techb);
        final Button dodajb = (Button) findViewById(R.id.dodajb);

        licb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zalogowany.this.startActivity(katliceum);
            }
        });

        dodajb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zalogowany.this.startActivity(dodajplik);
            }
        });
    }
}
