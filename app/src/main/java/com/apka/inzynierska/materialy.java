package com.apka.inzynierska;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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

        String typ = intent.getStringExtra("typ");
        String kategoria = intent.getStringExtra("kategoria");
        Log.e("hejo", typ);
        Log.e("sas", kategoria);

        final List<LinearLayout> lista = new ArrayList<LinearLayout>();
        TableLayout tableLayout=(TableLayout) findViewById(R.id.tableLayout);
        int benc = 3;
        /*for(int i=0;i<benc;i++){
            final TableRow tableRow=(TableRow) LayoutInflater.from(dwojeczka.this).inflate(R.layout.radiob,null);
            ((TextView) tableRow.findViewById(R.id.id)).setText("id "+(i+1));
            LinearLayout layoucik=(RadioGroup)tableRow.findViewById(R.id.GROUP);
            radioGroupList.add(radioGroup);

            tableLayout.addView(tableRow);
        }*/

        new GetMethod(materialy.this).execute(ip + "/files/");

    }

    @Override
    public void onTaskComplete(final String result) {


        //LinearLayout linear=(LinearLayout) findViewById(R.id.linear);
        TableLayout tableLayout=(TableLayout) findViewById(R.id.tableLayout);

        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(result);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonobject = jsonArray.getJSONObject(i);
                final int id = jsonobject.getInt("id");
                final String fileName = jsonobject.getString("fileName");

                final TableRow tableRow=(TableRow) getLayoutInflater().inflate(R.layout.tables,null);
                ((TextView) tableRow.findViewById(R.id.idt)).setText("Plik numer " + (i+1) + "o nazwie " + fileName);
                //LinearLayout linear=(LinearLayout) tableRow.findViewById(R.id.linear);
                tableLayout.addView(tableRow);

                /*View view = LayoutInflater.from(materialy.this).inflate(R.layout.tables, null);
                LinearLayout linear = (LinearLayout) findViewById(R.id.linear);
                linear.addView(view);*/
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
