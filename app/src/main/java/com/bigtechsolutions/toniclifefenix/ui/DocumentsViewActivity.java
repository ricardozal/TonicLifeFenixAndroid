package com.bigtechsolutions.toniclifefenix.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bigtechsolutions.toniclifefenix.R;
import com.bigtechsolutions.toniclifefenix.commons.MyFenixApp;
import com.bigtechsolutions.toniclifefenix.ui.profile.MyOrdersActivity;

public class DocumentsViewActivity extends AppCompatActivity {

    Toolbar toolbar;
    ProgressDialog loading;
    WebView myWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documents_view);

        Bundle bundle = getIntent().getExtras();
        String url = bundle.getString("pdf");
        String name = bundle.getString("name");

        toolbar = findViewById(R.id.toolbarPDF);
        loading = ProgressDialog.show(this, "Cargando", "Por favor espere...", false, false);
        myWebView = findViewById(R.id.WebViewPDF);

        toolbarConfig(name);

        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                loading.dismiss();
            }

        });

        myWebView.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url="+url);
    }

    private void toolbarConfig(String name) {

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setTitle(name);
    }
}