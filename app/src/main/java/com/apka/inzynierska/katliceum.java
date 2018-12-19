package com.apka.inzynierska;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class katliceum extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategorieliceum);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle("Liceum");
        actionbar.setDisplayHomeAsUpEnabled(true);

        Intent intent=getIntent();
        final String rank = intent.getStringExtra("rank");

        final Button matb = findViewById(R.id.matb);
        final Button polskib = findViewById(R.id.polskib);
        final Button fizb = findViewById(R.id.fizb);
        final Button angb = findViewById(R.id.angb);

        final Intent atmaterials=new Intent(katliceum.this,materialy.class);
        atmaterials.putExtra("rank", rank);

        matb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atmaterials.putExtra("typ", "Liceum");
                atmaterials.putExtra("kategoria", "Matematyka");
                katliceum.this.startActivity(atmaterials);
            }
        });
        polskib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atmaterials.putExtra("typ", "Liceum");
                atmaterials.putExtra("kategoria", "Polski");
                katliceum.this.startActivity(atmaterials);
            }
        });
        fizb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atmaterials.putExtra("typ", "Liceum");
                atmaterials.putExtra("kategoria", "Fizyka");
                katliceum.this.startActivity(atmaterials);
            }
        });
        angb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atmaterials.putExtra("typ", "Liceum");
                atmaterials.putExtra("kategoria", "Angielski");
                katliceum.this.startActivity(atmaterials);
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
