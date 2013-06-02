package uk.me.ajmfulcher.fpmplugin;

import java.io.File;
import org.jruby.JarBootstrapMain;
import org.apache.commons.lang3.ArrayUtils;

public class FpmPlugin {
	
	private String inputType;
	private String outputType;
	private String outputDir;
	private String typeArg;
	private String optionalArgs = "";
	
	FpmPlugin(String inputType, String outputType, String outputDir, String typeArg){
		this.inputType = inputType;
		this.outputType = outputType;
		this.outputDir = new File(outputDir, "fpm").toString();
		this.typeArg = typeArg;
		
		new File(this.outputDir, "base").mkdirs();
		new File(this.outputDir, "tmp").mkdirs();
		System.setProperty("user.dir", this.outputDir);
	}
	
	public void setOptionalArgs(String optionalArgs){
		this.optionalArgs = optionalArgs;
	}
	
	public String[] getMandatoryArgs(){
		String[] arguments = new String[9];
		arguments[0] = "-s";
		arguments[1] = inputType;
		arguments[2] = "-t";
		arguments[3] = outputType;
		arguments[4] = "-C";
		arguments[5] = new File(this.outputDir, "base").toString();
		arguments[6] = "--workdir";
		arguments[7] = new File(this.outputDir, "tmp").toString();
		arguments[8] = typeArg;
		return arguments;
	}
	
	public String[] getOptionalArgs(){
		return this.optionalArgs.split(" ");
	}
	
	public String[] getArgs(){
		if (this.optionalArgs == "") {
			return this.getMandatoryArgs();
		} else {
			return ArrayUtils.addAll(this.getOptionalArgs(),this.getMandatoryArgs());
		}
	}
	
	public void invoke() throws Exception {
		JarBootstrapMain.main(this.getArgs());
	}

}
