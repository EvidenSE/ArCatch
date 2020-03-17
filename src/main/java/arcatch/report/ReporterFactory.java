package arcatch.report;

public class ReporterFactory {

	public static Reporter createBasicStatisticsReporter() {
		return new BasicStatisticsReporter();
	}

	public static Reporter createDegreeOfConformanceReporter() {
		return new DegreeOfConformanceReporter();
	}

	public static Reporter createCompartmentDescriptionReporter() {
		return new CompartmentDescriptionReporter();
	}

	public static Reporter createUnitCompartimentMappingMatrixReporter() {
		return new UnitCompartmentMappingMatrixReporter();
	}

	public static Reporter createConformanceOfRulesReporter() {
		return new ConformanceOfRulesReporter();
	}

	public static Reporter createDegradationMatrixReporter() {
		return new DegradationMatrixReporter();
	}
	
	public static Reporter createViolationMatrixReporter() {
		return new ViolationMatrixReporter();
	}

	public static Reporter createMetricsSuiteReporter() {
		return new MetricsSuiteReporter();
	}

	public static Reporter createRuleViolationAtUnitLevelReporter() {
		return new RuleViolationAtUnitLevelReporter();
	}

	public static Reporter createMetricSuiteDescriptionReporter() {
		return new MetricSuiteDescriptionReporter();
	}
}
