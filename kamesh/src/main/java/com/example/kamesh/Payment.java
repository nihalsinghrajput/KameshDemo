package com.example.kamesh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class Payment extends AppCompatActivity {

    ProgressBar progressBar;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        webView = (WebView) findViewById(R.id.webView);




    }

    public void Play(Context context)
    {

        try {
            Intent myIntent = new Intent(context,Class.forName("com.example.kameshisir.MainActivity"));
            startActivity(myIntent );
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}