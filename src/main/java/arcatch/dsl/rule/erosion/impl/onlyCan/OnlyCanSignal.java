package arcatch.dsl.rule.erosion.impl.onlyCan;

import java.util.Set;

import arcatch.dsl.rule.erosion.impl.kind.SignalRule;
import arcatch.dsl.rule.erosion.impl.relation.BinaryRelation;
import arcatch.model.ModelFilter;
import arcatch.model.Operation;
import arcatch.model.SearchPattern;
import arcatch.model.Unit;

public class OnlyCanSignal extends SignalRule {

	public OnlyCanSignal(String id) {
		super(id);
		firstPartDescription = "only";
		secondPartDescription = "can";
	}

	@Override
	public boolean check() {
		isChecked = true;
		SearchPattern from = this.join(this.getFromNormalCompartments());
		SearchPattern to = this.join(this.getExceptionalCompartments());
		Set<BinaryRelation<Operation, Set<Unit>>> signalings = ModelFilter.complementAndFindSignalingsOf(from, to);
		if (!signalings.isEmpty()) {
			this.setPassing(false);
			this.violation.setOperationToUnitsViolations(signalings);
		}
		return this.isPassing();
	}
}
