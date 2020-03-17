package arcatch.dsl.compartment.impl;

import arcatch.dsl.compartment.grammar.Compartment;
import arcatch.model.Model;
import arcatch.model.SearchPattern;
import arcatch.model.Unit;

public class CompartmentImpl implements Compartment {

	private String id;

	private String label;

	private SearchPattern pattern;

	public CompartmentImpl(String id, String label) {
		this.id = id;
		this.label = label;
	}

	public CompartmentImpl(String id) {
		this(id, "");
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public void setLabel(String label) {
		this.label = label;
	}

	@Override
	public void setSearchPattern(SearchPattern pattern) {
		this.pattern = pattern;
	}

	@Override
	public SearchPattern getSearchPattern() {
		return pattern;
	}

	@Override
	public void doUnitsLabeling() {
		for (Unit unit : Model.findUnits(getSearchPattern())) {
			unit.addOwner(this);
		}
	}

	@Override
	public int compareTo(Compartment object) {
		if (object != null && object instanceof Compartment) {
			return this.getId().compareTo(((Compartment) object).getId());
		}
		return 0;
	}

}
