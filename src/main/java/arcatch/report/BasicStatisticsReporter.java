package arcatch.report;

import arcatch.metric.Metric;
import arcatch.model.Model;
import arcatch.util.Util;

public class BasicStatisticsReporter implements Reporter {

	@Override
	public void print() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Project Name");
		buffer.append(";");
		buffer.append(Model.getConfiguration().getProjectName());
		buffer.append("\n");
			
		buffer.append("Project Source Path");
		buffer.append(";");
		buffer.append(Model.getConfiguration().getProjectPath());
		buffer.append("\n");
		
		buffer.append("Number of Units");
		buffer.append(";");
		buffer.append(Model.getNotShadowUnits().size());
		buffer.append("\n");
		
		buffer.append("Number of Exceptions");
		buffer.append(";");
		buffer.append(Model.getNotShadowExceptions().size());
		buffer.append("\n");
	
		buffer.append("Lines of Code");
		buffer.append(";");
		buffer.append(Model.getMetricValueAtProjectLevel(Metric.LoC));
		buffer.append("\n");
		
		buffer.append("Catch Block Lines of Code");
		buffer.append(";");
		buffer.append(Model.getMetricValueAtProjectLevel(Metric.CLoC));
		buffer.append("\n");

		buffer.append("Overall Number of Rules");
		buffer.append(";");
		buffer.append(Model.getRules().size());
		buffer.append("\n");
		
		buffer.append("Overall Number of Passing Rules");
		buffer.append(";");
		buffer.append(Model.getPassingRules().size());
		buffer.append("\n");
		
		buffer.append("Number of Anti-Drift Rules");
		buffer.append(";");
		buffer.append(Model.getAntiDriftRules().size());
		buffer.append("\n");
		
		buffer.append("Number of Anit-Drift Passing Rules");
		buffer.append(";");
		buffer.append(Model.getPassingAntiDriftRules().size());
		buffer.append("\n");
		
		buffer.append("Number of Anti-Erosion Rules");
		buffer.append(";");
		buffer.append(Model.getAntiErosionRules().size());
		buffer.append("\n");
		
		buffer.append("Number of Anti-Erosion Passing Rules");
		buffer.append(";");
		buffer.append(Model.getPassingAntiErosionRules().size());
		buffer.append("\n");
		String projectLabel = Model.getConfiguration().getProjectName().toLowerCase().trim();
		projectLabel += Model.getConfiguration().getProjectVersion().toLowerCase().trim();
		Util.generateCSVFile(projectLabel + "-basic-statistics", buffer.toString());
	}
}
