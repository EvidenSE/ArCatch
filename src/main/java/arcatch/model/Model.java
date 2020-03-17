package arcatch.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import arcatch.dsl.compartment.builder.CompartmentBuilder;
import arcatch.dsl.compartment.grammar.Compartment;
import arcatch.dsl.config.grammar.Configuration;
import arcatch.dsl.rule.common.DesignRule;
import arcatch.dsl.rule.drift.builder.AntiDriftRuleBuilder;
import arcatch.dsl.rule.drift.grammar.AntiDriftRule;
import arcatch.dsl.rule.erosion.builder.AntiErosionRuleBuilder;
import arcatch.dsl.rule.erosion.grammar.AntiErosionRule;
import arcatch.metric.Measure;
import arcatch.metric.Metric;

public class Model {

	private static Configuration configuration;

	private static Set<Compartment> compartments = new HashSet<>();

	private static Set<AntiDriftRule> antiDriftRules = new HashSet<>();

	private static Set<AntiErosionRule> antiErosionRules = new HashSet<>();

	private static Map<String, Unit> unitsMap = new HashMap<>();

	public static Configuration getConfiguration() {
		return configuration;
	}

	public static void setConfiguration(Configuration configuration) {
		Model.configuration = configuration;
	}

	public static void addCompartment(Compartment compartment) {
		compartments.add(compartment);
	}

	public static Set<Compartment> getCompartments() {
		return compartments;
	}

	public static void addRule(DesignRule rule) {
		if (rule instanceof AntiErosionRule) {
			antiErosionRules.add((AntiErosionRule) rule);
		} else {
			antiDriftRules.add((AntiDriftRule) rule);
		}
	}

	public static Set<DesignRule> getRules() {
		Set<DesignRule> allRules = new HashSet<>();
		allRules.addAll(antiErosionRules);
		allRules.addAll(antiDriftRules);
		return allRules;
	}

	public static Set<DesignRule> getCheckedRules() {
		Set<DesignRule> checkedRules = new HashSet<>();
		for (DesignRule rule : getRules()) {
			if (rule.isChecked()) {
				checkedRules.add(rule);
			}
		}
		return checkedRules;
	}

	public static Set<DesignRule> getUncheckedRules() {
		Set<DesignRule> uncheckedRules = getRules();
		uncheckedRules.removeAll(getCheckedRules());
		return uncheckedRules;
	}

	public static Set<DesignRule> getPassingRules() {
		Set<DesignRule> passingRules = new HashSet<>();
		for (DesignRule rule : getCheckedRules()) {
			if (rule.isPassing()) {
				passingRules.add(rule);
			}
		}
		return passingRules;
	}

	public static Set<DesignRule> getFailingRules() {
		Set<DesignRule> failingRules = getCheckedRules();
		failingRules.removeAll(getPassingRules());
		return failingRules;
	}

	public static Set<AntiErosionRule> getAntiErosionRules() {
		return antiErosionRules;
	}

	public static Set<AntiErosionRule> getCheckedAntiErosionRules() {
		Set<AntiErosionRule> checkedRules = new HashSet<>();
		for (AntiErosionRule rule : getAntiErosionRules()) {
			if (rule.isChecked()) {
				checkedRules.add(rule);
			}
		}
		return checkedRules;
	}

	public static Set<AntiErosionRule> getUncheckedAntiErosionRules() {
		Set<AntiErosionRule> uncheckedRules = getAntiErosionRules();
		uncheckedRules.removeAll(getCheckedAntiErosionRules());
		return uncheckedRules;
	}

	public static Set<AntiErosionRule> getPassingAntiErosionRules() {
		Set<AntiErosionRule> passingRules = new HashSet<>();
		for (AntiErosionRule rule : getCheckedAntiErosionRules()) {
			if (rule.isPassing()) {
				passingRules.add(rule);
			}
		}
		return passingRules;
	}

	public static Set<AntiErosionRule> getFailingAntiErosionRules() {
		Set<AntiErosionRule> failingRules = getCheckedAntiErosionRules();
		failingRules.removeAll(getPassingAntiErosionRules());
		return failingRules;
	}

	public static Set<AntiDriftRule> getAntiDriftRules() {
		return antiDriftRules;
	}

	public static Set<AntiDriftRule> getCheckedAntiDriftRules() {
		Set<AntiDriftRule> checkedRules = new HashSet<>();
		for (AntiDriftRule rule : getAntiDriftRules()) {
			if (rule.isChecked()) {
				checkedRules.add(rule);
			}
		}
		return checkedRules;
	}

	public static Set<AntiDriftRule> getUncheckedAntiDriftRules() {
		Set<AntiDriftRule> uncheckedRules = getAntiDriftRules();
		uncheckedRules.removeAll(getCheckedAntiDriftRules());
		return uncheckedRules;
	}

