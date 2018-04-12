/*            Name of module: FetchData
              Order of execution: #3
              Purpose of Module: To create a functionality to obtain list of users from a test server
              Description: In this module, I Developed a custom listview containing Imageviews and Textboxes.
                  Here AsyncTask is used to create a thread where all the network related functions are performed.
                  JSON data is obtained, parsed in to String and displayed in a custom list view.
              Author: Narender Rayala  */

            //Libraries Used
package com.example.naren.fetchdata;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
            //Activity Starts Here
public class FetchData extends AppCompatActivity {
    String []Firstname=new String[12];
    String []Lastname=new String[12];  //Variables to store the User details from the server
    Bitmap[] Avatar=new Bitmap[12];
    int list=0;
    ListView list1;                     //Variable for listview
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_data);
        list1=(ListView)findViewById(R.id.list);
        DownloadJson d= new DownloadJson(); //Calling AsyncTask to create a new thread to access data from server
        d.execute("https://reqres.in/api/users?page=");//url sent to the DownloadJson class
    }
    public class DownloadJson extends AsyncTask<String,Void, String> { //class created to perform Network operations
        @Override
        protected String doInBackground(String... urls) {
            String result="";
            String numofpages2="1";
            int numofpages=1;
            int page=1;
            String newURL="";
            URL url,url2;
            HttpURLConnection urlConnection,urlConnection2; //creating a HTTP URL connection
            numofpages=Integer.parseInt(numofpages2);
            try {
                  /* num of pages is the variable for the total number of pages. the loop is iterated until it
                     reaches its maximum number. First numofpage is set 1, then the page 1 was iterated.
                     JSON data is parsed.then maximum page number is obtained. numofpage is updated to maximum page.
                     from each page first name last name avatar is obtained. then page is incremented, then page=2
                     which may be less than max page. Same way data is obtained from all pages */

                    while (page<=numofpages) {
                        newURL=urls[0]+page;
                        url = new URL(newURL);
                        urlConnection = (HttpURLConnection) url.openConnection();
                        InputStream in = urlConnection.getInputStream();
                        InputStreamReader reader = new InputStreamReader(in);
                        int data = reader.read();
                            while (data != -1) {
                                char current;
                                current = (char) data;
                                result += current;
                                data = reader.read();
                            }
                        String reqdata="";
                        try {
                            JSONObject jsonobject= new JSONObject(result);
                            reqdata=jsonobject.getString("data");
                            numofpages2=jsonobject.getString("total_pages");
                            numofpages=Integer.parseInt(numofpages2);
                            result="";
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            JSONArray arr=new JSONArray(reqdata);
                                for(int i=0;i<arr.length();i++)
                                {
                                    JSONObject jsonpart=arr.getJSONObject(i);
                                    Log.i("avatar",jsonpart.getString("avatar"));
                                    Firstname[list]=jsonpart.getString("first_name");
                                    Lastname[list]=jsonpart.getString("last_name");
                                    Log.i("Firstname",Firstname[list]);
                                    Log.i("Lastname",Lastname[list]);
                                    url2 = new URL(jsonpart.getString("avatar"));
                                    urlConnection2 = (HttpURLConnection) url2.openConnection();
                                    InputStream in2 = urlConnection2.getInputStream();
                                    Avatar[list]= BitmapFactory.decodeStream(in2);
                                    list++;
                                }
                            }
                        catch (JSONException e) {
                            e.printStackTrace();
                            }
                        page++;
                    }
            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
        // It is the function that executes once the work with the AsyncTask is done and result is passed to this method
        @Override
        protected void onPostExecute(String result) {
            Log.i("Website content",result);
            CustomAdapter customAdapter=new CustomAdapter();
            list1.setAdapter(customAdapter);
        }
    }
    //THIS CLASS IS DEFINED TO CREATE A CUSTOM LISTVIEW FROM ANOTHER LAYOUT
    class CustomAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return Avatar.length;
        }
        @Override
        public Object getItem(int i) {
            return null;
        }
        @Override
        public long getItemId(int i) {
            return 0;
        }
        //THIS IS THE METHOD CALLED TO CREATE A CUSTOM Listview LAYOUT WITH IMAGE VIEW AND TEXT VIEW
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view=getLayoutInflater().inflate(R.layout.listview,null);//LAYOUT OF IMAGE AND TEXT IS CALLED HERE
            ImageView avatar=(ImageView)view.findViewById(R.id.imageView);
            TextView name=(TextView)view.findViewById(R.id.textView_name);
            avatar.setImageBitmap(Avatar[i]);               //EACH VALUES STORED IN ARRAY IS SET ONE BY ONE IN LIST VIEW
            name.setText(Firstname[i]+" "+Lastname[i]);
            return view; //while data=null,  view would be empty
        }
    }
}
