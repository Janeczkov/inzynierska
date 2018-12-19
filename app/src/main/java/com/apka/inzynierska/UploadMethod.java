package com.apka.inzynierska;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class UploadMethod extends AsyncTask<TaskParamsHelper , String ,String> {


    public UploadMethod(Context context){

    }

    @Override
    protected String doInBackground(TaskParamsHelper... params) {
        try {
            URL url = new URL(params[0].ip);
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";
            int bytesRead, bytesAvailable, bufferSize;
            byte[] buffer;
            int maxBufferSize = 1024 * 1024;
            byte[] content = params[0].content;
            String urisegment = params[0].filename;

            //Log.e("path", content);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            //urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            //urlConnection.setRequestProperty("Accept","application/json");

            urlConnection.setRequestProperty("Connection", "Keep-Alive");
            urlConnection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);



            DataOutputStream outputStream;
            outputStream = new DataOutputStream(urlConnection.getOutputStream());
            outputStream.writeBytes(twoHyphens + boundary + lineEnd);
            outputStream.writeBytes("Content-Disposition: form-data; name=\"reference\""+ lineEnd);
            outputStream.writeBytes(lineEnd);
            outputStream.writeBytes("my_refrence_text");
            outputStream.writeBytes(lineEnd);
            outputStream.writeBytes(twoHyphens + boundary + lineEnd);

            outputStream.writeBytes("Content-Disposition: form-data; name=\"content\";filename=\"" + urisegment +"\"" + lineEnd);
            outputStream.writeBytes(lineEnd);


            /*File file = new File(uripath);
            FileInputStream fileInputStream = new FileInputStream(file);

            //fileInputStream = new FileInputStream(uripath);
            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];

            bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            while (bytesRead > 0) {
                outputStream.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }*/

            outputStream.write(content);
            outputStream.writeBytes(lineEnd);
            outputStream.writeBytes(twoHyphens + boundary + lineEnd);
            outputStream.writeBytes("Content-Disposition: form-data; name=\"typ\"" + lineEnd + lineEnd + params[0].typ);
            outputStream.writeBytes(lineEnd);
            outputStream.writeBytes(twoHyphens + boundary + lineEnd);
            outputStream.writeBytes("Content-Disposition: form-data; name=\"kategoria\"" + lineEnd + lineEnd + params[0].category);
            outputStream.writeBytes(lineEnd);
            outputStream.writeBytes(twoHyphens + boundary + lineEnd);
            outputStream.writeBytes("Content-Disposition: form-data; name=\"autor\"" + lineEnd + lineEnd + params[0].username);
            outputStream.writeBytes(lineEnd);

            outputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);





            // Responses from the server (code and message)
            int serverResponseCode = urlConnection.getResponseCode();
            String result = null;
            if (serverResponseCode == 200) {
                StringBuilder s_buffer = new StringBuilder();
                InputStream is = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String inputLine;
                while ((inputLine = br.readLine()) != null) {
                    s_buffer.append(inputLine);
                }
                result = s_buffer.toString();
            }
            //fileInputStream.close();
            outputStream.flush();
            outputStream.close();
            if (result != null) {
                Log.d("result_for upload", result);
                //file_name = getDataFromInputStream(result, "file_name");
            }

            Log.e("STATUS", String.valueOf(urlConnection.getResponseCode()));
            Log.e("MSG" , urlConnection.getResponseMessage());
            urlConnection.disconnect();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
