/*              Name of module: Post
                Order of execution: #4
                Purpose of Module: To Post Data to the server and save the response
                Description: In this Module I designed a functionality to post the data to the server using the
                    HTTP URL connection. I connected this application to the test server and then data was converted
                    in to JSON and data request was sent. The resultant server response was saved
                Author: Narender Rayala*/

//LIBRARIES USED
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
//ACTIVITY STARTS HERE
public class Post extends AppCompatActivity {
    int responseCode=100;
    EditText Firstname;
    EditText Lastname;
    EditText avatar;
    Button submit;              //VARIABLES DECLARED TO STORE DIFFERENT TYPES OF DATA ITEMS
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
    //ASYNCTASK CLASS
    public class PostJson extends AsyncTask<String,Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            URL url;
            HttpURLConnection urlConnection;
            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();   //CREATE HTTPURL CONNECTION
                urlConnection.setReadTimeout(15000 /* milliseconds */);
                urlConnection.setConnectTimeout(15000 /* milliseconds */);
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);
                JSONObject jsonParam = new JSONObject();
                jsonParam.put("first_name", First);     //INSERTING DATA IN TO SERVER
                jsonParam.put("last_name", Last);
                jsonParam.put("avatar", avatarURL);
                Log.i("params",jsonParam.toString());
                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(jsonParam)); //THIS FUNCTION IS CALLED AS STRING IS CONVERTED TO JSON FORMAT
                writer.flush();                             //THE RESULTANT IS WRITTEN TO SERVER
                writer.close();
                os.close();
                responseCode=urlConnection.getResponseCode();       //RESPONSE CODE IS THE VALUE FROM SERVER AS RESPONSE
                if (responseCode == 201) {
                    BufferedReader in=new BufferedReader(
                            new InputStreamReader(
                                    urlConnection.getInputStream()));
                    StringBuffer sb = new StringBuffer("");
                    String line="";
                        while((line = in.readLine()) != null) {            //RESPONSE FROM THE SERVER IS SAVED IN SB STRING

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
        //FUNCTION USED TO CONVERT STRING TO JSON DATA FORMAT
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
    //FUNCTION CALLED WHEN THE SUBMIT BUTTON WAS PRESSED
    public void onclick(View view)
    {
        First=Firstname.getText().toString();
        Last=Lastname.getText().toString();
        avatarURL=avatar.getText().toString();                  //TEXT VALIDATION IS DONE HERE
        if (!isValid(First)) {
            Firstname.setError("Please enter First name");
            Firstname.requestFocus();
        }
        if (!isValid(Last)) {
            Lastname.setError("please enter Lastname");
            Lastname.requestFocus();
        }
         if(! URLUtil.isValidUrl(avatarURL))
        {
            avatar.setError("please enter valid URL");
            avatar.requestFocus();
        }
         else {

            PostJson d = new PostJson();                // THE ASYNC IS CALLED HERE, AND THEN THE WRITING DATA
             try {                                      //FETCHING SERVER RESULT  WITH THIS CALL
                Serverresult = d.execute(" https://reqres.in/api/users").get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            Intent in = new Intent(Post.this, ServerResponse.class);// CALLING OTHER ACTIVITY AND SENDING THE
             in.putExtra("ServerMessage", Serverresult);            //SERVER RESPONSE CODE TO THAT ACTIVITY
             startActivity(in);
        }
    }
    // validating Text Function
    private boolean isValid(String pass) {
        if (pass != null && pass.length() > 0) {
            return true;
        }
        return false;
    }
}

