package arcatch.dsl.rule.erosion.impl.must;

import java.util.Set;

import arcatch.dsl.rule.erosion.impl.kind.SignalToRule;
import arcatch.dsl.rule.erosion.impl.relation.TernaryRelation;
import arcatch.model.ModelFilter;
import arcatch.model.Operation;
import arcatch.model.SearchPattern;
import arcatch.model.Unit;

public class MustSignalTo extends SignalToRule {

	public MustSignalTo(String id) {
		super(id);
		secondPartDescription = "must";
	}

	@Override
	public boolean check() {
		isChecked = true;
		SearchPattern from = this.join(this.getFromNormalCompartments());
		SearchPattern pattern = this.join(this.getExceptionalCompartments());
		SearchPattern to = this.join(this.getToExceptionalCompartments());
		Set<TernaryRelation<Operation, Set<Unit>, Operation>> signalingsOfUpTo = ModelFilter.findSignalingsOfUpTo(from,
				pattern, to);
		if (signalingsOfUpTo.isEmpty()) {
			this.setPassing(false);
			this.violation.setOperationToUnitsToOperationViolations(signalingsOfUpTo);
		}
		return this.isPassing();
	}
}
