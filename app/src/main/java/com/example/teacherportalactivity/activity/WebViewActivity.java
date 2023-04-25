package com.example.teacherportalactivity.activity;

import static android.view.View.GONE;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import com.example.teacherportalactivity.R;

public class WebViewActivity extends AppCompatActivity {
    WebView mWebView;
    TextView weblink;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        Toolbar toolbar = findViewById(R.id.toolbar);
        AppCompatTextView toolbar_title = findViewById(R.id.toolbar_title);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        toolbar_title.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        toolbar_title.setFocusableInTouchMode(true);
        toolbar_title.setFreezesText(true);
        toolbar_title.setSingleLine(true);
        toolbar_title.setMarqueeRepeatLimit(-1);
        toolbar_title.setFocusable(true);
        toolbar_title.setSelected(true);
        toolbar_title.setText("eLive Portal");
        // Find the WebView by its unique ID
        mWebView = findViewById(R.id.web);

//        mWebView.loadData("<iframe src=\"http://www.google.com\"></iframe>", "text/html",
//                "utf-8");

//
//        final String mimeType = "text/html";
//        final String encoding = "UTF-8";
//        String html = "<h1>tanya</h1><math display='block' xmlns='http://www.w3.org/1998/Math/MathML'>\n" +
//                " <semantics>\n" +
//                "  <mrow>\n" +
//                "   <mrow><mo>(</mo>\n" +
//                "    <mrow>\n" +
//                "     <mn>15</mn><mtext>&#x2009;</mtext><mo>+</mo><mtext>&#x2009;</mtext><mn>12</mn><mtext>&#x2009;</mtext><mo>&#x00D7;</mo><mtext>&#x2009;</mtext><mfrac>\n" +
//                "      <mrow>\n" +
//                "       <mn>125</mn>\n" +
//                "      </mrow>\n" +
//                "      <mrow>\n" +
//                "       <mn>136</mn>\n" +
//                "      </mrow>\n" +
//                "     </mfrac>\n" +
//                "     <mtext>&#x2009;</mtext><mo>&#x00F7;</mo><mtext>&#x2009;</mtext><mfrac>\n" +
//                "      <mrow>\n" +
//                "       <mn>25</mn>\n" +
//                "      </mrow>\n" +
//                "      <mrow>\n" +
//                "       <mn>36</mn>\n" +
//                "      </mrow>\n" +
//                "     </mfrac>\n" +
//                "     \n" +
//                "    </mrow>\n" +
//                "   <mo>)</mo></mrow>\n" +
//                "  </mrow>\n" +
//                "  </semantics>\n" +
//                "</math>";
//
//
//        mWebView.loadDataWithBaseURL("", html, mimeType, encoding, "");


//        WebStorage webStorage = WebStorage.getInstance();
//        webStorage.deleteAllData();
//        CookieSyncManager.createInstance(this);
//        CookieManager cookieManager = CookieManager.getInstance();
//        cookieManager.removeAllCookie();

        // loading http://www.google.com url in the WebView.

//     mWebView.loadUrl("https://elive.rsgr.in/book/PeaksDemo/Animations/index.html");
     mWebView.loadUrl("https://elive.rsgr.in/");
        // this will enable the javascript.
        mWebView.getSettings().setJavaScriptEnabled(true);

        // WebViewClient allows you to handle
        // onPageFinished and override Url loading.
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.getSettings().setDomStorageEnabled(true);
       mWebView.getSettings().setSaveFormData(false);
        mWebView.getSettings().setAllowContentAccess(true);
        mWebView.getSettings().setAllowFileAccess(true);
        mWebView.getSettings().setAllowFileAccessFromFileURLs(true);
        mWebView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setDisplayZoomControls(false);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setDatabaseEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);

        mWebView.setClickable(true);

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) // Press Back Icon
        {
            mWebView.stopLoading();
            mWebView.removeAllViews();
            mWebView.destroy();
//            mWebView = null;
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        mWebView.stopLoading();
        mWebView.removeAllViews();
        mWebView.destroy();
//        mWebView = null;
        finish();
        super.onBackPressed();

    }
}

