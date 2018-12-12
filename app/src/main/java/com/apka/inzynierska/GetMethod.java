package com.apka.inzynierska;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetMethod extends AsyncTask<String , Void ,String> {
    String inputStream;
    BufferedReader reader = null;
    private Context mContext;

    ProgressDialog mProgress;
    private TaskCompleted mCallback;

    public GetMethod(Context context){
        this.mContext = context;
        this.mCallback = (TaskCompleted) context;

    }

    @Override
    protected String doInBackground(String... strings) {
        String finalJson="";
        try {
            URL url = new URL(strings[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuffer buffer = new StringBuffer();
            String line ="";
            while ((line = reader.readLine()) != null){
                buffer.append(line);
            }
            finalJson = buffer.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return finalJson;
    }


    protected void onPostExecute(String results) {
        //mProgress.dismiss();
        //This is where you return data back to caller
        mCallback.onTaskComplete(results);
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