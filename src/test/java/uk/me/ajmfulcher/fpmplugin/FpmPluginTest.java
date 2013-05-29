package uk.me.ajmfulcher.fpmplugin;

import static org.junit.Assert.*;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

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
		assertEquals(args,fpm.getArgs());
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
		assertEquals(args,fpm.getArgs());
	}
	
	@Test
	public void createPackage(){
		String inputType = "gem";
		String outputType = "deb";
		String outputDir = "/tmp";
		String typeArg = "json";
		FpmPlugin fpm = new FpmPlugin(inputType,outputType,outputDir,typeArg);
		try {
			fpm.invoke();
		} catch (Exception e) {
			e.printStackTrace();
		}
		File file = new File("/tmp/fpm/rubygem-json_1.8.0_amd64.deb");
		assertTrue(file.exists());
	}

}
