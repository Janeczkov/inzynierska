package com.apka.inzynierska;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class typszkoly extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_typszkoly);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle("Typ szkoły");
        actionbar.setDisplayHomeAsUpEnabled(true);


        final Intent katliceum = new Intent(typszkoly.this, katliceum.class);
        final Intent kattechnikum = new Intent(typszkoly.this, kattechnikum.class);



        final Button licb = findViewById(R.id.licb);
        final Button techb = findViewById(R.id.techb);

        licb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                katliceum.putExtra("rank", "0");
                typszkoly.this.startActivity(katliceum);
            }
        });
        techb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kattechnikum.putExtra("rank", "0");
                typszkoly.this.startActivity(kattechnikum);
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
