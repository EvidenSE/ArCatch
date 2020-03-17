package arcatch.report;

import arcatch.dsl.rule.common.DesignRule;
import arcatch.model.Model;
import arcatch.util.Util;

public class ConformanceOfRulesReporter implements Reporter {

	@Override
	public void print() {
		StringBuffer buffer = new StringBuffer("Rule Id;Rule Label;Criticality Label;Criticality Value;Description;Checked;Passing\n");
		for (DesignRule rule : Model.getRules()) {
			buffer.append(rule.getId());
			buffer.append(";");
			buffer.append(rule.getLabel());
			buffer.append(";");
			buffer.append(rule.getCriticality().getLabel());
			buffer.append(";");
			buffer.append(rule.getCriticality().getValue());
			buffer.append(";");
			buffer.append(rule.getDescription().trim());
			buffer.append(";");
			buffer.append(rule.isChecked());
			buffer.append(";");
			buffer.append(rule.isPassing());
			buffer.append("\n");
		}
		String projectLabel = Model.getConfiguration().getProjectName().toLowerCase().trim();
		projectLabel += Model.getConfiguration().getProjectVersion().toLowerCase().trim();
		Util.generateCSVFile(projectLabel + "-conformance-of-rules", buffer.toString());
	}
}