	public static Set<AntiDriftRule> getPassingAntiDriftRules() {
		Set<AntiDriftRule> passingRules = new HashSet<>();
		for (AntiDriftRule rule : getCheckedAntiDriftRules()) {
			if (rule.isPassing()) {
				passingRules.add(rule);
			}
		}
		return passingRules;
	}

	public static Set<AntiDriftRule> getFailingAntiDriftRules() {
		Set<AntiDriftRule> failingRules = getCheckedAntiDriftRules();
		failingRules.removeAll(getPassingAntiDriftRules());
		return failingRules;
	}

	public static void addUnit(Unit unit) {
		unitsMap.put(unit.getQualifiedName(), unit);
	}

	public static boolean isEmpty() {
		return unitsMap.isEmpty();
	}

	public static void clear() {
		CompartmentBuilder.resetIdCount();
		compartments.clear();
		AntiDriftRuleBuilder.resetIdCount();
		antiDriftRules.clear();
		AntiErosionRuleBuilder.resetIdCount();
		antiErosionRules.clear();
		unitsMap.clear();
	}
	
	public static Unit getUnit(String qualifiedName) {
		return unitsMap.get(qualifiedName);
	}

	public static Set<Unit> getUnits() {
		return new HashSet<>(unitsMap.values());
	}

	public static Set<Unit> getNotShadowUnits() {
		Set<Unit> result = new HashSet<>();
		for (Unit unit : getUnits()) {
			if (!unit.isShadow()) {
				result.add(unit);
			}
		}
		return result;
	}
	
	public static Set<Unit> getNormalUnits() {
		Set<Unit> normalUnits = new HashSet<>();
		for (Unit unit : getUnits()) {
			if (!unit.isException()) {
				normalUnits.add(unit);
			}
		}
		return normalUnits;
	}
	
	public static Set<Unit> getNotShadowNormalUnits() {
		Set<Unit> normalUnits = new HashSet<>();
		for (Unit unit : getNormalUnits()) {
			if (!unit.isShadow()) {
				normalUnits.add(unit);
			}
		}
		return normalUnits;
	}

	public static Set<Unit> getNormalClasses() {
		Set<Unit> result = new HashSet<>();
		for (Unit unit : getNormalUnits()) {
			if (!unit.isInterface()) {
				result.add(unit);
			}
		}
		return result;
	}

	public static Set<Unit> getNotShadowNormalClasses() {
		Set<Unit> result = new HashSet<>();
		for (Unit unit : getNormalClasses()) {
			if (!unit.isShadow()) {
				result.add(unit);
			}
		}
		return result;
	}
	
	public static Set<Unit> getClasses() {
		Set<Unit> result = new HashSet<>();
		for (Unit unit : getUnits()) {
			if (!unit.isInterface()) {
				result.add(unit);
			}
		}
		return result;
	}
	
	public static Set<Unit> getNotShadowClasses() {
		Set<Unit> result = new HashSet<>();
		for (Unit unit : getClasses()) {
			if (!unit.isShadow()) {
				result.add(unit);
			}
		}
		return result;
	}


	public static Set<Unit> getInterfaces() {
		Set<Unit> interfaceUnits = new HashSet<>();
		for (Unit unit : getUnits()) {
			if (unit.isInterface()) {
				interfaceUnits.add(unit);
			}
		}
		return interfaceUnits;
	}

	public static Set<Unit> getNotShadowInterfaces() {
		Set<Unit> interfaceUnits = new HashSet<>();
		for (Unit unit : getInterfaces()) {
			if (!unit.isShadow()) {
				interfaceUnits.add(unit);
			}
		}
		return interfaceUnits;
	}

	public static Set<Unit> getExceptions() {
		Set<Unit> exceptionUnits = new HashSet<>();
		for (Unit unit : getUnits()) {
			if (unit.isException()) {
				exceptionUnits.add(unit);
			}
		}
		return exceptionUnits;
	}

	public static Set<Unit> getNotShadowExceptions() {
		Set<Unit> exceptionUnits = new HashSet<>();
		for (Unit unit : getExceptions()) {
			if (!unit.isShadow()) {
				exceptionUnits.add(unit);
			}
		}
		return exceptionUnits;
	}

	public static Set<Unit> findNotShadowUnits(SearchPattern searchPattern) {
		Set<Unit> notShadowUnits = new HashSet<>();
		for (Unit unit : findUnits(searchPattern)) {
			if (!unit.isShadow()) {
				notShadowUnits.add(unit);
			}
		}
		return notShadowUnits;
	}

	public static Set<Unit> findNotShadowClasses(SearchPattern searchPattern) {
		Set<Unit> notShadowUnits = new HashSet<>();
		for (Unit unit : findUnits(searchPattern)) {
			if (!unit.isShadow() && !unit.isInterface()) {
				notShadowUnits.add(unit);
			}
		}
		return notShadowUnits;
	}

