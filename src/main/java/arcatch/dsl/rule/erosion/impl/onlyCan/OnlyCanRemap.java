package arcatch.dsl.rule.erosion.impl.onlyCan;

import java.util.Set;

import arcatch.dsl.rule.erosion.impl.kind.RemapRule;
import arcatch.dsl.rule.erosion.impl.relation.TernaryRelation;
import arcatch.model.ModelFilter;
import arcatch.model.Operation;
import arcatch.model.SearchPattern;
import arcatch.model.Unit;

public class OnlyCanRemap extends RemapRule {

	public OnlyCanRemap(String id) {
		super(id);
		firstPartDescription = "only";
		secondPartDescription = "can";
	}

	@Override
	public boolean check() {
		isChecked = true;
		SearchPattern source = this.join(this.getFromNormalCompartments());
		SearchPattern from = this.join(this.getExceptionalCompartments());
		SearchPattern to = this.join(this.getToExceptionalCompartments());
		Set<TernaryRelation<Operation, Unit, Unit>> remappings = ModelFilter.complementAndFindRemappingsOf(source, from,
				to);
		if (!remappings.isEmpty()) {
			this.setPassing(false);
			this.violation.setOperationToUnitToUnitViolations(remappings);
		}
		return this.isPassing();
	}
}
