package uk.me.ajmfulcher.fpmplugin;

import java.io.File;
import org.jruby.JarBootstrapMain;
import org.apache.commons.lang3.ArrayUtils;

public class FpmPlugin {
	
	private String inputType;
	private String outputType;
	private String outputDir;
	private String packageName;
	private String packageVersion;
	private String optionalArgs = "";
	
	FpmPlugin(
		String inputType, 
		String outputType, 
		String outputDir, 
		String packageName,
		String packageVersion 
		)
	{
		this.inputType = inputType;
		this.outputType = outputType;
		this.outputDir = new File(outputDir, "fpm").toString();
		this.packageName = packageName;
		this.packageVersion = packageVersion;
		
		new File(this.outputDir, "base").mkdirs();
		new File(this.outputDir, "tmp").mkdirs();
		System.setProperty("user.dir", this.outputDir);
	}
	
	public void setOptionalArgs(String optionalArgs){
		this.optionalArgs = optionalArgs;
	}
	
	public String[] getCoreArgs(){
		String[] arguments = new String[10];
		arguments[0] = "-s";
		arguments[1] = this.inputType;
		arguments[2] = "-t";
		arguments[3] = this.outputType;
		arguments[4] = "-C";
		arguments[5] = new File(this.outputDir, "base").toString();
		arguments[6] = "--workdir";
		arguments[7] = new File(this.outputDir, "tmp").toString();
		arguments[8] = "-v";
		arguments[9] = this.packageVersion;
		return ArrayUtils.addAll(arguments,this.getTypeArgs(this.inputType));
	}
	
	public String[] getMandatoryArgs(){
		if (inputType == "dir") {
			return ArrayUtils.addAll(this.getDirArgs(), this.getCoreArgs());
		} else {
			return this.getCoreArgs();
		}
	}
	
	public String[] getDirArgs(){
		return new String[] {"-n", this.packageName};
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
	
	public String[] getTypeArgs(String inputType){
		String[] typeArgs = new String[1];
		if (inputType == "dir") {
			File baseDir = new File(this.outputDir, "base");
			typeArgs = baseDir.list();
		} else {
			typeArgs[0] = this.packageName;
		}
		return typeArgs;
	}
	
	public void invoke() throws Exception {
		JarBootstrapMain.main(this.getArgs());
	}

}
