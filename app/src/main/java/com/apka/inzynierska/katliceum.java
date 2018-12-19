package com.apka.inzynierska;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class katliceum extends AppCompatActivity {

    private String typ = "liceum";
    private Button matb;
    private Button polskib;
    private Button infb;
    private Button angb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategorieliceum);
        Intent intent = getIntent();

        final Button matb = (Button) findViewById(R.id.matb);
        final Button polskib=(Button) findViewById(R.id.polskib);
        final Button fizb = (Button) findViewById(R.id.fizb);
        final Button angb=(Button) findViewById(R.id.angb);

        final Intent atmaterials=new Intent(katliceum.this,materialy.class);

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
}
