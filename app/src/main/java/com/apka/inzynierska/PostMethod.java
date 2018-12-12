package com.apka.inzynierska;

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

public class PostMethod extends AsyncTask<String , String ,String> {


    public PostMethod(Context context){

    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            URL url = new URL(strings[0]);
            String data = strings[1]; //data to post
            OutputStream out = null;
            String response = null;

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            //urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            urlConnection.setRequestProperty("Accept","application/json");
            urlConnection.setDoOutput(true);

            JSONObject jsonParam = new JSONObject();


            jsonParam.toString();
            jsonParam.put("username", strings[1]);
            jsonParam.put("password", strings[2]);
            jsonParam.put("rank", "1");
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

            Log.e("STATUS", String.valueOf(urlConnection.getResponseCode()));
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
}