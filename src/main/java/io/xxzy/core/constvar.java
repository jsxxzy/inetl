// Author: d1y<chenhonzhou@gmail.com>
// https://github.com/jsxxzy/inet/blob/master/inet.go

package io.xxzy.core;

public class constvar {

  static final String Pid = "2";

  static final String Calg = "12345678";

  static final String R1 = "0";

  static final String R2 = "1";

  static final String Para = "00";

  static final String MKKey = "123456";

  static final String baseURL = "http://210.22.55.58";

  static final String createURL(String route) {
    return baseURL + route;
  }

  /**
   * 基础路由
   */
  static final String AuthBaseURL = createURL("");

  /**
   * 登录路由
   */
  static final String LoginURL = createURL("/0.htm");

  /**
   * 注销路由
   */
  static final String LogoutURL = createURL("/F.htm");

  static final String ErrorNoAuth = "未登录"; // 0
  static final String ErrorUserAuthFail = "账号密码错误"; // 1
  static final String ErrorMultipleDevices = "多台设备同时在线"; // 5
  static final String Unknown = "未知";
  static final String LoginSuccessMessage = "登录成功";

  static final String InfoPortalname = "portalname"; // 名称
  static final String InfoTime = "time"; // 使用时长
  static final String InfoXip = "xip"; // 外网映射
  static final String InfoUID = "uid"; // 用户id
  static final String InfoIpv4 = "v4ip"; // ipv4
  static final String InfoIpv6 = "v6ip"; // ipv6
  static final String InfoFlow = "flow"; // 流量

  static final String QueryInfoSelectText = "script[language=\"JavaScript\"]"; // 查询信息的选择器


  /**
   * 未登录
   */
  static final int ErrorNoAuthCode = 0;

  /**
   * 账号密码错误
   */
  static final int ErrorUserAuthFailCode = 1;

  /**
   * 已登录
   */
  static final int ErrorLoginAuthCode = 2;

  /**
   * 登录成功
   */
  static final int LoginSuccess = 66;

  /**
   * 多台设备同时在线
   */
  static final int ErrorMultipleDevicesCode = 5;

  /**
   * 获取消息
   * @param code 返回代码
   * @return 消息
   */
  static final String GetMsg(int code) {
    switch (code) {
      case LoginSuccess:
        return LoginSuccessMessage;
      case ErrorNoAuthCode:
        return ErrorNoAuth;
      case ErrorUserAuthFailCode:
        return ErrorUserAuthFail;
      case ErrorMultipleDevicesCode:
        return ErrorMultipleDevices;
      default:
        return Unknown;
    }
  }

}
