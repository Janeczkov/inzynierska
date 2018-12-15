package com.apka.inzynierska;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class sprawdzanieMaterialow extends AppCompatActivity implements TaskCompleted {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sprawdzaniematerialow);
        Intent intent = getIntent();

        final String ip = getString(R.string.ip);

        final String username = intent.getStringExtra("username");
        final String rank = intent.getStringExtra("rank");

        final Intent katliceum = new Intent(sprawdzanieMaterialow.this, katliceum.class);
        final Intent dodajplik = new Intent(sprawdzanieMaterialow.this, dodajplik.class);

        final Button licb = (Button) findViewById(R.id.licb);
        final Button techb = (Button) findViewById(R.id.techb);
        final Button dodajb = (Button) findViewById(R.id.dodajb);
        final Button sprawdzajb = (Button) findViewById(R.id.sprawdzajb);
        final Button uzytkownicyb = (Button) findViewById(R.id.uzytkownicyb);

        new GetMethod(sprawdzanieMaterialow.this).execute(ip + "/files/");
    }

    @Override
    public void onTaskComplete(final String result) {

        Intent intent = getIntent();
        final String typ = intent.getStringExtra("typ");
        final String kategoria = intent.getStringExtra("kategoria");

        LinearLayout linearLayout=(LinearLayout) findViewById(R.id.linearLayout);

        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(result);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonobject = jsonArray.getJSONObject(i);
                final int id = jsonobject.getInt("id");
                final String fileName = jsonobject.getString("fileName");
                final String typmaterialu = jsonobject.getString("typ");
                final String kategoriamaterialu = jsonobject.getString("category");
                final String akceptowany = jsonobject.getString("accepted");

                if (akceptowany.equals("false")) {
                        final LinearLayout linear=(LinearLayout) getLayoutInflater().inflate(R.layout.allmaterials,null);
                        ((TextView) linear.findViewById(R.id.idt)).setText("Plik numer " + (i+1) + " o nazwie " + fileName);
                        ((TextView) linear.findViewById(R.id.typt)).setText(typmaterialu);
                        ((TextView) linear.findViewById(R.id.katt)).setText(kategoriamaterialu);
                        //LinearLayout linear=(LinearLayout) tableRow.findViewById(R.id.linear);
                        linearLayout.addView(linear);
                    }


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}