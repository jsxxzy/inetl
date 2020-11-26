// copyright by: https://www.jianshu.com/p/a6cfe013ac9d
// 我不生产代码, 我只是代码的搬运工
// d1y<chenhonzhou@gmail.com>

package io.xxzy.core;

import javax.script.*;

public class js {

  ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");

  public Object GetVar(String key) {
    return this.engine.get(key);
  }

  public String GetString(String key) {
    try {
      Object obj = GetVar(key);
      String r = (String)obj;
      return r.trim();
    } catch (Exception e) {
      e.printStackTrace();
      return "";
    }
  }

  public boolean RunScript(String initScript) {
    try {
      this.engine.eval(initScript);
      return true;
    }catch (ScriptException err) {
      err.printStackTrace();
      return false;
    }
  }

}
