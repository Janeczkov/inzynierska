package com.apka.inzynierska;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class wybormaterialu extends AppCompatActivity {

    private Button tekstb;
    private Button obrazb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wybormaterialu);
        Intent intent=getIntent();

        final Intent dodajobraz = new Intent(wybormaterialu.this,dodajplik.class);

        tekstb = (Button) findViewById(R.id.tekstb);
        obrazb = (Button) findViewById(R.id.techb);

        /*tekstb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wybormaterialu.this.startActivity(dodajtekst);
            }
        });*/

        obrazb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wybormaterialu.this.startActivity(dodajobraz);
            }
        });
    }
}
