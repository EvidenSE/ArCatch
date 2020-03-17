package arcatch.dsl.rule.drift.impl;

import java.util.HashSet;
import java.util.Set;

import arcatch.model.Unit;

public class AntiDriftRuleViolation {

	private Set<Unit> units;

	public AntiDriftRuleViolation() {
		this.units = new HashSet<>();
	}

	public Set<Unit> getUnits() {
		return units;
	}

	public void setUnits(Set<Unit> units) {
		this.units = units;
	}

	public void addUnit(Unit unit) {
		this.units.add(unit);
	}

	public boolean hasViolation() {
		return !this.units.isEmpty();
	}

}
