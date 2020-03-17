package arcatch.dsl.config.grammar;

public interface ConfigBegin {

	public ConfigPath projectName(String name);
	
	public ConfigPath projectNameAndVersion(String name, String version);
}
