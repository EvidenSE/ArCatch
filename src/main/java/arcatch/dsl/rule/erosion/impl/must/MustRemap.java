package arcatch.dsl.rule.erosion.impl.must;

import java.util.Set;

import arcatch.dsl.rule.erosion.impl.kind.RemapRule;
import arcatch.dsl.rule.erosion.impl.relation.TernaryRelation;
import arcatch.model.ModelFilter;
import arcatch.model.Operation;
import arcatch.model.SearchPattern;
import arcatch.model.Unit;

public class MustRemap extends RemapRule {

	public MustRemap(String id) {
		super(id);
		secondPartDescription = "must";
	}

	@Override
	public boolean check() {
		isChecked = true;
		SearchPattern source = this.join(this.getFromNormalCompartments());
		SearchPattern from = this.join(this.getExceptionalCompartments());
		SearchPattern to = this.join(this.getToExceptionalCompartments());
		Set<TernaryRelation<Operation, Unit, Unit>> remappings = ModelFilter.findRemappingsOf(source, from, to);
		if (remappings.isEmpty()) {
			this.setPassing(false);
			this.violation.setOperationToUnitToUnitViolations(remappings);
		}
		return this.isPassing();
	}
}
