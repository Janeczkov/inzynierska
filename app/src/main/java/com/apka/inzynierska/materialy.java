package com.apka.inzynierska;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class materialy extends AppCompatActivity implements TaskCompleted,DownloadCompleted {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materialy);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle("Dostępne materiały");
        actionbar.setDisplayHomeAsUpEnabled(true);

        final String ip = getString(R.string.ip);

        new GetMethod(materialy.this).execute(ip + "/file/all/");
    }

    public boolean onOptionsItemSelected(MenuItem item){
        /*Intent myIntent = new Intent(getApplicationContext(), logowanie.class);
        startActivityForResult(myIntent, 0);*/
        finish();
        return true;
    }

    @Override
    public void onTaskComplete(final String result) {

        Intent intent = getIntent();
        final String ip = getString(R.string.ip);
        final String typ = intent.getStringExtra("typ");
        final String kategoria = intent.getStringExtra("kategoria");

        LinearLayout linearLayout = findViewById(R.id.linearLayout);

        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(result);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonobject = jsonArray.getJSONObject(i);
                final int fileid = jsonobject.getInt("id");
                String fileName = jsonobject.getString("fileName");
                final String typmaterialu = jsonobject.getString("typ");
                final String kategoriamaterialu = jsonobject.getString("category");
                final String akceptowany = jsonobject.getString("accepted");

                if (akceptowany.equals("true")) {
                    if (typmaterialu.equals(typ) && kategoriamaterialu.equals(kategoria)) {
                        final LinearLayout linear = (LinearLayout) getLayoutInflater().inflate(R.layout.materials, null);
                        ((TextView) linear.findViewById(R.id.idt)).setText("Plik numer " + (i + 1) + " o nazwie " + fileName);
                        //LinearLayout linear=(LinearLayout) tableRow.findViewById(R.id.linear);
                        linearLayout.addView(linear);

                        final Button downloadb = (Button) linear.findViewById(R.id.downloadb);


                        downloadb.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(materialy.this);
                                builder.setTitle("Pobieranie pliku");
                                builder.setMessage("Czy na pewno chcesz pobrac?");
                                //builder.setIcon(R.drawable.ic_launcher);
                                builder.setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();
                                        new DownloadMethod(materialy.this).execute(ip + "/file/download/" + fileid);

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
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onDownloadComplete(final String result) {
        Toast.makeText(materialy.this, result, Toast.LENGTH_LONG).show();
        /*Log.e("content here", result);
        String filename = "";
        String content = "";
        Writer output = null;

        try {

            JSONObject jsonobject = new JSONObject(result);
                filename = jsonobject.getString("filename");
                content = jsonobject.getString("content");

            File root = new File(Environment.getExternalStorageDirectory(), "Materiały");
            if (!root.exists()) {
                root.mkdir();
            }
            File file = new File(root, filename);
            boolean filedone = false;
            if (!file.exists()) {
                filedone = file.createNewFile();
            }

            FileWriter writer = new FileWriter(file);
            writer.append(content);
            writer.flush();
            writer.close();
            if (filedone) {
                Toast.makeText(materialy.this, "Poprawnie pobrano plik", Toast.LENGTH_LONG).show();
            }
            else if (!filedone) {
                Toast.makeText(materialy.this, "Plik o podanej nazwie już istnieje", Toast.LENGTH_LONG).show();
            }
            /*
            String path = Environment.getExternalStorageDirectory() + "/" + "MyFirstApp/";

            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String fullName = path + "mylog";
            File file = new File (fullName);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}