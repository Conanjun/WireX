package com.fsoc131y.wirex.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class DisplayAdReceiver extends BroadcastReceiver {
  public void onReceive(Context context, Intent intent) {
    if ("android.intent.action.SCREEN_ON".equals(intent.getAction())) {
      // Display an ad with StartAppSDK
    }
  }
}
