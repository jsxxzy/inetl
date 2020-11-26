// Author: d1y<chenhonzhou@gmail.com>

package io.xxzy.core;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class inetData {

  /**
   * 名称
   */
  public String Portalname;

  /**
   * 使用时间
   */
  public int Time = 0;

  /**
   * 外网映射Ip
   */
  public String Xip;

  /**
   * 用户id
   */
  public String UID;

  /**
   * IPv4
   */
  public String Ipv4;

  /**
   * IPv6
   */
  public String Ipv6;

  /**
   * 流量
   */
  public int Flow = 0;

  /**
   * TODO
   * 简单的格式化一下
   */
  private String easyHumanTime(int time) {
    int h = 60;
    if (time == h) {
      return "1小时";
    }
    if (time < h) {
      return time + "分钟";
    }
    int m = time % h;
    int nh = time / h;
    return String.format("%s小时%s分钟", m, nh);
  }

  /**
   * 阳间的时间
   * @return 格式化好的时间
   */
  public String GetHumanDate() {
    int curr = this.Time;
    if (curr == 0) {
      return "0分钟";
    }
    return this.easyHumanTime(curr);
  }

  /**
   * 阳间的流量
   */
  public String GetHumanFlow() {
    int flow = this.Flow;
    if (flow == 0) {
      return "0kb";
    }
    long inputFlow = flow * 1024;
    return utilsx.BytesToHuman(inputFlow);
  }

  /**
   * 运营商的js写的不标准, 此函数试图去除一些不标准
   *
   * 一开口就老外包了...
   *
   * @param raw 源数据
   * @return 正确的`js-code`
   */
  private String jsCodeRemoveCommit(String raw) {
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


  /**
   * 初始化
   * @param $ `Jsoup` 实例
   */
  void InitData(Document $) {
    js jsRuntime = new js();
    Elements jsData = $.select(constvar.QueryInfoSelectText);
    String jsDataText = jsData.get(0).html();
    String jsInputData = jsCodeRemoveCommit(jsDataText);
    jsRuntime.RunScript(jsInputData);
    String cacheTimeString = jsRuntime.GetString(constvar.InfoTime);
    try {
      this.Time = Integer.parseInt(cacheTimeString);
    } catch (NumberFormatException e) {
      e.printStackTrace();
    }
    this.Ipv6 = jsRuntime.GetString(constvar.InfoIpv6);
    this.Ipv4 = jsRuntime.GetString(constvar.InfoIpv4);
    this.UID = jsRuntime.GetString(constvar.InfoUID);
    this.Xip = jsRuntime.GetString(constvar.InfoXip);
    this.Portalname = jsRuntime.GetString(constvar.InfoPortalname);
    String cacheFlowData = jsRuntime.GetString(constvar.InfoFlow);
    try {
      this.Flow = Integer.parseInt(cacheFlowData);
    } catch (NumberFormatException e) {
      e.printStackTrace();
    };
  }
}
