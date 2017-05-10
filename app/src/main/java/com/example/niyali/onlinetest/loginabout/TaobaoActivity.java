package com.example.niyali.onlinetest.loginabout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.niyali.onlinetest.R;

/**
 * Created by niyali on 17/4/15.
 */

public class TaobaoActivity extends AppCompatActivity {
    private WebView webView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.taobao_webview);
        webView=(WebView)findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://detail.tmall.com/item.htm?spm=a230r.1.14.1.YuGb3y&id=545669540519&ns=1&abbucket=15&sku_properties=14829532:72836383");
        ExitApplication.getInstance().addActivity(this);
    }

}
