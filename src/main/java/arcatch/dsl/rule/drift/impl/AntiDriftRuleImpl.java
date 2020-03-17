package arcatch.dsl.rule.drift.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;

import arcatch.dsl.compartment.grammar.Compartment;
import arcatch.dsl.rule.common.Criticality;
import arcatch.dsl.rule.common.DesignRule;
import arcatch.dsl.rule.drift.grammar.AntiDriftRule;
import arcatch.model.Model;
import arcatch.model.Unit;

public class AntiDriftRuleImpl implements AntiDriftRule {

	private String id;

	private String label;

	private AntiDriftRuleViolation violation;

	private Criticality criticality;

	private boolean isPassing = true;

	private boolean isChecked = false;

	private List<Compartment> compartments;

	private String expression;

	private Expression fullExpression;

	public AntiDriftRuleImpl(String id) {
		this.id = id;
		this.label = "";
		this.violation = new AntiDriftRuleViolation();
		this.compartments = new ArrayList<>();
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
	public AntiDriftRuleViolation getViolation() {
		return violation;
	}

	@Override
	public Criticality getCriticality() {
		return criticality;
	}

	@Override
	public void setCriticality(Criticality criticality) {
		this.criticality = criticality;
	}

	@Override
	public void setExpression(String expression) {
		this.expression = expression;

	}

	@Override
	public String getExpression() {
		return this.expression;
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
	public List<Compartment> getCompartments() {
		return compartments;
	}

	@Override
	public void setCompartments(List<Compartment> compartments) {
		this.compartments = compartments;
	}

	@Override
	public void addCompartment(Compartment compartment) {
		this.compartments.add(compartment);
	}

	@Override
	public String getDescription() {
		StringBuffer description = new StringBuffer();
		description.append("compartment (");
		Iterator<Compartment> it = this.compartments.iterator();

		while (it.hasNext()) {
			Compartment compartment = it.next();
			if (compartment.getLabel() != null && !compartment.getLabel().isEmpty()) {
				description.append(compartment.getLabel());
			} else {
				description.append(compartment.getId());
			}
			if (it.hasNext()) {
				description.append(", ");
			}
		}

		description.append(") constrained to (");
		description.append(this.expression);
		description.append(")");
		return description.toString();
	}

	@Override
	public boolean check() {
		isChecked = true;
		for (Compartment compartment : this.getCompartments()) {

			for (Unit unit : Model.findNotShadowClasses(compartment.getSearchPattern())) {
				if (!check(unit)) {
					this.setPassing(false);
					violation.addUnit(unit);
				}
			}
		}
		return this.isPassing();
	}

	private boolean check(Unit unit) {
		boolean result = false;
		this.isChecked = true;
		fullExpression = new Expression(this.expression);
		for (String argumentName : fullExpression.getMissingUserDefinedArguments()) {
			Argument argument = new Argument(argumentName.trim(), unit.getMetricValueByName(argumentName.trim()));
			fullExpression.addArguments(argument);
		}
		if (fullExpression.checkSyntax()) {
			result = fullExpression.calculate() == 1.0;
		} else {
			System.out.println("Expression Syntax Error: " + fullExpression.getErrorMessage());
		}
		this.setPassing(result);
		return result;
	}

	@Override
	public int compareTo(DesignRule object) {
		if (object != null && object instanceof DesignRule) {
			return this.getId().compareTo(((DesignRule) object).getId());
		}
		return 0;
	}

}