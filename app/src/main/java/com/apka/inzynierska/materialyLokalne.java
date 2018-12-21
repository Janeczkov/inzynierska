package com.apka.inzynierska;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;

import java.io.File;

public class materialyLokalne extends AppCompatActivity {

    Button displayb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materialylokalne);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setTitle("Pobrane materiały");

        LinearLayout linearLayout = findViewById(R.id.linearLayout);

        try {
            SharedPreferences filelist = PreferenceManager
                    .getDefaultSharedPreferences(materialyLokalne.this);

            final int filelistsize = filelist.getAll().size();

            for (int j = 1; j <= filelistsize; j++) {
                JSONArray filearray = new JSONArray(filelist.getString("Plik_" + j, "[]"));
                final int fileid = filearray.getInt(0);
                final String fileName = filearray.getString(1);
                final String typmaterialu = filearray.getString(2);
                final String kategoriamaterialu = filearray.getString(3);

                final LinearLayout linear = (LinearLayout) getLayoutInflater().inflate(R.layout.materialylokalne, null);
                ((TextView) linear.findViewById(R.id.idt)).setText("Plik numer " + j + " o nazwie\r\n" + fileName);
                ((TextView) linear.findViewById(R.id.typt)).setText(typmaterialu);
                ((TextView) linear.findViewById(R.id.katt)).setText(kategoriamaterialu);
                linearLayout.addView(linear);

                displayb = linear.findViewById(R.id.displayb);

                displayb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String contentfolder = "Materiały";

                        try {
                            File root = new File(Environment.getExternalStorageDirectory(), contentfolder);
                            File file = new File(root, fileName);
                            String fileExtension = fileName.substring(fileName.lastIndexOf("."));


                            /*int cut = fileName.lastIndexOf('.');
                            if (cut != -1) {
                                fileExtension = fileName.substring(cut + 1);
                            }*/

                            Uri uri = FileProvider.getUriForFile(materialyLokalne.this, BuildConfig.APPLICATION_ID + ".provider", file);

                            Intent fileIntent = new Intent(Intent.ACTION_VIEW);

                            if (fileExtension.contains(".pdf")) {
                                // PDF file
                                fileIntent.setDataAndType(uri, "application/pdf");
                            }
                            else if (fileExtension.contains(".jpg") || fileExtension.contains(".jpeg") || fileExtension.contains(".png")) {
                                // JPG file
                                fileIntent.setDataAndType(uri, "image/jpeg");
                            }
                            else if (fileExtension.contains(".txt")) {
                                // Text file
                                fileIntent.setDataAndType(uri, "text/plain");
                            }

                            fileIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            startActivity(fileIntent);
                        } catch (ActivityNotFoundException e) {
                            e.printStackTrace();
                            //Toast.makeText(materialyLokalne.this, "Cant Find Your File", Toast.LENGTH_LONG).show();
                        }
                    }
                });
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
