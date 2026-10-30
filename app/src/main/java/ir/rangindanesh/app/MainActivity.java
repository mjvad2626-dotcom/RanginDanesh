package ir.rangindanesh.app;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings;
import android.content.Intent;
import android.webkit.JavascriptInterface;

public class MainActivity extends Activity {
    private WebView web;
    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        web = new WebView(this);
        setContentView(web);
        WebSettings s = web.getSettings();
        s.setJavaScriptEnabled(true);
        s.setDomStorageEnabled(true);
        s.setAllowFileAccess(true);
        s.setDatabaseEnabled(true);
        web.setWebViewClient(new WebViewClient());
        web.setWebChromeClient(new WebChromeClient());
        web.addJavascriptInterface(new ShareBridge(), "AndroidShare");
        web.loadUrl("file:///android_asset/index.html");
    }
    private class ShareBridge {
        @JavascriptInterface public void share(String text) {
            runOnUiThread(() -> {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT, text);
                startActivity(Intent.createChooser(i, "اشتراک‌گذاری"));
            });
        }
    }
    @Override public void onBackPressed() {
        if (web != null && web.canGoBack()) web.goBack(); else super.onBackPressed();
    }
    @Override protected void onDestroy() { if (web != null) web.destroy(); super.onDestroy(); }
}
