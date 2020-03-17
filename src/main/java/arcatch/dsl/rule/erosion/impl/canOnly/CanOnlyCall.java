package arcatch.dsl.rule.erosion.impl.canOnly;

import java.util.Set;

import arcatch.dsl.rule.erosion.impl.kind.CallRule;
import arcatch.dsl.rule.erosion.impl.relation.BinaryRelation;
import arcatch.model.ModelFilter;
import arcatch.model.Operation;
import arcatch.model.SearchPattern;

public class CanOnlyCall extends CallRule {

	public CanOnlyCall(String id) {
		super(id);
		secondPartDescription = "can only";
	}

	@Override
	public boolean check() {
		isChecked = true;
		SearchPattern from = this.join(this.getFromNormalCompartments());
		SearchPattern to = this.join(this.getToNormalCompartments());
		Set<BinaryRelation<Operation, Set<Operation>>> calls = ModelFilter.findCallsToComplementOperationsOf(from, to);
		if (!calls.isEmpty()) {
			this.setPassing(false);
			this.violation.setOperationToOperationsViolations(calls);
		}
		return this.isPassing();
	}
}
