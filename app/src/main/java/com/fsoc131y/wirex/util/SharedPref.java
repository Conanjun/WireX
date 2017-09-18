package com.fsoc131y.wirex.util;

import com.fsoc131y.wirex.BuildConfig;
import java.security.GeneralSecurityException;

public class SharedPref {
  public static String AES(String str, String str2) {
    try {
      return AESCrypt.decrypt(str, str2);
    } catch (GeneralSecurityException e) {
      return BuildConfig.FLAVOR;
    }
  }
}
