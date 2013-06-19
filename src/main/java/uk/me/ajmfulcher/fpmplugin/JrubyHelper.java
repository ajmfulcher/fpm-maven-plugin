package uk.me.ajmfulcher.fpmplugin;

public class JrubyHelper {
  public String[] getJarPath() {
    String[] paths = new String[2];
    paths[0] = JrubyHelper.class.getProtectionDomain().getCodeSource().getLocation().toString() + "!";
    paths[1] = JrubyHelper.class.getProtectionDomain().getCodeSource().getLocation().getPath();
    return paths;
  }
}
