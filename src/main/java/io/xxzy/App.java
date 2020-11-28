// Author: d1y<chenhonzhou@gmail.com>

package io.xxzy;

import io.xxzy.core.iNet;
import io.xxzy.core.inetData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

class CliHelp {

  /**
   * 配置文件名
   */
  private String configFilename = ".inetconfig";

  private String offset = ",";

  /**
   * 自动创建文件
   */
  private boolean autoCreateFile(Path path) {
    File file = path.toFile();
    if (!file.exists()) {
      try {
        return file.createNewFile();
      } catch (IOException e) {
        return false;
      } // 文件已创建
    } else {
      return true; // 文件已存在
    }
  }

  /**
   * 读取文件
   */
  private String readFile(File file) throws IOException {
    StringBuilder fileContents = new StringBuilder((int)file.length());

    try (Scanner scanner = new Scanner(file)) {
      while(scanner.hasNextLine()) {
        fileContents.append(scanner.nextLine() + System.lineSeparator());
      }
      return fileContents.toString();
    }
  }

  /**
   * 解析格式
   */
  private String[] parseLocalAuth(String rawString) {
    String[] em = new String[0];
    if (rawString.length() == 0) {
      return em;
    }
    String[] arr = rawString.split(this.offset);
    if (arr.length <= 1) {
      return em;
    }
    String[] r = new String[2];
    r[0] = arr[0];
    r[1] = arr[1];
    return r;
  }

  /**
   * 获取配置文件目录
   */
  public Path GetConfigPath() {
    String home = System.getProperty("user.home");
    Path now = Paths.get(home, this.configFilename);
    this.autoCreateFile(now);
    return now;
  }

  /**
   * 设置本地用户
   */
  public void SetLocalAuth(String u, String p) throws IOException {
    Path path = this.GetConfigPath();
    String sp = u + this.offset + p;
    this.writeFile(path, sp);
  }

  /**
   * 写入文件
   */
  private void writeFile(Path path, String content) throws IOException {
    File file = path.toFile();
    FileWriter fileWriterCtx = new FileWriter(file);
    fileWriterCtx.write(content);
    fileWriterCtx.close();
  }

  /**
   * 清除本地用户
   */
  public void Clean() throws IOException {
    Path path = this.GetConfigPath();
    this.writeFile(path, "");
  }

  /**
   * 获取本地用户
   */
  public String[] GetLocalAuth() {
    Path p = GetConfigPath();
    String f;
    try {
      f = this.readFile(p.toFile());
    } catch (IOException e) {
      f = "";
    }
    return this.parseLocalAuth(f);
  }

}

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
    CliHelp cli = new CliHelp();
    switch (action) {
      case "fix":
        try {
          cli.Clean();
          System.out.println("清除成功");
        } catch (IOException e) {
          System.out.println("未知错误");
        }
        break;
      case "get":
        String[] upList = cli.GetLocalAuth();
        if (upList.length <= 1) {
          System.out.println("没有保存账号密码");
          return;
        }
        String u2 = upList[0];
        String p2 = upList[1];
        System.out.printf("账号: %s \n密码: %s \n", u2, p2);
        break;
      case "save":
        if (args.length <= 2) {
          System.out.println("请传入账号密码");
          return;
        }
        String u1 = args[1];
        String p1 = args[2];
        try {
          cli.SetLocalAuth(u1, p1);
          System.out.println("保存成功");
        } catch (IOException e) {
          System.out.println("保存失败");
        }
        break;
      case "check": // 查询是否登录
        boolean isLogin = netSoftware.HasLogin();
        System.out.println(!isLogin ? "已登录" : "未登录");
        break;
      case "login": // 登录
        String u = "";
        String p = "";
        String[] uplist1 = cli.GetLocalAuth();
        if (uplist1.length == 2) {
          u = uplist1[0];
          p = uplist1[1];
        }
        if (args.length >= 2) {
          u = args[1];
          p = args[2];
        }
        if (u.length()==0 || p.length() == 0) {
          System.out.println("请传入账号密码");
          return;
        }
        String msg = netSoftware.LoginMessage(u, p);
        System.out.println(msg);
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
      case "logout": // 注销
        boolean flag = netSoftware.Logout();
        System.out.println(flag ? "已注销" : "注销失败, 未知错误");
    }
  }
}
