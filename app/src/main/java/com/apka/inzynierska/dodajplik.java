package com.apka.inzynierska;

import android.app.Activity;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import static android.app.PendingIntent.getActivity;

public class dodajplik extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    private Button przegladajb;
    private Button wyslijplikb;
    private TextView wybrany;
    private static final int READ_REQUEST_CODE = 42;
    Uri uri = null;
    Spinner typspinner, katspinner;
    String displayName;
    String size = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodajplik);
        Intent intent=getIntent();
        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle("Dodawanie pliku");
        actionbar.setDisplayHomeAsUpEnabled(true);

        final String username=intent.getStringExtra("username");

        przegladajb = findViewById(R.id.przegladajb);
        wyslijplikb = findViewById(R.id.wyslijplikb);
        final String ip = getString(R.string.ip);

        typspinner = findViewById(R.id.typspinner);
        katspinner = findViewById(R.id.katspinner);

        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(this,
                R.array.typ_array, android.R.layout.simple_spinner_dropdown_item);
        typspinner.setAdapter(adapter1);
        typspinner.setOnItemSelectedListener(this);

        final Button cancelb = findViewById(R.id.cancelb);



        przegladajb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(dodajplik.this,
                            android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(dodajplik.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                    } else {
                        performFileSearch();
                    }
                } else {
                    performFileSearch();
                }

            }
        });

        wyslijplikb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (getFileName(uri).length()>40) {
                    Toast.makeText(getApplicationContext(), "Nazwa pliku jest za długa", Toast.LENGTH_LONG).show();
                }
                else {
                    if (uri != null) {
                        try {
                            TaskParamsHelper params = new TaskParamsHelper(ip + "/file/upload/", readTextFromUri(uri),
                                    getFileName(uri),
                                    typspinner.getSelectedItem().toString(),
                                    katspinner.getSelectedItem().toString(),
                                    username);


                            new UploadMethod(dodajplik.this).execute(params);
                            Toast.makeText(getApplicationContext(), "Poprawnie wysłano plik", Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
/*
                    File root = new File(Environment.getExternalStorageDirectory(), "Materiały");
                    if (!root.exists()) {
                        root.mkdir();
                    }
                    File file = new File(root, getFileName(uri));
                    boolean filedone = false;
                    if (!file.exists()) {
                        try {
                            filedone = file.createNewFile();

                            //OutputStreamWriter oswriter = new OutputStreamWriter(getApplicationContext().openFileOutput(getFileName(uri), Context.MODE_PRIVATE));
                            oswriter.write(s);
                            //FileOutputStream stream = new FileOutputStream(file);
                            //FileWriter writer = new FileWriter(file);
                            //stream.write(readTextFromUri(uri));
                            //writer.append(s);
                            //writer.flush();
                            //writer.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }*/

                    } else {
                        Toast.makeText(getApplicationContext(), "Wybierz plik", Toast.LENGTH_LONG).show();
                    }
                }



            }
        });

        cancelb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    public boolean onOptionsItemSelected(MenuItem item){
        /*Intent myIntent = new Intent(getApplicationContext(), logowanie.class);
        startActivityForResult(myIntent, 0);*/
        finish();
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        if (typspinner.getSelectedItem().equals("Liceum")) {
            ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this,
                    R.array.liceum_array, android.R.layout.simple_spinner_dropdown_item);
            katspinner.setAdapter(adapter2);

        } else {
            ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this,
                    R.array.technikum_array, android.R.layout.simple_spinner_dropdown_item);
            katspinner.setAdapter(adapter2);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
    }

    public void performFileSearch() {

        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
        // browser.
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

        // Filter to only show results that can be "opened", such as a
        // file (as opposed to a list of contacts or timezones)
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // Filter to show only images, using the image MIME data type.
        // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
        // To search for all documents available via installed storage providers,
        // it would be "*/*".
        intent.setType("*/*");

        startActivityForResult(intent, READ_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            performFileSearch();
        }
    }
    /*@Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (resultData != null) {
                Uri selectedImageUri = resultData.getData();
                String[] projection = {MediaStore.Images.ImageColumns.DATA};
                CursorLoader loader = new CursorLoader(this, selectedImageUri, projection, null, null, null);
                Cursor cursor = loader.loadInBackground();
                if (cursor.moveToFirst()) {
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATA);
                    imageUri = Uri.parse(cursor.getString(column_index));
                    if(imageUri!=null){
                        Log.e("he", "he");
                    }
                }
                cursor.close();
            }
        }
    }*/

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {

        wybrany = (TextView) findViewById(R.id.wybranyt);

        // The ACTION_OPEN_DOCUMENT intent was sent with the request code
        // READ_REQUEST_CODE. If the request code seen here doesn't match, it's the
        // response to some other intent, and the code below shouldn't run at all.

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData().

            if (resultData != null) {
                uri = resultData.getData();
                    Log.i("uritostring", "Uri: " + uri.toString());
                    Log.i("uripath", "Uri: " + uri.getPath());
                    String[] projection = {MediaStore.Images.ImageColumns.DATA};
                    Cursor cursor = dodajplik.this.getContentResolver()
                            .query(uri, projection, null, null, null, null);
                    try {
                        // moveToFirst() returns false if the cursor has 0 rows.  Very handy for
                        // "if there's anything to look at, look at it" conditionals.
                        if (cursor != null && cursor.moveToFirst()) {
                            int indeksik = cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA);
                            displayName = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA));


                            int sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE) + 1;

                            // If the size is unknown, the value stored is null.  But since an
                            // int can't be null in Java, the behavior is implementation-specific,
                            // which is just a fancy term for "unpredictable".  So as
                            // a rule, check if it's null before assigning to an int.  This will
                            // happen often:  The storage API allows for remote files, whose
                            // size might not be locally known.


                            if (!cursor.isNull(sizeIndex)) {
                                // Technically the column stores an int, but cursor.getString()
                                // will do the conversion automatically.
                                size = cursor.getString(sizeIndex);
                            } else {
                                size = "Unknown";
                            }
                            size = cursor.getString(sizeIndex);
                            Log.i("size", "Size: " + size);

                            wybrany.setText(getFileName(uri));


                            //imageUri = Uri.parse(displayName);
                        }
                    } finally {
                        cursor.close();/*
                Uri selectedImageUri = resultData.getData();
                String[] projection = {MediaStore.Images.ImageColumns.DATA};
                CursorLoader loader = new CursorLoader(this, selectedImageUri, projection, null, null, null);
                Cursor cursor = loader.loadInBackground();
                if (cursor.moveToFirst()) {
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA);
                    imageUri = Uri.parse(cursor.getString(column_index));
                    if(imageUri!=null){
                        Log.e("blad", "jakis");
                    }
                }
                cursor.close();*/

                        //dumpImageMetaData(uri);
                        //showImage(uri);
                    }

            }
        }
    }

    private byte[] readTextFromUri(Uri uri) throws IOException {
        InputStream inputStream = getContentResolver().openInputStream(uri);

        byte[] data = IOUtils.toByteArray(inputStream);

        //ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        /*byte[] data = new byte[4096];

        int len = 0;
        while ((len = inputStream.read(data)) != -1) {
            buffer.write(data, 0, len);
        }*/
        //String s = new String(data, "UTF-8");


       /* BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder total = new StringBuilder();
        for (String line; (line = r.readLine()) != null; ) {
            total.append(line).append('\n');
        }
        String s = total.toString();
*/
        /*
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] data = new byte[1024];

        int len = 0;
        while ((len = inputStream.read(data)) != -1) {
            buffer.write(data, 0, len);
        }

*/
        //String s = new String(data, "UTF-8");

        //Log.e("strinczek", data.toString());
        return data;

        /*
        bis = new BufferedInputStream(new FileInputStream(getFileName(uri)));
        bis.read(buf);



        Log.e("strinczek", s);
        return s;*/
    }

/*
        InputStream inputStream = getContentResolver().openInputStream(uri);
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        inputStream.close();
        reader.close();
        return stringBuilder.toString();
    }
    /*private String licz (Uri uri) throws IOException {
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1024 * 1024;

        File file = new File(uri.getPath());
        FileInputStream fileInputStream = new FileInputStream(file);
        bytesAvailable = fileInputStream.available();
        bufferSize = Math.min(bytesAvailable, maxBufferSize);
        buffer = new byte[bufferSize];

        InputStream inputStream = getContentResolver().openInputStream(uri);
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line;

        bytesRead = fileInputStream.read(buffer, 0, bufferSize);

        String s = new String(buffer);

        while (bytesRead > 0) {

            stringBuilder.append(buffer, 0, bufferSize);
            //outputStream.write(buffer, 0, bufferSize);
            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
        }

        Log.e("bufor", s);
        return s;

    }*/
    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }
}