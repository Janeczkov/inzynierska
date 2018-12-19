package com.apka.inzynierska;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class kattechnikum extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_kategorietechnikum);

        Intent intent = getIntent();

        final Button matb = findViewById(R.id.matb);
        final Button polskib=(Button) findViewById(R.id.polskib);
        final Button infb = (Button) findViewById(R.id.infb);
        final Button angb=(Button) findViewById(R.id.angb);

        final Intent atmaterials=new Intent(kattechnikum.this,materialy.class);

        matb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atmaterials.putExtra("typ", "Technikum");
                atmaterials.putExtra("kategoria", "Matematyka");
                kattechnikum.this.startActivity(atmaterials);
            }
        });
        polskib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atmaterials.putExtra("typ", "Technikum");
                atmaterials.putExtra("kategoria", "Polski");
                kattechnikum.this.startActivity(atmaterials);
            }
        });
        infb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atmaterials.putExtra("typ", "Technikum");
                atmaterials.putExtra("kategoria", "Informatyka");
                kattechnikum.this.startActivity(atmaterials);
            }
        });
        angb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atmaterials.putExtra("typ", "Technikum");
                atmaterials.putExtra("kategoria", "Angielski");
                kattechnikum.this.startActivity(atmaterials);
            }
        });
    }
}