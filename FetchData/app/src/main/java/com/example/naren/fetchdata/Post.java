package com.example.naren.fetchdata;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

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
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Post extends AppCompatActivity {
    int responseCode=100;
    EditText Firstname;
    EditText Lastname;
    EditText avatar;
    Button submit;
    String First;
    String Last;
    String Serverresult="no res";
    String avatarURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        Firstname=(EditText)findViewById(R.id.editText);
        Lastname=(EditText)findViewById(R.id.editText2);
        avatar=(EditText)findViewById(R.id.editText3);
        submit=(Button)findViewById(R.id.button3);
    }
    public class PostJson extends AsyncTask<String,Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            URL url;
            HttpURLConnection urlConnection;
            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(15000 /* milliseconds */);
                urlConnection.setConnectTimeout(15000 /* milliseconds */);
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);
                JSONObject jsonParam = new JSONObject();
                jsonParam.put("first_name", First);
                jsonParam.put("last_name", Last);
                jsonParam.put("avatar", avatarURL);
                Log.i("params",jsonParam.toString());

                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(jsonParam));

                writer.flush();
                writer.close();
                os.close();

                responseCode=urlConnection.getResponseCode();

                if (responseCode == 201) {

                    BufferedReader in=new BufferedReader(
                            new InputStreamReader(
                                    urlConnection.getInputStream()));
                    StringBuffer sb = new StringBuffer("");
                    String line="";

                    while((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                }
                else {
                    return new String("false : "+responseCode);
                }



            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }
        public String getPostDataString(JSONObject params) throws Exception {

            StringBuilder result = new StringBuilder();
            boolean first = true;

            Iterator<String> itr = params.keys();

            while(itr.hasNext()){

                String key= itr.next();
                Object value = params.get(key);

                if (first)
                    first = false;
                else
                    result.append("&");

                result.append(URLEncoder.encode(key, "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(value.toString(), "UTF-8"));

            }
            return result.toString();
        }

           }
    public void onclick(View view)
    {
        First=Firstname.getText().toString();
        Last=Lastname.getText().toString();
        avatarURL=avatar.getText().toString();
        if (!isValidPassword(First)) {

            Firstname.setError("Please enter First name");
            Firstname.requestFocus();
        }


        if (!isValidPassword(Last)) {
            Lastname.setError("please enter Lastname");
            Lastname.requestFocus();
        }
         if(! URLUtil.isValidUrl(avatarURL))
        {
            avatar.setError("please enter valid URL");
            avatar.requestFocus();
        }

        else {

            PostJson d = new PostJson();
            try {
                Serverresult = d.execute(" https://reqres.in/api/users").get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            Intent in = new Intent(Post.this, ServerResponse.class);


            in.putExtra("ServerMessage", Serverresult);
            startActivity(in);
        }
    }


    // validating password with retype password
    private boolean isValidPassword(String pass) {
        if (pass != null && pass.length() > 0) {
            return true;
        }
        return false;
    }


}

