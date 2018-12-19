package com.apka.inzynierska;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class UpdateMethod extends AsyncTask<String , String ,String> {

    private Context mContext;

    ProgressDialog mProgress;
    private TaskCompleted mCallback;

    public UpdateMethod(Context context){
        this.mContext = context;
        this.mCallback = (TaskCompleted) context;
    }

    @Override
    protected String doInBackground(String... strings) {
        String status = null;
        try {
            URL url = new URL(strings[0]);
            String data = strings[1]; //data to post
            OutputStream out = null;
            String response = null;

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("PUT");
            urlConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            urlConnection.setRequestProperty("Accept","application/json");
            urlConnection.setDoOutput(true);

            JSONObject jsonParam = new JSONObject();


            int length = strings.length;
            //Log.e("lenthg", String.valueOf(length));

            for (int i = 1; i < strings.length; i = i+2) {
                jsonParam.put(strings[i], strings[i+1]);
                if (strings[i+1].equals("true")) {
                    jsonParam.put(strings[i], true);
                }
                jsonParam.put("accepted", true);
            }
            Log.e("wazneeee", String.valueOf(jsonParam));

            /*out = new BufferedOutputStream(urlConnection.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
            Log.e("benc", data);
            writer.write(data);
            writer.flush();
            writer.close();
            out.close();*/

            DataOutputStream os = new DataOutputStream(urlConnection.getOutputStream());
            //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
            os.writeBytes(jsonParam.toString());

            os.flush();
            os.close();

            status = String.valueOf(urlConnection.getResponseCode());
            Log.e("STATUS", status);
            Log.e("MSG" , urlConnection.getResponseMessage());
            urlConnection.disconnect();

/*
            String line = "";
            InputStreamReader isr = new InputStreamReader(urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null)
            {
                sb.append(line + "\n");
            }
            // Response from server after login process will be stored in response variable.
            response = sb.toString();
            // You can perform UI operations here
            Log.e("wazne", response);
            isr.close();
            reader.close();*/

            urlConnection.connect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    /*protected void onPostExecute(String results) {
        //mProgress.dismiss();
        //This is where you return data back to caller
        mCallback.onTaskComplete(results);
    }*/
}