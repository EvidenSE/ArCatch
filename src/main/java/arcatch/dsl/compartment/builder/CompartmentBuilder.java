package arcatch.dsl.compartment.builder;

import arcatch.dsl.compartment.grammar.CBegin;
import arcatch.dsl.compartment.grammar.CEnd;
import arcatch.dsl.compartment.grammar.CMatching;
import arcatch.dsl.compartment.grammar.Compartment;
import arcatch.dsl.compartment.impl.CompartmentImpl;
import arcatch.model.SearchPattern;

public class CompartmentBuilder implements CBegin, CMatching, CEnd {

	private static int ID = 1;

	private Compartment compartment;

	@Override
	public Compartment build() {
		if (this.compartment.getSearchPattern() == null) {
			this.compartment.setSearchPattern(SearchPattern.compile());
		}
		return this.compartment;
	}

	@Override
	public CMatching matching(String classPattern) {
		if (this.compartment.getSearchPattern() != null) {
			this.compartment.setSearchPattern(
					SearchPattern.join(this.compartment.getSearchPattern(), SearchPattern.compile(classPattern)));
		} else {
			this.compartment.setSearchPattern(SearchPattern.compile(classPattern));
		}
		return this;
	}

	@Override
	public CMatching matching(String classPattern, String methodPattern) {
		if (this.compartment.getSearchPattern() != null) {
			this.compartment.setSearchPattern(SearchPattern.join(this.compartment.getSearchPattern(),
					SearchPattern.compile(classPattern, methodPattern)));
		}
		this.compartment.setSearchPattern(SearchPattern.compile(classPattern, methodPattern));
		return this;
	}

	@Override
	public CMatching compartment(String label) {
		this.compartment = new CompartmentImpl(genId(), label);
		return this;
	}

	@Override
	public CMatching compartment() {
		this.compartment = new CompartmentImpl(genId());
		return this;
	}

	private static String genId() {
		return (ID < 10) ? ("CPT0" + ID++) : ("CPT" + ID++);
	}

	public static void resetIdCount() {
		ID = 1;
	}

}
