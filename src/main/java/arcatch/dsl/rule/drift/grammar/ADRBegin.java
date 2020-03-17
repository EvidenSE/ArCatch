package arcatch.dsl.rule.drift.grammar;

import arcatch.dsl.rule.common.Criticality;

public interface ADRBegin {

	public ADRBody criticality(Criticality level);
}
