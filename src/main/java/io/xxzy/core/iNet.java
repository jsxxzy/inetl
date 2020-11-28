// Author: d1y<chenhonzhou@gmail.com>

package io.xxzy.core;

import com.github.kevinsawicki.http.HttpRequest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Map;

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
   * @param u 账号
   * @param p 密码
   * @return 返回消息
   */
  public String LoginMessage(String u, String p) {
    int code = this.Login(u.trim(), p.trim());
    return constvar.GetMsg(code);
  }

  
  /**
   * 登录
   */
  public int Login(String u, String p) {
    String hex = this.CreatePassword(p);
    Map<String, String> data = new HashMap<String, String>();
    data.put("DDDDD", u);
    data.put("upass", hex);
    data.put("R1", constvar.R1);
    data.put("R2", constvar.R2);
    data.put("para", constvar.Para);
    data.put("0MKKey", constvar.MKKey);
    HttpRequest htmlBody = HttpRequest.post(constvar.LoginURL).form(data);
    if (!htmlBody.ok()) {
      return constvar.ErrorNoAuthCode;
    }
    String body = htmlBody.body();
    Document $ = Jsoup.parse(utilsx.toUTF8(body));
    Elements eles = $.select("SCRIPT");
    String jsCacheCode = eles.get(2).html();
    String jsCodeBlock = utilsx.jsCodeRemoveCommit(jsCacheCode);
    js jsVM = new js();
    jsVM.RunScript(jsCodeBlock);
//    String msg;
//    try {
//      jsVM.engine.eval("mm = Msg.toString()");
//    } catch (ScriptException e) {
//      e.printStackTrace();
//      return constvar.ErrorNoAuthCode;
//    }
//    msg = jsVM.GetString("mm");
    String msga = jsVM.GetString("msga");
//    if (msg.length() == 0 || msga.length() == 0) {
//    if (msga.equals("")) return constvar.LoginSuccess;
//    int code = Integer.parseInt(msg);
//    System.out.printf("返回码: %s\n", code);
    switch (msga) {
      case "5":
//        System.out.println("多台设备在线");
        return constvar.ErrorMultipleDevicesCode;
      case "1":
//        System.out.println("账号密码错误");
        return constvar.ErrorUserAuthFailCode;
    }
    return constvar.LoginSuccess;
  }

  /**
   * 注销
   */
  public boolean Logout() {
    HttpRequest req = HttpRequest.get(constvar.LogoutURL);
    return req.ok();
  }

  /**
   * 查询信息
   */
  public inetData QueryInfo() {
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
//        System.out.println("当前未登录");
        return null;
      }
      inetData inetWrap = new inetData();
      inetWrap.InitData($);
//      System.out.println("当前已登录: ");
      return inetWrap;
    }

    return null;
  }

  /**
   * 查询是否登录
   */
  public boolean HasLogin() {
    inetData queryData = this.QueryInfo();
    return queryData == null;
  }

}
