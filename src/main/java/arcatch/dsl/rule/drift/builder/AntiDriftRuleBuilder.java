package arcatch.dsl.rule.drift.builder;

import arcatch.dsl.compartment.grammar.Compartment;
import arcatch.dsl.rule.common.Criticality;
import arcatch.dsl.rule.drift.grammar.ADRBegin;
import arcatch.dsl.rule.drift.grammar.ADRBody;
import arcatch.dsl.rule.drift.grammar.ADRConstraint;
import arcatch.dsl.rule.drift.grammar.ADREnd;
import arcatch.dsl.rule.drift.grammar.ADRLabel;
import arcatch.dsl.rule.drift.grammar.AntiDriftRule;
import arcatch.dsl.rule.drift.impl.AntiDriftRuleImpl;

public class AntiDriftRuleBuilder implements ADRBegin, ADRLabel, ADRBody, ADRConstraint, ADREnd {

	private static int ID = 1;

	private AntiDriftRule rule = new AntiDriftRuleImpl(genId());

	@Override
	public AntiDriftRule build() {
		return this.rule;
	}

	@Override
	public ADREnd constrainedTo(String expression) {
		this.rule.setExpression(expression);
		return this;
	}

	@Override
	public ADRConstraint compartiment(Compartment... compartments) {
		for (Compartment compartment : compartments) {
			this.rule.addCompartment(compartment);
		}
		return this;
	}

	@Override
	public ADRBody criticality(Criticality level) {
		rule.setCriticality(level);
		return this;
	}

	@Override
	public ADRBody label(String label) {
		this.rule.setLabel(label);
		return this;
	}

	private static String genId() {
		return (ID < 10) ? ("ADR0" + ID++) : ("ADR" + ID++);
	}

	public static void resetIdCount() {
		ID = 1;
	}

}
