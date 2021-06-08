package com.atandroidlabs.beatcovid_fightagainstthecovid_19;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        getSupportActionBar().hide();
        ImageView backImageView = findViewById(R.id.back_imageView);
        TextView textView = findViewById(R.id.video_title);
        WebView webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        String url = getIntent().getStringExtra("url");
        String title = getIntent().getStringExtra("title");
        textView.setText(title);
        webView.loadUrl(url);

        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}