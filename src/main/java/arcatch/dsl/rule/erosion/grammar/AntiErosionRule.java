package arcatch.dsl.rule.erosion.grammar;

import java.util.List;

import arcatch.dsl.compartment.grammar.Compartment;
import arcatch.dsl.rule.common.DesignRule;
import arcatch.dsl.rule.erosion.impl.AntiErosionRuleViolation;

public interface AntiErosionRule extends DesignRule {

	public void setFromNormalCompartments(List<Compartment> compartments);

	public List<Compartment> getFromNormalCompartments();

	public void setToNormalCompartments(List<Compartment> compartments);

	public List<Compartment> getToNormalCompartments();

	public void setExceptionalCompartments(List<Compartment> compartments);

	public List<Compartment> getExceptionalCompartments();

	public void setToExceptionalCompartments(List<Compartment> compartments);

	public List<Compartment> getToExceptionalCompartments();

	public AntiErosionRuleViolation getViolation();
}