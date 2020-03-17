package arcatch.model;

import java.util.List;

public class SearchPattern {

	private String compiledClassSearchPattern;

	private String compiledMethodSearchPattern;
	
	private String plainClassSearchPattern;

	private String plainMethodSearchPattern;

	private SearchPattern() {
		this("*", "*:*");
	}

	private SearchPattern(String classSearchPattern) {
		this(classSearchPattern, "*:*");
	}

	private SearchPattern(String classSearchPattern, String methodSearchPattern) {
		this.compiledClassSearchPattern = classSearchPattern;
		this.compiledMethodSearchPattern = methodSearchPattern;
	}
	
	public String getClassSearchPattern() {
		return compiledClassSearchPattern;
	}

	public String getCompiledMethodSearchPattern() {
		return compiledMethodSearchPattern;
	}
	
	public String getPlainClassSearchPattern() {
		return plainClassSearchPattern;
	}
	
	public String getPlainMethodSearchPattern() {
		return plainMethodSearchPattern;
	}
	
	private void setPlainClassSearchPattern(String plainClassSearchPattern) {
		this.plainClassSearchPattern = plainClassSearchPattern;
	}

	private void setPlainMethodSearchPattern(String plainMethodSearchPattern) {
		this.plainMethodSearchPattern = plainMethodSearchPattern;
	}

	public static SearchPattern join(SearchPattern first, SearchPattern second) {
		String classPattern = "(" + first.getClassSearchPattern() + "|" + second.getClassSearchPattern() + ")";
		String methodPattern = "(" + first.getCompiledMethodSearchPattern() + "|" + second.getCompiledMethodSearchPattern() + ")";
		SearchPattern instance = new SearchPattern(classPattern, methodPattern);
		instance.setPlainClassSearchPattern("(" + first.getPlainClassSearchPattern() + "|" + second.getPlainClassSearchPattern() + ")");
		instance.setPlainMethodSearchPattern("(" + first.getPlainMethodSearchPattern() + "|" + second.getPlainMethodSearchPattern() + ")");
		return instance;
	}

	public static SearchPattern join(SearchPattern... patterns) {
		SearchPattern result = new SearchPattern();
		if (patterns != null && patterns.length > 0) {
			result = patterns[0];
			if (patterns.length > 2) {
				for (int i = 1; i < patterns.length; i++) {
					result = SearchPattern.join(result, patterns[i]);
				}
			}
		}
		return result;
	}
	
	public static SearchPattern join(List<SearchPattern> patterns) {
		return SearchPattern.join(patterns.toArray(new SearchPattern[patterns.size()]));
	}

	public static SearchPattern compile() {
		SearchPattern instance = new SearchPattern();
		instance.doClassSearchPatternProcess();
		instance.doMethodSearchPatternProcess();
		instance.setPlainClassSearchPattern("*");
		instance.setPlainMethodSearchPattern("*:*");
		return instance;
	}
	
	public static SearchPattern compile(String classSearchPattern) {
		SearchPattern instance = new SearchPattern(classSearchPattern);
		instance.doClassSearchPatternProcess();
		instance.doMethodSearchPatternProcess();
		instance.setPlainClassSearchPattern(classSearchPattern);
		instance.setPlainMethodSearchPattern("*:*");
		return instance;
	}

	public static SearchPattern compile(String classSearchPattern, String methodSearchPattern) {
		SearchPattern instance = new SearchPattern(classSearchPattern, methodSearchPattern);
		instance.doClassSearchPatternProcess();
		instance.doMethodSearchPatternProcess();
		instance.setPlainClassSearchPattern(classSearchPattern);
		instance.setPlainMethodSearchPattern(methodSearchPattern);
		return instance;
	}

	private void doClassSearchPatternProcess() {
		String regex = this.compiledClassSearchPattern.trim().replace(" ", "").replace("\t", "");
		regex = regex.replace(".", "\\.");
		regex = regex.replace("*", ".*");
		if (regex.contains(";")) {
			String[] parts = regex.split(";");
			if (parts.length > 1) {
				regex = "(" + parts[0] + ")";
				for (int i = 0; i < (parts.length - 1); i++) {
					regex = regex + "|(" + parts[i + 1] + ")";
				}
				regex = "(" + regex + ")";
			}

		} else {
			regex = "(" + regex + ")";
		}
		this.compiledClassSearchPattern = regex;
	}

	private void doMethodSearchPatternProcess() {
		String regex = this.compiledMethodSearchPattern.trim().replace("\t", "");
		regex = regex.replace(".", "\\.");
		regex = regex.replace("*", ".*");
		if (regex.contains(";")) {
			String[] parts = regex.split(";");
			if (parts.length > 1) {
				regex = "(" + doMethodSearchPatternPartProcess(parts[0]) + ")";
				for (int i = 0; i < (parts.length - 1); i++) {
					regex = regex + "|(" + doMethodSearchPatternPartProcess(parts[i + 1]) + ")";
				}
				regex = "(" + regex + ")";
			}

		} else {
			regex = "(" + doMethodSearchPatternPartProcess(regex) + ")";
		}
		this.compiledMethodSearchPattern = regex;
	}

	private String doMethodSearchPatternPartProcess(String part) {
		String regex = part;

		regex = regex.replace("(", "\\(");

		regex = regex.replace(")", "\\)");

		regex = regex.replace(",", "\\,");

		String[] parts = regex.split(":");
		if (parts.length > 1) {
			regex = parts[1] + "\\s" + parts[0];
		}

		return regex;
	}

}
