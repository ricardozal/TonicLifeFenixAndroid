package com.bigtechsolutions.toniclifefenix.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

import com.bigtechsolutions.toniclifefenix.R;

public class DocumentsViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documents_view);

        WebView myWebView = (WebView) findViewById(R.id.WebViewPDF);

        String url = getIntent().getExtras().getString("pdf");

        myWebView.loadUrl("http://docs.google.com/gview?embedded=true&url=" + url);
    }
}