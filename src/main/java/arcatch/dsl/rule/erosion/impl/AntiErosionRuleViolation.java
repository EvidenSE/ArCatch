package arcatch.dsl.rule.erosion.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import arcatch.dsl.rule.erosion.grammar.AntiErosionRule;
import arcatch.dsl.rule.erosion.impl.relation.BinaryRelation;
import arcatch.dsl.rule.erosion.impl.relation.TernaryRelation;
import arcatch.model.Operation;
import arcatch.model.Unit;

public class AntiErosionRuleViolation {

	private AntiErosionRule rule;

	private Set<BinaryRelation<Unit, Unit>> unitToUnitViolations;

	private Set<BinaryRelation<Unit, Set<Unit>>> unitToUnitsViolations;

	private Set<BinaryRelation<Operation, Set<Unit>>> operationToUnitsViolations;

	private Set<BinaryRelation<Operation, Set<Operation>>> operationToOperationsViolations;

	private Set<TernaryRelation<Operation, Unit, Unit>> operationToUnitToUnitViolations;

	private Set<TernaryRelation<Operation, Set<Unit>, Operation>> operationToUnitsToOperationViolations;

	public AntiErosionRuleViolation(AntiErosionRule rule) {
		this.rule = rule;
	}

	public AntiErosionRule getRule() {
		return rule;
	}

	public void setRule(AntiErosionRule rule) {
		this.rule = rule;
	}

	public Set<BinaryRelation<Unit, Unit>> getUnitToUnitViolations() {
		return unitToUnitViolations;
	}

	public void setUnitToUnitViolations(Set<BinaryRelation<Unit, Unit>> unitToUnitViolations) {
		this.unitToUnitViolations = unitToUnitViolations;
	}

	public Set<BinaryRelation<Unit, Set<Unit>>> getUnitToUnitsViolations() {
		return unitToUnitsViolations;
	}

	public void setUnitToUnitsViolations(Set<BinaryRelation<Unit, Set<Unit>>> unitToUnitsViolations) {
		this.unitToUnitsViolations = unitToUnitsViolations;
	}

	public Set<BinaryRelation<Operation, Set<Unit>>> getOperationToUnitsViolations() {
		return operationToUnitsViolations;
	}

	public void setOperationToUnitsViolations(Set<BinaryRelation<Operation, Set<Unit>>> operationToUnitsViolations) {
		this.operationToUnitsViolations = operationToUnitsViolations;
	}

	public Set<BinaryRelation<Operation, Set<Operation>>> getOperationToOperationsViolations() {
		return operationToOperationsViolations;
	}

	public void setOperationToOperationsViolations(
			Set<BinaryRelation<Operation, Set<Operation>>> operationToOperationsViolations) {
		this.operationToOperationsViolations = operationToOperationsViolations;
	}

	public Set<TernaryRelation<Operation, Unit, Unit>> getOperationToUnitToUnitViolations() {
		return operationToUnitToUnitViolations;
	}

	public void setOperationToUnitToUnitViolations(
			Set<TernaryRelation<Operation, Unit, Unit>> operationToUnitToUnitViolations) {
		this.operationToUnitToUnitViolations = operationToUnitToUnitViolations;
	}

	public Set<TernaryRelation<Operation, Set<Unit>, Operation>> getOperationToUnitsToOperationViolations() {
		return operationToUnitsToOperationViolations;
	}

	public void setOperationToUnitsToOperationViolations(
			Set<TernaryRelation<Operation, Set<Unit>, Operation>> operationToUnitsToOperationViolations) {
		this.operationToUnitsToOperationViolations = operationToUnitsToOperationViolations;
	}

	public List<Unit> getUnits() {
		List<Unit> units = new ArrayList<>();

		if (unitToUnitViolations != null) {
			for (BinaryRelation<Unit, Unit> relation : unitToUnitViolations) {
				units.add(relation.getLeft());
			}
		} else if (unitToUnitsViolations != null) {
			for (BinaryRelation<Unit, Set<Unit>> relation : unitToUnitsViolations) {
				units.add(relation.getLeft());
			}
		} else if (operationToUnitsViolations != null) {
			for (BinaryRelation<Operation, Set<Unit>> relation : operationToUnitsViolations) {
				units.add(relation.getLeft().getOwner());
			}
		} else if (operationToOperationsViolations != null) {
			for (BinaryRelation<Operation, Set<Operation>> relation : operationToOperationsViolations) {
				units.add(relation.getLeft().getOwner());
			}
		} else if (operationToUnitToUnitViolations != null) {
			for (TernaryRelation<Operation, Unit, Unit> relation : operationToUnitToUnitViolations) {
				units.add(relation.getLeft().getOwner());
			}
		} else if (operationToUnitsToOperationViolations != null) {
			for (TernaryRelation<Operation, Set<Unit>, Operation> relation : operationToUnitsToOperationViolations) {
				units.add(relation.getLeft().getOwner());
			}
		}
		return units;
	}

}
