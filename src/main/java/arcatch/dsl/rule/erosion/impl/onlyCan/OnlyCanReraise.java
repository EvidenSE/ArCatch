package arcatch.dsl.rule.erosion.impl.onlyCan;

import java.util.Set;

import arcatch.dsl.rule.erosion.impl.kind.ReraiseRule;
import arcatch.dsl.rule.erosion.impl.relation.BinaryRelation;
import arcatch.model.ModelFilter;
import arcatch.model.Operation;
import arcatch.model.SearchPattern;
import arcatch.model.Unit;

public class OnlyCanReraise extends ReraiseRule {

	public OnlyCanReraise(String id) {
		super(id);
		firstPartDescription = "only";
		secondPartDescription = "can";
	}

	@Override
	public boolean check() {
		isChecked = true;
		SearchPattern from = this.join(this.getFromNormalCompartments());
		SearchPattern to = this.join(this.getExceptionalCompartments());
		Set<BinaryRelation<Operation, Set<Unit>>> reraisings = ModelFilter.complementAndFindReraisingsOf(from, to);
		if (!reraisings.isEmpty()) {
			this.setPassing(false);
			this.violation.setOperationToUnitsViolations(reraisings);
		}
		return this.isPassing();
	}
}
