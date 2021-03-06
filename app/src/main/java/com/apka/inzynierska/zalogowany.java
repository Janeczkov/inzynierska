package com.apka.inzynierska;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class zalogowany extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zalogowany);
        Intent intent=getIntent();
        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle("Zostałeś zalogowany");
        actionbar.setDisplayHomeAsUpEnabled(true);

        final String username=intent.getStringExtra("username");
        final String rank=intent.getStringExtra("rank");

        final Intent katliceum=new Intent(zalogowany.this,katliceum.class);
        final Intent kattechnikum=new Intent(zalogowany.this,kattechnikum.class);
        final Intent dodajplik=new Intent(zalogowany.this,dodajplik.class);
        final Intent lokalne=new Intent(zalogowany.this,materialyLokalne.class);
        final Intent sprawdzanieMaterialow=new Intent(zalogowany.this,sprawdzanieMaterialow.class);
        final Intent listaUzytkownikow=new Intent(zalogowany.this,sprawdzanieUzytkownikow.class);

        final Button licb = findViewById(R.id.licb);
        final Button techb = findViewById(R.id.techb);
        final Button dodajb = findViewById(R.id.dodajb);
        final Button przegladajb = findViewById(R.id.przegladajb);
        final Button sprawdzajb = findViewById(R.id.sprawdzajb);
        final Button uzytkownicyb = findViewById(R.id.uzytkownicyb);

        Log.e("ranga", rank);
        if (rank.equals("2")) {
            sprawdzajb.setVisibility(View.VISIBLE);
        }
        else if (rank.equals("3")){
            sprawdzajb.setVisibility(View.VISIBLE);
            uzytkownicyb.setVisibility(View.VISIBLE);
        }

        licb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                katliceum.putExtra("username", username);
                katliceum.putExtra("rank", rank);
                zalogowany.this.startActivity(katliceum);
            }
        });

        techb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kattechnikum.putExtra("username", username);
                kattechnikum.putExtra("rank", rank);
                zalogowany.this.startActivity(kattechnikum);
            }
        });

        dodajb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dodajplik.putExtra("username", username);
                dodajplik.putExtra("rank", rank);
                zalogowany.this.startActivity(dodajplik);
            }
        });

        przegladajb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zalogowany.this.startActivity(lokalne);
            }
        });

        sprawdzajb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zalogowany.this.startActivity(sprawdzanieMaterialow);
            }
        });

        uzytkownicyb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zalogowany.this.startActivity(listaUzytkownikow);
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item){
        /*Intent myIntent = new Intent(getApplicationContext(), logowanie.class);
        startActivityForResult(myIntent, 0);*/
        finish();
        return true;
    }
}
