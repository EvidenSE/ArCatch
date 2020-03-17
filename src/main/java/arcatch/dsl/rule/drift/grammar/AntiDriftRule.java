package arcatch.dsl.rule.drift.grammar;

import java.util.List;

import arcatch.dsl.compartment.grammar.Compartment;
import arcatch.dsl.rule.common.DesignRule;
import arcatch.dsl.rule.drift.impl.AntiDriftRuleViolation;

public interface AntiDriftRule extends DesignRule {

	public List<Compartment> getCompartments();

	public void setCompartments(List<Compartment> compartments);

	public void addCompartment(Compartment compartment);

	public AntiDriftRuleViolation getViolation();

	public void setExpression(String expression);

	public String getExpression();

}
