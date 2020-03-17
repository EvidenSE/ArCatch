package arcatch.dsl.rule.common;

import arcatch.dsl.rule.drift.builder.AntiDriftRuleBuilder;
import arcatch.dsl.rule.drift.grammar.ADRBegin;
import arcatch.dsl.rule.erosion.builder.AntiErosionRuleBuilder;
import arcatch.dsl.rule.erosion.grammar.AERBegin;

public class RuleBuilder {

	public ADRBegin antiDrift() {
		return new AntiDriftRuleBuilder();
	}

	public AERBegin antiErosion() {
		return new AntiErosionRuleBuilder();
	}

}
