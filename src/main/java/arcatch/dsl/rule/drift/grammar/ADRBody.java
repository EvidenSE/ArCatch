package arcatch.dsl.rule.drift.grammar;

import arcatch.dsl.compartment.grammar.Compartment;

public interface ADRBody {

	public ADRConstraint compartiment(Compartment... compartments);
}