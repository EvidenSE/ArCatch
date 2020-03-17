package arcatch.dsl.compartment.grammar;

import arcatch.model.SearchPattern;

public interface Compartment extends Comparable<Compartment> {

	public String getId();

	public String getLabel();

	public void setLabel(String label);

	public void doUnitsLabeling();

	public void setSearchPattern(SearchPattern pattern);

	public SearchPattern getSearchPattern();

}
