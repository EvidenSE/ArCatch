package arcatch.dsl.rule.erosion.impl.canOnly;

import java.util.Set;

import arcatch.dsl.rule.erosion.impl.kind.ExtendRule;
import arcatch.dsl.rule.erosion.impl.relation.BinaryRelation;
import arcatch.model.ModelFilter;
import arcatch.model.SearchPattern;
import arcatch.model.Unit;

public class CanOnlyExtend extends ExtendRule {

	public CanOnlyExtend(String id) {
		super(id);
		secondPartDescription = "can only";
	}

	@Override
	public boolean check() {
		isChecked = true;
		SearchPattern from = this.join(this.getFromNormalCompartments());
		SearchPattern to = this.join(this.getToNormalCompartments());
		Set<BinaryRelation<Unit, Unit>> extensions = ModelFilter.findComplementExtensionsOf(from, to);
		if (!extensions.isEmpty()) {
			this.setPassing(false);
			this.violation.setUnitToUnitViolations(extensions);
		}
		return this.isPassing();
	}
}
