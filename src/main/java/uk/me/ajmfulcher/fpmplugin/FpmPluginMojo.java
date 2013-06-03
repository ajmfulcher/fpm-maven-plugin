package uk.me.ajmfulcher.fpmplugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * Goal which creates a package using FPM
 * @goal fpm
 * @requiresProject false
 */
public class FpmPluginMojo extends AbstractMojo {

    /**
     * Input type
     * @parameter expression="${fpm.inputType}"
     * @required
     */
    private String inputType;

    /**
     * Output type
     * @parameter expression="${fpm.outputType}"
     * @required
     */
    private String outputType;
    
    /**
     * Output directory
     * @parameter expression="${fpm.outputDir}" default-value="${project.build.directory}"
     * @required
     */
    private String outputDir;
    
    /**
     * Package name
     * @parameter expression="${fpm.packageName}" default-value="${project.artifactId}"
     * @required
     */
    private String packageName;
    
    /**
     * Package name
     * @parameter expression="${fpm.packageVersion}" default-value="${project.version}"
     * @required
     */
    private String packageVersion;
    
    /**
     * Optional arguments
     * @parameter expression="${fpm.optionalArgs}"
     */
    private String optionalArgs;
    
	public void execute() throws MojoExecutionException, MojoFailureException {
		
		FpmPlugin fpm = new FpmPlugin(this.inputType,this.outputType,this.outputDir,this.packageName,this.packageVersion);
		
		if (optionalArgs != null){
			fpm.setOptionalArgs(this.optionalArgs);
		}
		
		try {
			fpm.invoke();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
