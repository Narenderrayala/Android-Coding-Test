package com.example.naren.fetchdata;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

    }
    public void GetUsers(View view)
    {
        Intent in=new Intent(Main2Activity.this, FetchData.class);
        startActivity(in);
    }
    public void CreateNewUsers(View view)
    {
        Intent in=new Intent(Main2Activity.this, Post.class);
        startActivity(in);
    }
}
