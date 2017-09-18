package com.fsoc131y.wirex.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.fsoc131y.wirex.service.StartService;

public class StartServiceReceiver extends BroadcastReceiver {
  public void onReceive(Context context, Intent intent) {
    context.startService(new Intent(context, StartService.class));
  }
}
