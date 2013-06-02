package uk.me.ajmfulcher.fpmplugin;

import static org.junit.Assert.*;
import java.io.File;

import org.junit.Test;

public class FpmPluginTest {
	
	@Test
	public void testCreate() {
		String inputType = "gem";
		String outputType = "deb";
		String outputDir = "/tmp";
		String typeArg = "json";
		FpmPlugin fpm = new FpmPlugin(inputType,outputType,outputDir,typeArg);
		String[] args = { "-s","gem","-t","deb","-C","/tmp/fpm/base","--workdir","/tmp/fpm/tmp","json" };
		assertArrayEquals(args,fpm.getArgs());
	}
	
	@Test
	public void addOptionalArgs() {
		String inputType = "gem";
		String outputType = "deb";
		String outputDir = "/tmp";
		String typeArg = "json";
		FpmPlugin fpm = new FpmPlugin(inputType,outputType,outputDir,typeArg);
		String optionalArgs = "--debug";
		String[] args = { optionalArgs, "-s","gem","-t","deb","-C","/tmp/fpm/base","--workdir","/tmp/fpm/tmp","json" };
		fpm.setOptionalArgs(optionalArgs);
		assertArrayEquals(args,fpm.getArgs());
	}
	
	@Test
	public void createPackage(){
		String inputType = "gem";
		String outputType = "deb";
		String outputDir = targetDir().toString();
		String typeArg = "json";
		FpmPlugin fpm = new FpmPlugin(inputType,outputType,outputDir,typeArg);
		try {
			fpm.invoke();
		} catch (Exception e) {
			e.printStackTrace();
		}
		File file = new File(outputDir + "/fpm/rubygem-json_1.8.0_amd64.deb");
		assertTrue(file.exists());
	}
	
	public File targetDir(){
	  String relPath = getClass().getProtectionDomain().getCodeSource().getLocation().getFile();
	  File targetDir = new File(relPath + "../../target");
	  if(!targetDir.exists()) {
	    targetDir.mkdir();
	  }
	  return targetDir;
	}

}
