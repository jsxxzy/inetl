// Author: d1y<chenhonzhou@gmail.com>

package io.xxzy.core;

import com.github.kevinsawicki.http.HttpRequest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class iNet {

  /**
   * 生成密码
   * https://github.com/jsxxzy/ipsd
   * @return 密码
   */
  public String CreatePassword(String p) {
    String offset1 = constvar.Pid + p + constvar.Calg;
    String md5 = utilsx.CreateMD5(offset1);
    return md5 + constvar.Calg + constvar.Pid;
  }

  
  /**
   * 登录
   */
  public boolean Login(String u, String p) {
    return false;
  }

  /**
   * 注销
   */
  public boolean Logout() {
    return false;
  }

  /**
   * 查询信息
   */
  public boolean QueryInfo() {
    HttpRequest req = HttpRequest.get(constvar.AuthBaseURL);
    if (req.ok()) {
      String htmlBody = req.body();
      String outputHtml = utilsx.toUTF8(htmlBody);
      Document $ = Jsoup.parse(outputHtml);

      // =======================
      //
      // 未登录将会自动跳转到 `/0.htm` 登录界面, 但判断条件为 `title` 字符串为空
      //
      // =======================
      String title = $.title();

      if (title.length() == 0) {
        System.out.println("当前未登录");
        return false;
      }
      inetData inetWrap = new inetData();
      inetWrap.InitData($);
      System.out.println("当前已登录: ");
      return true;
    }

    return false;
  }

  /**
   * 查询是否登录
   */
  public boolean Check() {
    return false;
  }

}
