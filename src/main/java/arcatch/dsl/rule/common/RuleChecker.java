package arcatch.dsl.rule.common;

import arcatch.dsl.compartment.grammar.Compartment;
import arcatch.dsl.rule.drift.grammar.AntiDriftRule;
import arcatch.dsl.rule.erosion.grammar.AntiErosionRule;
import arcatch.model.Model;
import arcatch.model.ModelBuilder;

public class RuleChecker {

	public static void checkAll() {
		for (Compartment compartment : Model.getCompartments()) {
			compartment.doUnitsLabeling();
		}

		for (AntiDriftRule rule : Model.getUncheckedAntiDriftRules()) {
			rule.check();
		}

		for (AntiErosionRule rule : Model.getUncheckedAntiErosionRules()) {
			rule.check();
		}
	}

	public static boolean check(DesignRule rule) {
		ModelBuilder.addRule(rule);
		for (Compartment compartment : Model.getCompartments()) {
			compartment.doUnitsLabeling();
		}
		if (!rule.isChecked()) {
			rule.check();
		}
		return rule.isPassing();
	}
}
