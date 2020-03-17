package arcatch.dsl.ccflow.grammar;

import arcatch.dsl.rule.common.RuleChecker;
import arcatch.model.Model;
import arcatch.model.ModelBuilder;
import arcatch.report.ReportManager;

public class CCFlow {

	private boolean hasMetricExtractionStep;

	private boolean hasReportGenerationStep;

	public CCFlow(boolean hasMetricExtractionStep, boolean hasReportGenerationStep) {
		super();
		this.hasMetricExtractionStep = hasMetricExtractionStep;
		this.hasReportGenerationStep = hasReportGenerationStep;
	}

	public void start() {
		ModelBuilder.buildModel();
		if(hasMetricExtractionStep) {
			ModelBuilder.extractMetrics();
		}
		RuleChecker.checkAll();
		if(hasReportGenerationStep) {
			ReportManager.generateReports();
		}
		Model.clear();
		ReportManager.clear();
	}
}
