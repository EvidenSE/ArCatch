package arcatch.report;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import arcatch.dsl.compartment.grammar.Compartment;
import arcatch.model.Model;
import arcatch.model.Unit;
import arcatch.util.Util;

public class UnitCompartmentMappingMatrixReporter implements Reporter {

	@Override
	public void print() {
		int rows = Model.getNotShadowUnits().size() + 1;
		int row = 0;
		int columns = Model.getCompartments().size() + 1;
		int column = 0;
		String[][] matrix = new String[rows][columns];

		for (row = 0; row < rows; row++) {
			for (column = 0; column < columns; column++) {
				matrix[row][column] = "";
			}
		}
		matrix[0][0] = "Unit";

		column = 0;
		List<Compartment> compartments = new ArrayList<>(Model.getCompartments());
		Collections.sort(compartments);
		for (Compartment compartment : compartments) {
			matrix[0][++column] = compartment.getId();
		}

		row = 0;
		List<Unit> units = new ArrayList<>(Model.getNotShadowUnits());
		Collections.sort(units);
		for (Unit unit : units) {
			matrix[++row][0] = unit.getQualifiedName();
		}

		for (row = 1; row < rows; row++) {
			if (Model.getUnit(matrix[row][0]).hasOwner()) {
				Set<Compartment> owners = Model.getUnit(matrix[row][0]).getOwners();
				Set<String> ownersIds = new HashSet<>();
				for (Compartment owner : owners) {
					ownersIds.add(owner.getId());
				}

				for (column = 1; column < columns; column++) {
					if (ownersIds.contains(matrix[0][column])) {
						matrix[row][column] = "X";
					}
				}
			}
		}
		StringBuffer buffer = new StringBuffer();
		for (row = 0; row < rows; row++) {
			for (column = 0; column < columns; column++) {
				buffer.append(matrix[row][column]);
				buffer.append(";");
			}
			buffer.append("\n");
		}
		String projectLabel = Model.getConfiguration().getProjectName().toLowerCase().trim();
		projectLabel += Model.getConfiguration().getProjectVersion().toLowerCase().trim();
		Util.generateCSVFile(projectLabel + "-unit-compartment-mapping-matrix", buffer.toString());
	}
}
