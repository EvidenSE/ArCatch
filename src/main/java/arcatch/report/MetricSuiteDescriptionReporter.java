package arcatch.report;

import arcatch.metric.Metric;
import arcatch.model.Model;
import arcatch.util.Util;

public class MetricSuiteDescriptionReporter implements Reporter {

	@Override
	public void print() {
		StringBuffer buffer = new StringBuffer("Short Name;Full Name\n");
		for (Metric metric : Metric.values()) {
			buffer.append(metric.getShortName());
			buffer.append(";");
			buffer.append(metric.getFullName());
			buffer.append("\n");
		}
		String projectLabel = Model.getConfiguration().getProjectName().toLowerCase().trim();
		projectLabel += Model.getConfiguration().getProjectVersion().toLowerCase().trim();
		Util.generateCSVFile(projectLabel + "-metric-suite-description", buffer.toString());
	}
}
