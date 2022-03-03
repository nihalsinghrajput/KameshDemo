package com.example.kamesh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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

        final RelativeLayout layout = new RelativeLayout(context);

        final RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                getWindowManager().getDefaultDisplay().getWidth(),
                getWindowManager().getDefaultDisplay().getHeight());

        layout.setLayoutParams(params);

        setContentView(layout);


        final WebView web = new WebView(this);

        web.getSettings().setJavaScriptEnabled(true);


        web.loadUrl("http://www.google.com");

        RelativeLayout.LayoutParams webViewParams = new RelativeLayout.LayoutParams(
                300, 100);
        webViewParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

    }
}