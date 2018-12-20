package com.apka.inzynierska;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import org.json.JSONArray;

public class materialyLokalne extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materialylokalne);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setTitle("Pobrane materiały");

        try {
            SharedPreferences prefs = PreferenceManager
                    .getDefaultSharedPreferences(materialyLokalne.this);
            JSONArray jsonArray2 = new JSONArray(prefs.getString("key", "[]"));
            for (int i = 0; i < jsonArray2.length(); i++) {
                Log.d("your JSON Array", jsonArray2.getInt(i)+"");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean onOptionsItemSelected(MenuItem item){
        /*Intent myIntent = new Intent(getApplicationContext(), logowanie.class);
        startActivityForResult(myIntent, 0);*/
        finish();
        return true;
    }
}
