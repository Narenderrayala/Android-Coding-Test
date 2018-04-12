              /*Name of module: MainActivity
                Order of execution: #1
                Purpose of Module: To create a splash screen
                Description: This is the first page of the application where I created a Splash screen by using HANDLERS
                Author: Narender Rayala
                */
    //Libraries Used
package com.example.naren.fetchdata;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
              //Main class
public class MainActivity extends AppCompatActivity {
    private static int splash=500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setBackgroundDrawableResource(R.drawable.genius);
              //Handler to showcase splash design
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent in=new Intent(MainActivity.this, Main2Activity.class);
                startActivity(in);
                finish();
            }
        },splash);
    }
}
