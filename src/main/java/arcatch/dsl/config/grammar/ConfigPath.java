package arcatch.dsl.config.grammar;

public interface ConfigPath {

	public ConfigDependencies projectPath(String path);

	public ConfigEnd projectPathWithMaven(String path);
}
