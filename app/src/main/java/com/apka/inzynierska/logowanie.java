package com.apka.inzynierska;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class logowanie extends AppCompatActivity implements TaskCompleted {

    private String baseUrl;
    private String username;
    private String password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        Intent intent=getIntent();
        final String ip = getString(R.string.ip);




        final Intent main=new Intent(logowanie.this,MainActivity.class);
        final Intent zalogowany=new Intent(logowanie.this,zalogowany.class);

        final Button loginb = (Button) findViewById(R.id.loginb);
        final Button cancelb=(Button) findViewById(R.id.logcancelb);

        loginb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new GetMethod(logowanie.this).execute(ip + "/accounts/");


            }

                            /*jsonReader.beginObject(); // Start processing the JSON object
                            while (jsonReader.hasNext()) { // Loop through all keys
                                String key = jsonReader.nextName(); // Fetch the next key
                                if (key.equals("organization_url")) { // Check if desired key
                                    // Fetch the value as a String
                                    String value = jsonReader.nextString();

                                    Toast.makeText(getApplicationContext(), value, Toast.LENGTH_LONG).show();

                                    break; // Break out of the loop
                                } else {
                                    jsonReader.skipValue(); // Skip values of other keys
                                }
                            }*/

                            /*int c = 0;
                            StringBuffer buffer = new StringBuffer();
                            while ((c = inputStream.read()) != -1)
                            {
                                buffer.append((char) c);
                            }
                            final String result = buffer.toString();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                                }
                            });

                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

*/




        });

        cancelb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //logowanie.this.startActivity(main);
                finish();
            }
        });


    }
    @Override
    public void onTaskComplete(final String result) {

        final EditText usernamelogin = (EditText) findViewById(R.id.usernamelogin);
        final EditText passwordlogin = (EditText) findViewById(R.id.passwordlogin);
        final Intent zalogowany=new Intent(logowanie.this,zalogowany.class);

        final String usernamecontent = usernamelogin.getText().toString();
        final String passwordcontent = passwordlogin.getText().toString();

        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(result);
            Boolean logged = false;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonobject = jsonArray.getJSONObject(i);
                final String username = jsonobject.getString("username");
                final String password = jsonobject.getString("password");
                final String rank = jsonobject.getString("rank");


                if (usernamecontent.equals(username)) {
                    if (passwordcontent.equals(password)) {
                        logged = true;
                        Toast.makeText(getApplicationContext(), "Zostałeś zalogowany!", Toast.LENGTH_LONG).show();
                        zalogowany.putExtra("username", username);
                        zalogowany.putExtra("rank", rank);

                        logowanie.this.startActivity(zalogowany);
                        break;
                    }
                }
            }
            if (logged == false) {
                Toast.makeText(getApplicationContext(), "Podałeś niepoprawne dane, spróbuj jeszcze raz.", Toast.LENGTH_LONG).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

}
