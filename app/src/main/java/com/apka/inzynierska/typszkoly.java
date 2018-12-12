package com.apka.inzynierska;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class typszkoly extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_typszkoly);

        Intent intent=getIntent();

        final Intent katliceum=new Intent(typszkoly.this,katliceum.class);
        final Intent kattechnikum=new Intent(typszkoly.this,kattechnikum.class);

        final Button licb = (Button) findViewById(R.id.licb);
        final Button techb = (Button) findViewById(R.id.techb);

        licb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                typszkoly.this.startActivity(katliceum);
            }
        });
        techb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                typszkoly.this.startActivity(kattechnikum);
            }
        });
    }
}
