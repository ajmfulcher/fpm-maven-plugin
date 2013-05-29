maven-fpm-plugin
================

A Maven plugin to create system packages using FPM

```xml
  <plugin>
      <groupId>uk.me.ajmfulcher.fpmplugin</groupId>
      <artifactId>maven-fpm-plugin</artifactId>
      <configuration>
        <typeArg>fpm</typeArg>
        <inputType>gem</inputType>
        <outputType>deb</outputType>
      </configuration>
      <executions>
        <execution>
            <id>run-fpm</id>
            <phase>package</phase>
            <goals>
                <goal>fpm</goal>
            </goals>
        </execution>
      </executions>
  </plugin>
```
