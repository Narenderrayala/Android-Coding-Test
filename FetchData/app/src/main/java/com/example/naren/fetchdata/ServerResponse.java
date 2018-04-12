/*              Name of module: ServerResponse
                Order of execution: #5
                Purpose of Module: To display the server response
                Description: In this activity, Server response was passed from the Post activity to this current
                    ServerResponse activity. The data was fetched using intent extras and then displayed in Textviews.
                Author: Narender Rayala   */
//LIBRARIES USED
package com.example.naren.fetchdata;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
//ACTIVITY STARTS HERE
public class ServerResponse extends AppCompatActivity {
    String serverresponse;
    TextView Code,Response;
    Button Mainmenu;                    //VARIABLE DECLARATIONS HERE
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_response);          //GETTING DATA FROM PREVIOUS INTENT
        intent=getIntent();
        serverresponse=intent.getStringExtra("ServerMessage");
        Code=(TextView)findViewById(R.id.textView);
        Response=(TextView)findViewById(R.id.textView2);
        Mainmenu=(Button)findViewById(R.id.button4);
        Response.setText(serverresponse);                           //WRITING THE SERVER RESPONSE IN TO TEXTFIELD
    }
    public void onclick(View view) {
        intent=new Intent(ServerResponse.this,Main2Activity.class);    //INTENT TO MOVE TO FIRST PAGE
        startActivity(intent);
    }
}
