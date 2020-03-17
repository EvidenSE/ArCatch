package arcatch.dsl.config.builder;

import java.util.HashSet;
import java.util.Set;

import arcatch.dsl.config.grammar.ConfigBegin;
import arcatch.dsl.config.grammar.ConfigDependencies;
import arcatch.dsl.config.grammar.ConfigEnd;
import arcatch.dsl.config.grammar.ConfigPath;
import arcatch.dsl.config.grammar.Configuration;
import arcatch.dsl.config.impl.ConfigurationImpl;

public class ConfigurationBuilder implements ConfigBegin, ConfigPath, ConfigDependencies, ConfigEnd {

	private String projectName;
	
	private String projectVersion;

	private String projectPath;

	private Set<String> projectDependencies = new HashSet<>();

	private boolean isMavenBasedProject;

	@Override
	public ConfigDependencies addDependency(String path) {
		this.projectDependencies.add(path);
		return this;
	}

	@Override
	public Configuration build() {
		return new ConfigurationImpl(isMavenBasedProject, projectName, projectVersion, projectPath, projectDependencies);
	}

	@Override
	public ConfigDependencies projectPath(String path) {
		this.projectPath = path;
		this.isMavenBasedProject = false;
		return this;
	}

	@Override
	public ConfigEnd projectPathWithMaven(String path) {
		this.projectPath = path;
		this.isMavenBasedProject = true;
		return this;
	}

	@Override
	public ConfigPath projectName(String name) {
		this.projectName = name;
		this.projectVersion = "unknown";
		return this;
	}

	@Override
	public ConfigPath projectNameAndVersion(String name, String version) {
		this.projectName = name;
		this.projectVersion = version;
		return this;
	}

}
