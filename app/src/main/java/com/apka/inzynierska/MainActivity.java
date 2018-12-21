package com.apka.inzynierska;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle("Menu główne");

        final Button mainguestb = findViewById(R.id.mainguestb);
        final Button mainloginb = findViewById(R.id.mainloginb);
        final Button mainregisterb = findViewById(R.id.mainregisterb);
        final Button przegladajb = findViewById(R.id.przegladajb);

        final Intent atguest=new Intent(MainActivity.this,typszkoly.class);
        final Intent atlogin=new Intent(MainActivity.this,logowanie.class);
        final Intent atregister=new Intent(MainActivity.this,rejestracja.class);
        final Intent atlocal=new Intent(MainActivity.this,materialyLokalne.class);

        mainguestb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.startActivity(atguest);
            }


        /*final Toast grzanka=Toast.makeText(this, //kontekst-zazwyczaj referencja do Activity
                "heck", //napis do wyświetlenia
                Toast.LENGTH_SHORT);



        this.przycisk = (Button) this.findViewById(R.id.bocen);

        przycisk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //przycisk.setText("ha");
                grzanka.show();
            }
            */

        });

        przegladajb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.startActivity(atlocal);
            }


        /*final Toast grzanka=Toast.makeText(this, //kontekst-zazwyczaj referencja do Activity
                "heck", //napis do wyświetlenia
                Toast.LENGTH_SHORT);



        this.przycisk = (Button) this.findViewById(R.id.bocen);

        przycisk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //przycisk.setText("ha");
                grzanka.show();
            }
            */

        });

        mainloginb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.startActivity(atlogin);
            }
        });

        mainregisterb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.startActivity(atregister);
            }
        });
    }
}