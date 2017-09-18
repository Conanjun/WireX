package com.fsoc131y.wirex.receiver;

import android.annotation.SuppressLint;
import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import com.fsoc131y.wirex.ui.MainActivity;

import static android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION;

@SuppressLint({ "NewApi" }) public class AdminReceiver extends DeviceAdminReceiver {

  public CharSequence onDisableRequested(Context context, Intent intent) {
    abortBroadcast();
    return null;
  }

  public void onDisabled(Context context, Intent intent) {
    context.getSharedPreferences(context.getPackageName(), 0).edit().putBoolean("", false).apply();
    Intent intent2 = new Intent(context, MainActivity.class);
    intent2.setFlags(FLAG_GRANT_READ_URI_PERMISSION);
    try {
      context.startActivity(intent2);
    } catch (Exception e) {
    }
  }

  public void onEnabled(Context context, Intent intent) {
    super.onEnabled(context, intent);
  }
}
