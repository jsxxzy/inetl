// Author: d1y<chenhonzhou@gmail.com>

package io.xxzy.core;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class utilsx {

  /**
   * 转为 `utf-8`
   */
  static String toUTF8(String input) {
    return new String(input.getBytes(), StandardCharsets.UTF_8);
  }

  /**
   * 创建md5
   *
   * 参考: https://javarevisited.blogspot.com/2013/03/generate-md5-hash-in-java-string-byte-array-example-tutorial.html
   */
  static String CreateMD5(String message) {
    String digest = null;
    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      byte[] hash = md.digest(message.getBytes(StandardCharsets.UTF_8));

      //converting byte array to Hexadecimal String
      StringBuilder sb = new StringBuilder(2*hash.length);
      for(byte b : hash){
        sb.append(String.format("%02x", b&0xff));
      }

      digest = sb.toString();

    } catch (NoSuchAlgorithmException ex) {
      return "";
    }
    return digest;
  }

}
