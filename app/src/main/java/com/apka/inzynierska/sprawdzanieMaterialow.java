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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class sprawdzanieMaterialow extends AppCompatActivity implements TaskCompleted,DownloadCompleted {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sprawdzaniematerialow);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle("Materiały do przejrzenia");
        actionbar.setDisplayHomeAsUpEnabled(true);

        final String ip = getString(R.string.ip);

        new GetMethod(sprawdzanieMaterialow.this).execute(ip + "/file/all/");
    }

    public boolean onOptionsItemSelected(MenuItem item){
        /*Intent myIntent = new Intent(getApplicationContext(), logowanie.class);
        startActivityForResult(myIntent, 0);*/
        finish();
        return true;
    }

    @Override
    public void onTaskComplete(final String result) {

        final String ip = getString(R.string.ip);

        LinearLayout linearLayout = findViewById(R.id.linearLayout);

        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(result);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonobject = jsonArray.getJSONObject(i);
                final int fileid = jsonobject.getInt("id");
                final String fileName = jsonobject.getString("fileName");
                final String typmaterialu = jsonobject.getString("typ");
                final String kategoriamaterialu = jsonobject.getString("category");
                final String akceptowany = jsonobject.getString("accepted");

                if (akceptowany.equals("false")) {
                        final LinearLayout linear=(LinearLayout) getLayoutInflater().inflate(R.layout.allmaterials,null);
                        ((TextView) linear.findViewById(R.id.idt)).setText("Plik o id " + fileid + ", nazwa " + fileName);
                        ((TextView) linear.findViewById(R.id.typt)).setText(typmaterialu);
                        ((TextView) linear.findViewById(R.id.katt)).setText(kategoriamaterialu);
                        //LinearLayout linear=(LinearLayout) tableRow.findViewById(R.id.linear);
                        linearLayout.addView(linear);

                    final Button downloadb = (Button) linear.findViewById(R.id.downloadb);
                    final Button acceptb = (Button) linear.findViewById(R.id.acceptb);
                    final Button deleteb = (Button) linear.findViewById(R.id.deleteb);

                    acceptb.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(sprawdzanieMaterialow.this);
                            builder.setTitle("Akceptowanie materiału");
                            builder.setMessage("Zaakceptować materiał?");
                            builder.setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                    new UpdateMethod(sprawdzanieMaterialow.this).execute(ip + "/file/update/" + fileid, "accepted", "true");
                                    Toast.makeText(sprawdzanieMaterialow.this, "Zaakceptowano plik", Toast.LENGTH_LONG).show();

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

                    deleteb.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(sprawdzanieMaterialow.this);
                            builder.setTitle("Usunięcie materiału");
                            builder.setMessage("Czy na pewno usunąć ten materiał?");
                            builder.setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                    new DeleteMethod(sprawdzanieMaterialow.this).execute(ip + "/file/delete/" + fileid);
                                    finish();
                                    startActivity(getIntent());
                                    Toast.makeText(sprawdzanieMaterialow.this, "Usunięto plik", Toast.LENGTH_LONG).show();

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



                    downloadb.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(sprawdzanieMaterialow.this);
                            builder.setTitle("Pobieranie pliku");
                            builder.setMessage("Czy na pewno chcesz pobrac?");
                            builder.setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                    new DownloadMethod(sprawdzanieMaterialow.this).execute(ip + "/file/download/" + fileid);
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
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onDownloadComplete(final String result) {
        Toast.makeText(sprawdzanieMaterialow.this, result, Toast.LENGTH_LONG).show();
        /*
        Log.e("content here", result);
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
            boolean done = false;
            if (!file.exists()) {
                done = file.createNewFile();
            }

            FileWriter writer = new FileWriter(file);
            writer.append(content);
            writer.flush();
            writer.close();
            if (done) {
                Toast.makeText(sprawdzanieMaterialow.this, "Poprawnie pobrano plik", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(sprawdzanieMaterialow.this, "Wystąpił błąd podczas pobierania pliku", Toast.LENGTH_LONG).show();
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