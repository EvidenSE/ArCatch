package arcatch.dsl.config.grammar;

import java.util.Set;

public interface Configuration {

	public String getProjectName();
	
	public String getProjectVersion();
	
	public boolean isMavenBasedProject();

	public String getProjectPath();

	public Set<String> getDependencies();
}
