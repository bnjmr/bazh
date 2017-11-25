package ir.jahanmirbazh.bazh;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import ir.jahanmirbazh.R;

public class ActivityBrowser extends AppCompatActivity {

    String Url;
    WebView web;
    LinearLayout layImgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        layImgBack = (LinearLayout) findViewById(R.id.layImgBack);
        layImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent = getIntent();
        if (intent != null) {
            Url = intent.getStringExtra("Url");
        }

        web = (WebView) findViewById(R.id.web);

        web.clearCache(true);
        web.clearHistory();
        web.getSettings().setSaveFormData(true);

        web.getSettings().setJavaScriptEnabled(true);
        web.setWebViewClient(new MyWebViewClient());
        web.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        web.loadUrl(Url);
    }


    private class MyWebViewClient extends WebViewClient
    {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            //dlg.showDialogLoadingEnableCancelable(G.currentActivity);
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            //dlg.closeDialogLoading();
            super.onPageFinished(view, url);
        }
        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
        }
    }
}
