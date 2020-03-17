package arcatch.dsl.rule.erosion.impl.onlyCan;

import java.util.Set;

import arcatch.dsl.rule.erosion.impl.kind.SignalToRule;
import arcatch.dsl.rule.erosion.impl.relation.TernaryRelation;
import arcatch.model.ModelFilter;
import arcatch.model.Operation;
import arcatch.model.SearchPattern;
import arcatch.model.Unit;

public class OnlyCanSignalTo extends SignalToRule {

	public OnlyCanSignalTo(String id) {
		super(id);
		firstPartDescription = "only";
		secondPartDescription = "can";
	}

	@Override
	public boolean check() {
		isChecked = true;
		SearchPattern from = this.join(this.getFromNormalCompartments());
		SearchPattern pattern = this.join(this.getExceptionalCompartments());
		SearchPattern to = this.join(this.getToExceptionalCompartments());

		Set<TernaryRelation<Operation, Set<Unit>, Operation>> signalingsOfUpTo = ModelFilter
				.complementAndFindSignalingsOfUpTo(from, pattern, to);
		if (!signalingsOfUpTo.isEmpty()) {
			this.setPassing(false);
			this.violation.setOperationToUnitsToOperationViolations(signalingsOfUpTo);
		}
		return this.isPassing();
	}
}
