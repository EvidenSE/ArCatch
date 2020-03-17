package arcatch.dsl.rule.erosion.impl.onlyCan;

import java.util.Set;

import arcatch.dsl.rule.erosion.impl.kind.CreateRule;
import arcatch.dsl.rule.erosion.impl.relation.BinaryRelation;
import arcatch.model.ModelFilter;
import arcatch.model.Operation;
import arcatch.model.SearchPattern;

public class OnlyCanCreate extends CreateRule {

	public OnlyCanCreate(String id) {
		super(id);
		firstPartDescription = "only";
		secondPartDescription = "can";
	}

	@Override
	public boolean check() {
		isChecked = true;
		SearchPattern from = this.join(this.getFromNormalCompartments());
		SearchPattern to = this.join(this.getToNormalCompartments());
		Set<BinaryRelation<Operation, Set<Operation>>> constructorCalls = ModelFilter
				.complementAndFindCallsToConstructorsOf(from, to);
		if (!constructorCalls.isEmpty()) {
			this.setPassing(false);
			this.violation.setOperationToOperationsViolations(constructorCalls);
		}
		return this.isPassing();
	}
}
