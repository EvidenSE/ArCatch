package arcatch.dsl.rule.erosion.impl.must;

import java.util.Set;

import arcatch.dsl.rule.erosion.impl.kind.CreateRule;
import arcatch.dsl.rule.erosion.impl.relation.BinaryRelation;
import arcatch.model.ModelFilter;
import arcatch.model.Operation;
import arcatch.model.SearchPattern;

public class MustCreate extends CreateRule {

	public MustCreate(String id) {
		super(id);
		secondPartDescription = "must";
	}

	@Override
	public boolean check() {
		isChecked = true;
		SearchPattern from = this.join(this.getFromNormalCompartments());
		SearchPattern to = this.join(this.getToNormalCompartments());
		Set<BinaryRelation<Operation, Set<Operation>>> constructorCalls = ModelFilter.findCallsToConstructorsOf(from,
				to);
		if (constructorCalls.isEmpty()) {
			this.setPassing(false);
			this.violation.setOperationToOperationsViolations(constructorCalls);
		}
		return this.isPassing();
	}
}
