package arcatch.dsl.rule.erosion.impl.canOnly;

import java.util.Set;

import arcatch.dsl.rule.erosion.impl.kind.HandleRule;
import arcatch.dsl.rule.erosion.impl.relation.BinaryRelation;
import arcatch.model.ModelFilter;
import arcatch.model.Operation;
import arcatch.model.SearchPattern;
import arcatch.model.Unit;

public class CanOnlyHandle extends HandleRule {

	public CanOnlyHandle(String id) {
		super(id);
		secondPartDescription = "can only";
	}

	@Override
	public boolean check() {
		isChecked = true;
		SearchPattern from = this.join(this.getFromNormalCompartments());
		SearchPattern to = this.join(this.getExceptionalCompartments());
		Set<BinaryRelation<Operation, Set<Unit>>> handlings = ModelFilter.findComplementHandlingsOf(from, to);
		if (!handlings.isEmpty()) {
			this.setPassing(false);
			this.violation.setOperationToUnitsViolations(handlings);
		}
		return this.isPassing();
	}
}
