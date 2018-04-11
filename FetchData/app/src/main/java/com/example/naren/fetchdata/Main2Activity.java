/*              Name of module: Main2Activity
                Order of execution: #2
                Purpose of Module: To create two choices for the user to view and create new users
                Description: This is the second page of the application where I developed two options
                  to view and create new users and for each option will direct to one of the Intents
                Author: Narender Rayala
    */
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
