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
     * Output directory
     * @parameter expression="${fpm.inputType}"
     * @required
     */
    private String inputType;

    /**
     * Output directory
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
     * Output directory
     * @parameter expression="${fpm.typeArg}"
     * @required
     */
    private String typeArg;
    
    /**
     * Output directory
     * @parameter expression="${fpm.optionalArgs}"
     */
    private String optionalArgs;
    
	public void execute() throws MojoExecutionException, MojoFailureException {
		
		FpmPlugin fpm = new FpmPlugin(this.inputType,this.outputType,this.outputDir,this.typeArg);
		
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
