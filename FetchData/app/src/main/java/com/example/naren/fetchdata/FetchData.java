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

public class FetchData extends AppCompatActivity {

    String []Firstname=new String[12];
    String []Lastname=new String[12];
    Bitmap[] Avatar=new Bitmap[12];
    int list=0;
    ListView list1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_data);
        list1=(ListView)findViewById(R.id.list);
        DownloadJson d= new DownloadJson();
        d.execute("https://reqres.in/api/users?page=");
    }
    public class DownloadJson extends AsyncTask<String,Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String result="";
            String numofpages2="1";
            int numofpages=1;
            int page=1;
            String value;
            String newURL="";
            URL url,url2;
            HttpURLConnection urlConnection,urlConnection2;
            numofpages=Integer.parseInt(numofpages2);
            try {
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
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        page++;
                    }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
        @Override
        protected void onPostExecute(String result) {
            Log.i("Website content",result);
            CustomAdapter customAdapter=new CustomAdapter();
            list1.setAdapter(customAdapter);
        }
    }
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
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view=getLayoutInflater().inflate(R.layout.listview,null);
            ImageView avatar=(ImageView)view.findViewById(R.id.imageView);
            TextView name=(TextView)view.findViewById(R.id.textView_name);
            avatar.setImageBitmap(Avatar[i]);
            name.setText(Firstname[i]+" "+Lastname[i]);
            return view; //while data=null donot publish list
        }
    }

}
