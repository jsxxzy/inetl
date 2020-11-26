// Author: d1y<chenhonzhou@gmail.com>

package io.xxzy;


import io.xxzy.core.iNet;
import io.xxzy.core.inetData;

/**
 * `CLI` 实现
 */
public class App
{

  public static void main(String[] args)
  {
    if (args.length == 0) {
      System.out.println();
      System.out.println("=============>");
      System.out.println(" login: 登录");
      System.out.println("   get: 获取保存的账号密码");
      System.out.println("  save: 保存账号密码");
      System.out.println("   fix: 初始化账号密码");
      System.out.println(" check: 查询是否登录");
      System.out.println("  info: 查询信息");
      System.out.println("logout: 注销");
      System.out.println("=============>");
      System.out.println();
      return;
    }
    String action = args[0];
    iNet netSoftware = new iNet();
    switch (action) {
      case "login":
        break;
      case "info": // 查询信息
        inetData infoData = netSoftware.QueryInfo();
        if (infoData == null) {
          System.out.println("未知错误");
          return;
        }
        System.out.printf("使用时长: %s\n", infoData.GetHumanDate());
        System.out.printf("使用流量: %s\n", infoData.GetHumanFlow());
        System.out.printf(" 用户id: %s\n", infoData.UID);
        System.out.printf("内网地址: %s\n", infoData.Ipv4);
        break;
    }
  }
}
