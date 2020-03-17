package arcatch.dsl.compartment.grammar;

public interface CMatching extends CEnd {

	public CMatching matching(String classPattern);

	public CMatching matching(String classPattern, String methodPattern);
}
