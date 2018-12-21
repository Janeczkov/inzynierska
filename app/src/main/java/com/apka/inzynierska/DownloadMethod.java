package com.apka.inzynierska;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadMethod extends AsyncTask<String , Void ,String> {
    String inputStream;
    BufferedReader reader = null;
    private Context mContext;

    ProgressDialog mProgress;
    private DownloadCompleted mCallback;

    public DownloadMethod(Context context){
        this.mContext = context;
        this.mCallback = (DownloadCompleted) context;

    }

    @Override
    protected String doInBackground(String... strings) {
        //String content="";
        String result = "";
        try {
            URL url = new URL(strings[0]);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            String header = urlConnection.getHeaderField("Content-Disposition");
            String headerSplit[] = header.split("filename=");
            String filename = headerSplit[1].replace("filename=", "").replace("\"", "").trim();


            //reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

            byte[] data = IOUtils.toByteArray(inputStream);
            //Log.e("available", String.valueOf(inputStream.available()));
            //inputStream.read(buffer);
            String contentfolder = "Materiały";

            File root = new File(Environment.getExternalStorageDirectory(), contentfolder);
            //Log.e("path", Environment.getExternalStorageDirectory().toString());

            if (!root.exists()) {
                root.mkdir();
            }
            File file = new File(root, filename);
            boolean filedone = false;
            if (!file.exists()) {
                filedone = file.createNewFile();
            }
            FileOutputStream stream = new FileOutputStream(file);

            stream.write(data);
            //int read = 0;
            /*
            while ((read = inputStream.read(buffer)) != -1) {
                stream.write(buffer, 0, read);
            }
            */

            if (filedone) {
                result = "Plik zapisano w folderze " + contentfolder;
            }
            else {
                result = "Wystąpił błąd";
            }

            stream.flush();
            stream.close();
            urlConnection.disconnect();

            /*
            FileWriter writer = new FileWriter(file);
            writer.append(content);
            writer.flush();
            writer.close();

            if (filedone) {
                Toast.makeText(materialy.this, "Poprawnie pobrano plik", Toast.LENGTH_LONG).show();
            }
            else if (!filedone) {
                Toast.makeText(materialy.this, "Plik o podanej nazwie już istnieje", Toast.LENGTH_LONG).show();


                StringBuffer buffer = new StringBuffer();
                buffer.append("{\"filename\":\"" + filename + "\",\"content\":\"");
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                buffer.append("\"}");
                content = buffer.toString();
            }
*/


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }


    protected void onPostExecute(String results) {
        //mProgress.dismiss();
        //This is where you return data back to caller
        mCallback.onDownloadComplete(results);
    }


// Converting InputStream to String

    private String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuffer response = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response.toString();
    }
}