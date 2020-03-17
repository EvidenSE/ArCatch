package arcatch.dsl.rule.erosion.impl;

import java.util.ArrayList;
import java.util.List;

import arcatch.dsl.compartment.grammar.Compartment;
import arcatch.dsl.rule.common.Criticality;
import arcatch.dsl.rule.common.DesignRule;
import arcatch.dsl.rule.erosion.grammar.AntiErosionRule;
import arcatch.model.SearchPattern;

public abstract class AntiErosionRuleImpl implements AntiErosionRule {

	private String id;

	private String label;

	private Criticality criticality;

	private boolean isPassing;

	protected boolean isChecked = false;

	protected String firstPartDescription = "";

	protected String secondPartDescription = "";

	private List<Compartment> fromCompartment;

	private List<Compartment> toCompartment;

	private List<Compartment> exceptionalCompartment;

	private List<Compartment> toExceptionalCompartment;

	protected AntiErosionRuleViolation violation;

	public AntiErosionRuleImpl(String id) {
		this.isPassing = true;
		this.id = id;
		fromCompartment = new ArrayList<>();
		toCompartment = new ArrayList<>();
		exceptionalCompartment = new ArrayList<>();
		toExceptionalCompartment = new ArrayList<>();
		this.violation = new AntiErosionRuleViolation(this);
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setLabel(String label) {
		this.label = label;
	}

	@Override
	public String getLabel() {
		return this.label;
	}

	@Override
	public void setCriticality(Criticality level) {
		this.criticality = level;
	}

	@Override
	public Criticality getCriticality() {
		return this.criticality;
	}

	@Override
	public void setPassing(boolean status) {
		this.isPassing = status;
	}

	@Override
	public boolean isPassing() {
		return this.isPassing;
	}

	@Override
	public boolean isChecked() {
		return this.isChecked;
	}

	@Override
	public void setFromNormalCompartments(List<Compartment> compartments) {
		this.fromCompartment = compartments;
	}

	@Override
	public List<Compartment> getFromNormalCompartments() {
		return this.fromCompartment;
	}

	@Override
	public void setToNormalCompartments(List<Compartment> compartments) {
		this.toCompartment = compartments;
	}

	@Override
	public List<Compartment> getToNormalCompartments() {
		return this.toCompartment;
	}

	@Override
	public void setExceptionalCompartments(List<Compartment> compartments) {
		this.exceptionalCompartment = compartments;
	}

	@Override
	public List<Compartment> getExceptionalCompartments() {
		return this.exceptionalCompartment;
	}

	@Override
	public void setToExceptionalCompartments(List<Compartment> compartments) {
		this.toExceptionalCompartment = compartments;
	}

	@Override
	public List<Compartment> getToExceptionalCompartments() {
		return this.toExceptionalCompartment;
	}

	public AntiErosionRuleViolation getViolation() {
		return this.violation;
	}

	public SearchPattern join(List<Compartment> compartments) {
		List<SearchPattern> patterns = new ArrayList<>();
		for (Compartment compartment : compartments) {
			patterns.add(compartment.getSearchPattern());
		}
		return SearchPattern.join(patterns);
	}

	@Override
	public int compareTo(DesignRule object) {
		if (object != null && object instanceof DesignRule) {
			return this.getId().compareTo(((DesignRule) object).getId());
		}
		return 0;
	}
}
