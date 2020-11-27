// Author: d1y<chenhonzhou@gmail.com>

package io.xxzy.core;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;

public class utilsx {

  /**
   * 运营商的js写的不标准, 此函数试图去除一些不标准
   *
   * 一开口就老外包了...
   *
   * @param raw 源数据
   * @return 正确的`js-code`
   */
  public static String jsCodeRemoveCommit(String raw) {
    String cp = raw.trim();
    String left = "<!--";
    String right = "// -->";
    if (cp.startsWith(left)) {
      cp = cp.substring(left.length());
    };
    if (cp.endsWith(right)) {
      int endIndex = cp.length() - right.length();
      cp = cp.substring(0, endIndex);
    }
    return cp;
  }


  public static String floatForm (double d)
  {
    return new DecimalFormat("#.##").format(d);
  }


  /**
   * 参考: https://stackoverflow.com/a/22967188/10272586
   */
  public static String BytesToHuman (long size)
  {
    long Kb = 1024;
    long Mb = Kb * 1024;
    long Gb = Mb * 1024;
    long Tb = Gb * 1024;
    long Pb = Tb * 1024;
    long Eb = Pb * 1024;
    if (size <  Kb)   return floatForm(        size     ) + " byte";
    if (size < Mb)    return floatForm((double)size / Kb) + " Kb";
    if (size < Gb)    return floatForm((double)size / Mb) + " Mb";
    if (size < Tb)    return floatForm((double)size / Gb) + " Gb";
    if (size < Pb)    return floatForm((double)size / Tb) + " Tb";
    if (size < Eb)    return floatForm((double)size / Pb) + " Pb";
    return floatForm((double)size / Eb) + " Eb";
  }

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
