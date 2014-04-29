package uk.me.ajmfulcher.fpmplugin;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jruby.JarBootstrapMain;
import org.apache.commons.lang3.ArrayUtils;

import com.google.common.collect.Lists;

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
		if ("dir".equals(inputType)) {
			return ArrayUtils.addAll(this.getDirArgs(), this.getCoreArgs());
		} else {
			return this.getCoreArgs();
		}
	}
	
	public String[] getDirArgs(){
		return new String[] {"-n", this.packageName};
	}
	
	private final Pattern ARGS_PATTERN = Pattern.compile("(\"[^\"]*\")|('[^\']*')|([^\"' ]+)");
	
	public String[] getOptionalArgs(){
	    
	    List<String> args = Lists.newArrayList();
	    Matcher matcher = ARGS_PATTERN.matcher(this.optionalArgs);	    
	    while(matcher.find()) {
	        args.add(matcher.group());
	    }
	    
		return args.toArray(new String[args.size()]);
	}
	
	public String[] getArgs(){
		if (this.optionalArgs.equals("")) {
			return this.getMandatoryArgs();
		} else {
			return ArrayUtils.addAll(this.getOptionalArgs(),this.getMandatoryArgs());
		}
	}
	
	public String[] getTypeArgs(String inputType){
		String[] typeArgs = new String[1];
		if ("dir".equals(inputType)) {
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
