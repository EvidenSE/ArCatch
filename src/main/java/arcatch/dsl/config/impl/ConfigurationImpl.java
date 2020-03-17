package arcatch.dsl.config.impl;

import java.util.Set;

import arcatch.dsl.config.grammar.Configuration;

public class ConfigurationImpl implements Configuration {

	private boolean isMavenBasedProject;

	private String projectName;

	private String projectVersion;

	private String projectPath;

	private Set<String> projectDependencies;

	public ConfigurationImpl(boolean isMavenBasedProject, String projectName, String projectVersion, String projectPath,
			Set<String> projectDependencies) {
		super();
		this.isMavenBasedProject = isMavenBasedProject;
		this.projectName = projectName;
		this.projectVersion = projectVersion;
		this.projectPath = projectPath;
		this.projectDependencies = projectDependencies;
	}

	@Override
	public String getProjectName() {
		return this.projectName;
	}

	@Override
	public String getProjectPath() {
		return this.projectPath;
	}

	@Override
	public Set<String> getDependencies() {
		return this.projectDependencies;
	}

	@Override
	public boolean isMavenBasedProject() {
		return this.isMavenBasedProject;
	}

	@Override
	public String getProjectVersion() {
		return this.projectVersion;
	}

}
