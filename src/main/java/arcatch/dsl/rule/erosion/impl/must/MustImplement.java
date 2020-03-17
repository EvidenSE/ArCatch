package arcatch.dsl.rule.erosion.impl.must;

import java.util.Set;

import arcatch.dsl.rule.erosion.impl.kind.ImplementRule;
import arcatch.dsl.rule.erosion.impl.relation.BinaryRelation;
import arcatch.model.ModelFilter;
import arcatch.model.SearchPattern;
import arcatch.model.Unit;

public class MustImplement extends ImplementRule {

	public MustImplement(String id) {
		super(id);
		secondPartDescription = "must";
	}

	@Override
	public boolean check() {
		isChecked = true;
		SearchPattern from = this.join(this.getFromNormalCompartments());
		SearchPattern to = this.join(this.getToNormalCompartments());
		Set<BinaryRelation<Unit, Set<Unit>>> implementations = ModelFilter.findImplementationsOf(from, to);
		if (implementations.isEmpty()) {
			this.setPassing(false);
			this.violation.setUnitToUnitsViolations(implementations);
		}
		return isPassing();
	}

}
