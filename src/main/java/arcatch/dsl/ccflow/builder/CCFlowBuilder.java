package arcatch.dsl.ccflow.builder;

import arcatch.dsl.ccflow.grammar.CCFlow;
import arcatch.dsl.ccflow.grammar.CCFlowBegin;
import arcatch.dsl.ccflow.grammar.CCFlowClear;
import arcatch.dsl.ccflow.grammar.CCFlowEnd;
import arcatch.dsl.ccflow.grammar.CCFlowMetric;
import arcatch.dsl.ccflow.grammar.CCFlowReport;
import arcatch.dsl.ccflow.grammar.CCFlowRules;

public class CCFlowBuilder implements CCFlowBegin, CCFlowMetric, CCFlowRules, CCFlowReport, CCFlowEnd {

	private boolean hasMetricExtractionStep = false;

	private boolean hasReportGenerationStep = false;
	
	@Override
	public CCFlow build() {
		return new CCFlow(hasMetricExtractionStep, hasReportGenerationStep);
	}

	@Override
	public CCFlowEnd clearModel() {
		return this;
	}
	
	@Override
	public CCFlowClear generateReport() {
		this.hasReportGenerationStep = true;
		return this;
	}

	@Override
	public CCFlowReport checkRules() {
		return this;
	}

	@Override
	public CCFlowRules extractMetrics() {
		this.hasMetricExtractionStep = true;
		return this;
	}

	@Override
	public CCFlowMetric buildModel() {
		return this;
	}
}
