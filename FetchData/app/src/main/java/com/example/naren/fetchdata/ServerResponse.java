package com.example.naren.fetchdata;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ServerResponse extends AppCompatActivity {

    String serverresponse;
    TextView Code,Response;
    Button Mainmenu;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_response);
        intent=getIntent();
        serverresponse=intent.getStringExtra("ServerMessage");

        Code=(TextView)findViewById(R.id.textView);
        Response=(TextView)findViewById(R.id.textView2);
        Mainmenu=(Button)findViewById(R.id.button4);

        Response.setText(serverresponse);


    }
    public void onclick(View view)
    {

        intent=new Intent(ServerResponse.this,Main2Activity.class);
        startActivity(intent);

    }
}
