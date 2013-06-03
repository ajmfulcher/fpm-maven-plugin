package uk.me.ajmfulcher.fpmplugin;

public class JrubyHelper {
  public String getJarPath() {
    return getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
  }
}
