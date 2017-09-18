package com.fsoc131y.wirex.util;

import android.support.v4.view.MotionEventCompat;
import android.util.Base64;
import android.util.Log;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public final class AESCrypt {
  private static final byte[] ENTRY = new byte[] {
      (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0,
      (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0
  };
  public static boolean DEBUG_LOG_ENABLED = false;

  private AESCrypt() {
  }

  private static String a(byte[] bArr) {
    int i = 0;
    char[] cArr = new char[] {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
    };
    char[] cArr2 = new char[(bArr.length * 2)];
    while (i < bArr.length) {
      int i2 = bArr[i] & MotionEventCompat.ACTION_MASK;
      cArr2[i * 2] = cArr[i2 >>> 4];
      cArr2[(i * 2) + 1] = cArr[i2 & 15];
      i++;
    }
    return new String(cArr2);
  }

  private static SecretKeySpec a(String str)
      throws NoSuchAlgorithmException, UnsupportedEncodingException {
    MessageDigest instance = MessageDigest.getInstance("SHA-256");
    byte[] bytes = str.getBytes("UTF-8");
    instance.update(bytes, 0, bytes.length);
    byte[] digest = instance.digest();
    a("SHA-256 key ", digest);
    return new SecretKeySpec(digest, "AES");
  }

  private static void a(String str, String str2) {
    if (DEBUG_LOG_ENABLED) {
      Log.d("AESCrypt", str + "[" + str2.length() + "] [" + str2 + "]");
    }
  }

  private static void a(String str, byte[] bArr) {
    if (DEBUG_LOG_ENABLED) {
      Log.d("AESCrypt", str + "[" + bArr.length + "] [" + a(bArr) + "]");
    }
  }

  public static String decrypt(String str, String str2) throws GeneralSecurityException {
    try {
      SecretKeySpec a = a(str);
      a("base64EncodedCipherText", str2);
      byte[] decode = Base64.decode(str2, 2);
      a("decodedCipherText", decode);
      byte[] decrypt = decrypt(a, ENTRY, decode);
      a("decryptedBytes", decrypt);
      String str3 = new String(decrypt, "UTF-8");
      a("message", str3);
      return str3;
    } catch (Throwable e) {
      if (DEBUG_LOG_ENABLED) {
        Log.e("AESCrypt", "UnsupportedEncodingException ", e);
      }
      throw new GeneralSecurityException(e);
    }
  }

  public static byte[] decrypt(SecretKeySpec secretKeySpec, byte[] bArr, byte[] bArr2)
      throws GeneralSecurityException {
    Cipher instance = Cipher.getInstance("AES/CBC/PKCS7Padding");
    instance.init(2, secretKeySpec, new IvParameterSpec(bArr));
    byte[] doFinal = instance.doFinal(bArr2);
    a("decryptedBytes", doFinal);
    return doFinal;
  }
}
