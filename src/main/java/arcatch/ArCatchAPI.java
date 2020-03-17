package arcatch;

import arcatch.dsl.ccflow.builder.CCFlowBuilder;
import arcatch.dsl.ccflow.grammar.CCFlow;
import arcatch.dsl.ccflow.grammar.CCFlowBegin;
import arcatch.dsl.compartment.builder.CompartmentBuilder;
import arcatch.dsl.compartment.grammar.CBegin;
import arcatch.dsl.compartment.grammar.Compartment;
import arcatch.dsl.config.builder.ConfigurationBuilder;
import arcatch.dsl.config.grammar.ConfigBegin;
import arcatch.dsl.config.grammar.Configuration;
import arcatch.dsl.rule.common.DesignRule;
import arcatch.dsl.rule.common.RuleBuilder;
import arcatch.model.ModelBuilder;
import arcatch.report.ReportManager;
import arcatch.report.Reporter;
import arcatch.report.ReporterFactory;

public class ArCatchAPI {

	public static void setConfiguration(Configuration configuration) {
		ModelBuilder.setConfiguration(configuration);
	}

	public static void addCompartment(Compartment compartment) {
		ModelBuilder.addCompartment(compartment);
	}

	public static void addRule(DesignRule rule) {
		ModelBuilder.addRule(rule);
	}

	public static void check(CCFlow flow) {
		if (ReportManager.isEmpty()) {
			fillReporter();
		}
		flow.start();
	}

	public static void check() {
		if (ReportManager.isEmpty()) {
			fillReporter();
		}
		conformanceCheckingFlowBuilder().buildModel().extractMetrics().checkRules().generateReport().clearModel()
				.build().start();
	}

	public static void addReporter(Reporter reporter) {
		ReportManager.add(reporter);
	}

	public static ConfigBegin configurationBuilder() {
		return new ConfigurationBuilder();
	}

	public static CBegin compartmentBuilder() {
		return new CompartmentBuilder();
	}

	public static RuleBuilder ruleBuilder() {
		return new RuleBuilder();
	}

	public static CCFlowBegin conformanceCheckingFlowBuilder() {
		return new CCFlowBuilder();
	}

	private static void fillReporter() {
		ReportManager.add(ReporterFactory.createBasicStatisticsReporter());
		ReportManager.add(ReporterFactory.createDegreeOfConformanceReporter());
		ReportManager.add(ReporterFactory.createCompartmentDescriptionReporter());
		ReportManager.add(ReporterFactory.createUnitCompartimentMappingMatrixReporter());
		ReportManager.add(ReporterFactory.createConformanceOfRulesReporter());
		ReportManager.add(ReporterFactory.createViolationMatrixReporter());
		ReportManager.add(ReporterFactory.createDegradationMatrixReporter());
		ReportManager.add(ReporterFactory.createRuleViolationAtUnitLevelReporter());
		ReportManager.add(ReporterFactory.createMetricsSuiteReporter());
		ReportManager.add(ReporterFactory.createMetricSuiteDescriptionReporter());
	}
}
