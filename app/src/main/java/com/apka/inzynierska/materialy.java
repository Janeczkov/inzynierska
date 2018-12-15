package com.apka.inzynierska;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class materialy extends AppCompatActivity implements TaskCompleted {

    /*private String typ = "liceum";
    private Button matb;
    private Button polskib;
    private Button infb;
    private Button angb;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materialy);
        Intent intent = getIntent();
        final String ip = getString(R.string.ip);




        new GetMethod(materialy.this).execute(ip + "/files/");

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

                if (akceptowany.equals("true")) {
                    if (typmaterialu.equals(typ) && kategoriamaterialu.equals(kategoria)){
                        final LinearLayout tableRow=(LinearLayout) getLayoutInflater().inflate(R.layout.materials,null);
                        ((TextView) tableRow.findViewById(R.id.idt)).setText("Plik numer " + (i+1) + " o nazwie " + fileName);
                        //LinearLayout linear=(LinearLayout) tableRow.findViewById(R.id.linear);
                        linearLayout.addView(tableRow);
                    }
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
