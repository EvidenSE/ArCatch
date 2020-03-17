package arcatch.dsl.rule.erosion.impl.onlyCan;

import java.util.Set;

import arcatch.dsl.rule.erosion.impl.kind.RaiseRule;
import arcatch.dsl.rule.erosion.impl.relation.BinaryRelation;
import arcatch.model.ModelFilter;
import arcatch.model.Operation;
import arcatch.model.SearchPattern;
import arcatch.model.Unit;

public class OnlyCanRaise extends RaiseRule {

	public OnlyCanRaise(String id) {
		super(id);
		firstPartDescription = "only";
		secondPartDescription = "can";
	}

	@Override
	public boolean check() {
		isChecked = true;
		SearchPattern from = this.join(this.getFromNormalCompartments());
		SearchPattern to = this.join(this.getExceptionalCompartments());
		Set<BinaryRelation<Operation, Set<Unit>>> raisings = ModelFilter.complementAndFindRaisingsOf(from, to);
		if (!raisings.isEmpty()) {
			this.setPassing(false);
			this.violation.setOperationToUnitsViolations(raisings);
		}
		return this.isPassing();
	}
}
