package arcatch.dsl.rule.erosion.impl.onlyCan;

import java.util.Set;

import arcatch.dsl.rule.erosion.impl.kind.ExtendRule;
import arcatch.dsl.rule.erosion.impl.relation.BinaryRelation;
import arcatch.model.ModelFilter;
import arcatch.model.SearchPattern;
import arcatch.model.Unit;

public class OnlyCanExtend extends ExtendRule {

	public OnlyCanExtend(String id) {
		super(id);
		firstPartDescription = "only";
		secondPartDescription = "can";
	}

	@Override
	public boolean check() {
		isChecked = true;
		SearchPattern from = this.join(this.getFromNormalCompartments());
		SearchPattern to = this.join(this.getToNormalCompartments());
		Set<BinaryRelation<Unit, Unit>> extensions = ModelFilter.complementAndFindExtensionsOf(from, to);
		if (!extensions.isEmpty()) {
			this.setPassing(false);
			this.violation.setUnitToUnitViolations(extensions);
		}
		return this.isPassing();
	}
}
