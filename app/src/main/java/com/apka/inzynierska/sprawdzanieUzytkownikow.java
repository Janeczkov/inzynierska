package com.apka.inzynierska;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class sprawdzanieUzytkownikow extends AppCompatActivity implements TaskCompleted {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listauzytkownikow);
        Intent intent = getIntent();
        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle("Lista użytkowników");
        actionbar.setDisplayHomeAsUpEnabled(true);
        final String ip = getString(R.string.ip);


        final String username = intent.getStringExtra("username");
        final String rank = intent.getStringExtra("rank");

        //final Intent katliceum = new Intent(sprawdzanieMaterialow.this, katliceum.class);
        //final Intent dodajplik = new Intent(sprawdzanieMaterialow.this, dodajplik.class);

        final Button licb = (Button) findViewById(R.id.licb);
        final Button techb = (Button) findViewById(R.id.techb);
        final Button dodajb = (Button) findViewById(R.id.dodajb);
        final Button sprawdzajb = (Button) findViewById(R.id.sprawdzajb);
        final Button uzytkownicyb = (Button) findViewById(R.id.uzytkownicyb);

        new GetMethod(sprawdzanieUzytkownikow.this).execute(ip + "/accounts/");
    }

    public boolean onOptionsItemSelected(MenuItem item){
        /*Intent myIntent = new Intent(getApplicationContext(), logowanie.class);
        startActivityForResult(myIntent, 0);*/
        finish();
        return true;
    }

    void rysuj(String result){
        Intent intent = getIntent();
        //final String typ = intent.getStringExtra("typ");
        //final String kategoria = intent.getStringExtra("kategoria");
        final String ip = getString(R.string.ip);

        LinearLayout linearLayout=(LinearLayout) findViewById(R.id.linearLayout);

        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(result);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonobject = jsonArray.getJSONObject(i);
                final int userid = jsonobject.getInt("id");
                final String username = jsonobject.getString("username");
                final String rank = jsonobject.getString("rank");
                final String elements = jsonobject.getString("elements");
                final String points = jsonobject.getString("points");

                final LinearLayout linear=(LinearLayout) getLayoutInflater().inflate(R.layout.users,null);
                ((TextView) linear.findViewById(R.id.usernamet)).setText("Użytkownik:\r\n" + username);
                ((TextView) linear.findViewById(R.id.rangt)).setText("Ranga: " + rank);
                ((TextView) linear.findViewById(R.id.elementst)).setText("Elementów: " + elements);
                ((TextView) linear.findViewById(R.id.pointst)).setText("Punktów: " + points);
                //LinearLayout linear=(LinearLayout) tableRow.findViewById(R.id.linear);

                linearLayout.addView(linear);

                final Button uprankb = (Button) linear.findViewById(R.id.uprankb);
                final Button downrankb = (Button) linear.findViewById(R.id.downrankb);
                final Button deleteb = (Button) linear.findViewById(R.id.deleteb);

                uprankb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new UpdateMethod(sprawdzanieUzytkownikow.this).execute(ip + "/accounts/" + userid, "rank", "2", "points", "0", "elements", "0");
                        finish();
                        startActivity(getIntent());

                    }
                });

                downrankb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new UpdateMethod(sprawdzanieUzytkownikow.this).execute(ip + "/accounts/" + userid,"rank", "1", "points", "0", "elements", "0");
                        finish();
                        startActivity(getIntent());
                    }
                });


                deleteb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(sprawdzanieUzytkownikow.this);
                        builder.setTitle("Usunięcie użytkownika");
                        builder.setMessage("Czy na pewno chcesz go usunąć?");
                        //builder.setIcon(R.drawable.ic_launcher);
                        builder.setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                                new DeleteMethod(sprawdzanieUzytkownikow.this).execute(ip + "/accounts/" + userid);
                                finish();
                                startActivity(getIntent());
                                //wykonuje delete usera

                            }
                        });
                        builder.setNegativeButton("Nie", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                });



            }
        } catch (JSONException e) {
            e.printStackTrace();
        }




    }


    @Override
    public void onTaskComplete(final String result) {
        Log.e("rezulcik", result);
        rysuj(result);
    }
}