package arcatch.report;

import arcatch.metric.Measure;
import arcatch.model.Model;
import arcatch.model.Unit;
import arcatch.util.Util;

public class MetricsSuiteReporter implements Reporter {

	@Override
	public void print() {
		StringBuffer buffer = new StringBuffer("Unit");

		for (Unit unit : Model.getNotShadowClasses()) {
			for (Measure measure : unit.getMetricSuite().getMeasures()) {
				buffer.append(";");
				buffer.append(measure.getMetric().getShortName());
			}
			break;
		}

		for (Unit unit : Model.getNotShadowClasses()) {
			buffer.append("\n");
			buffer.append(unit.getQualifiedName());
			for (Measure measure : unit.getMetricSuite().getMeasures()) {
				buffer.append(";");
				buffer.append(measure.getValue());
			}
		}
		String projectLabel = Model.getConfiguration().getProjectName().toLowerCase().trim();
		projectLabel += Model.getConfiguration().getProjectVersion().toLowerCase().trim();
		Util.generateCSVFile(projectLabel + "-metrics-suite", buffer.toString());
	}
}
