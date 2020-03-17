package arcatch.report;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import arcatch.dsl.compartment.grammar.Compartment;
import arcatch.model.Model;
import arcatch.util.Util;

public class CompartmentDescriptionReporter implements Reporter {

	@Override
	public void print() {
		StringBuffer buffer = new StringBuffer("Id;Label;Class Search Pattern;Method Search Pattern\n");
		List<Compartment> compartments = new ArrayList<>(Model.getCompartments());
		Collections.sort(compartments);
		for (Compartment compartment : compartments) {
			buffer.append(compartment.getId());
			buffer.append(";");
			buffer.append(compartment.getLabel());
			buffer.append(";");
			buffer.append(compartment.getSearchPattern().getPlainClassSearchPattern());
			buffer.append(";");
			buffer.append(compartment.getSearchPattern().getPlainMethodSearchPattern());
			buffer.append("\n");
		}
		String projectLabel = Model.getConfiguration().getProjectName().toLowerCase().trim();
		projectLabel += Model.getConfiguration().getProjectVersion().toLowerCase().trim();
		Util.generateCSVFile(projectLabel + "-compartment-description", buffer.toString());
	}
}
