package io.jenkins.plugins.main;

import hudson.model.BuildListener;
import hudson.model.AbstractBuild;
import hudson.FilePath;

import hudson.tasks.Maven;
import hudson.tools.ToolProperty;
import hudson.tasks.Maven.MavenInstallation;
import hudson.tasks.Maven.MavenInstaller;

import java.util.ArrayList;

/* Install apache maven 3.6.3 if no maven found on node */
public class MavenCustomInstaller implements Installer{
	private AbstractBuild build;
	private BuildListener listener;

	public MavenCustomInstaller(AbstractBuild build , BuildListener listener) {
		this.build = build;
		this.listener = listener;
	}

	public void install(){
		if (this.build == null || this.listener == null) {
			throw new IllegalArgumentException("Failed to install default maven");
		}
		try {
			MavenInstallation mvnInstallation = new MavenInstallation("maven",Config.getInstance().getMavenHome());
	        mvnInstallation = mvnInstallation.forEnvironment(this.build.getEnvironment());
	        MavenInstaller mvnInstaller = new MavenInstaller("3.6.3");
	        FilePath fp = mvnInstaller.performInstallation(mvnInstallation,this.build.getBuiltOn(),this.listener);
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
}