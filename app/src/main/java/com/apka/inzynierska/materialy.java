package com.apka.inzynierska;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class materialy extends AppCompatActivity {

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
    }
}