	public static Set<Unit> findUnits(SearchPattern searchPattern) {
		Set<Unit> units = new HashSet<>();
		for (Unit unit : getUnits()) {
			final Pattern pattern = Pattern.compile(searchPattern.getClassSearchPattern());
			if (pattern.matcher(unit.getQualifiedName()).matches()) {
				units.add(unit);
			}
		}
		return units;
	}

	public static Set<Unit> findUnitsComplement(SearchPattern searchPattern) {
		Set<Unit> units = getUnits();
		Set<Unit> filteredUnits = findUnits(searchPattern);
		units.removeAll(filteredUnits);
		return units;
	}

	public static Set<Unit> findNormalUnits(SearchPattern searchPattern) {
		Set<Unit> units = new HashSet<>();
		for (Unit unit : getNormalUnits()) {
			final Pattern pattern = Pattern.compile(searchPattern.getClassSearchPattern());
			if (pattern.matcher(unit.getQualifiedName()).matches()) {
				units.add(unit);
			}
		}
		return units;
	}

	public static Set<Unit> findNormalUnitsComplement(SearchPattern searchPattern) {
		Set<Unit> allUnits = getNormalUnits();
		Set<Unit> filteredNormalUnits = findNormalUnits(searchPattern);
		allUnits.removeAll(filteredNormalUnits);
		return allUnits;
	}

	public static Set<Unit> findExceptionUnits(SearchPattern searchPattern) {
		Set<Unit> units = new HashSet<>();
		for (Unit unit : getExceptions()) {
			final Pattern pattern = Pattern.compile(searchPattern.getClassSearchPattern());
			if (pattern.matcher(unit.getQualifiedName()).matches()) {
				units.add(unit);
			}
		}
		return units;
	}

	public static Set<Unit> findExceptionUnitsComplement(SearchPattern searchPattern) {
		Set<Unit> allExceptionUnits = getExceptions();
		Set<Unit> filteredExceptionUnits = findExceptionUnits(searchPattern);
		allExceptionUnits.removeAll(filteredExceptionUnits);
		return allExceptionUnits;
	}

	public static Set<Unit> findInterfaceUnits(SearchPattern searchPattern) {
		Set<Unit> units = new HashSet<>();
		for (Unit unit : getInterfaces()) {
			final Pattern pattern = Pattern.compile(searchPattern.getClassSearchPattern());
			if (pattern.matcher(unit.getQualifiedName()).matches()) {
				units.add(unit);
			}
		}
		return units;
	}

	public static Set<Unit> findInterfaceUnitsComplement(SearchPattern searchPattern) {
		Set<Unit> units = getInterfaces();
		Set<Unit> filteredUnits = findInterfaceUnits(searchPattern);
		units.removeAll(filteredUnits);
		return units;
	}

	public static void addOperationCall(Operation caller, Operation callee) {
		caller.addCallee(callee);
		callee.addCaller(caller);
	}

	public static Set<Operation> getOperations() {
		Set<Operation> operations = new HashSet<>();
		for (Unit unit : getUnits()) {
			operations.addAll(unit.getOperations());
		}
		return operations;
	}

	public static Set<Operation> findOperations(SearchPattern searchPattern) {
		Set<Operation> operations = new HashSet<>();
		for (Unit unit : findUnits(searchPattern)) {
			operations.addAll(unit.findOperations(searchPattern));
		}
		return operations;
	}

	public static Set<Operation> findOperationComplement(SearchPattern searchPattern) {
		Set<Operation> operations = getOperations();
		Set<Operation> foundOperations = findOperations(searchPattern);
		operations.removeAll(foundOperations);
		return operations;
	}

	public static Set<Operation> getConstructors() {
		Set<Operation> constructors = new HashSet<>();
		for (Unit unit : getUnits()) {
			if (!unit.getConstructors().isEmpty()) {
				constructors.addAll(unit.getConstructors());
			}
		}
		return constructors;
	}

	public static Set<Operation> findConstructors(SearchPattern searchPattern) {
		Set<Operation> constructors = new HashSet<>();
		for (Unit unit : findUnits(searchPattern)) {
			if (!unit.getConstructors().isEmpty()) {
				constructors.addAll(unit.getConstructors());
			}
		}
		return constructors;
	}

	public static Set<Operation> findConstructorsComplement(SearchPattern searchPattern) {
		Set<Operation> constructors = getConstructors();
		Set<Operation> foundConstructors = findConstructors(searchPattern);
		constructors.removeAll(foundConstructors);
		return constructors;
	}

	public static void addMeasure(String qualifiedName, Measure measure) {
		if (getUnit(qualifiedName) != null) {
			getUnit(qualifiedName).addMeasure(measure);
		}
	}

	public static Double getMetricValueAtProjectLevel(Metric metric) {
		Double value = 0.0;
		for (Unit unit : Model.getNotShadowUnits()) {
			value += unit.getMetricValue(metric);
		}
		return value;
	}
}