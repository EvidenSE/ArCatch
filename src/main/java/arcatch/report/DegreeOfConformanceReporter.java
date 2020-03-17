package arcatch.report;

import arcatch.dsl.rule.common.DesignRule;
import arcatch.model.Model;
import arcatch.util.Util;

public class DegreeOfConformanceReporter implements Reporter {

	@Override
	public void print() {
		double passingCriticalitySum = 0.0;
		double checkedCriticalitySum = 0.0;

		for (DesignRule rule : Model.getPassingRules()) {
			passingCriticalitySum += rule.getCriticality().getValue();
		}

		for (DesignRule rule : Model.getCheckedRules()) {
			checkedCriticalitySum += rule.getCriticality().getValue();
		}

		double overallConformance;
		
		if (checkedCriticalitySum > 0.0) {
			overallConformance = passingCriticalitySum / checkedCriticalitySum;
		} else {
			overallConformance = 1.0;
		}
		

		passingCriticalitySum = 0.0;
		checkedCriticalitySum = 0.0;

		for (DesignRule rule : Model.getPassingAntiErosionRules()) {
			passingCriticalitySum += rule.getCriticality().getValue();
		}

		for (DesignRule rule : Model.getCheckedAntiErosionRules()) {
			checkedCriticalitySum += rule.getCriticality().getValue();
		}

		double erosionConformance;

		if (checkedCriticalitySum > 0.0) {
			erosionConformance = passingCriticalitySum / checkedCriticalitySum;
		} else {
			erosionConformance = 1.0;
		}
		
		passingCriticalitySum = 0.0;
		checkedCriticalitySum = 0.0;

		for (DesignRule rule : Model.getPassingAntiDriftRules()) {
			passingCriticalitySum += rule.getCriticality().getValue();
		}

		for (DesignRule rule : Model.getCheckedAntiDriftRules()) {
			checkedCriticalitySum += rule.getCriticality().getValue();
		}

		double driftConformance;

		if (checkedCriticalitySum > 0.0) {
			driftConformance = passingCriticalitySum / checkedCriticalitySum;
		} else {
			driftConformance = 1.0;
		}
		
		StringBuffer buffer = new StringBuffer("Overall;Erosion;Drift\n");
		buffer.append(overallConformance);
		buffer.append(";");
		buffer.append(erosionConformance);
		buffer.append(";");
		buffer.append(driftConformance);
		String projectLabel = Model.getConfiguration().getProjectName().toLowerCase().trim();
		projectLabel += Model.getConfiguration().getProjectVersion().toLowerCase().trim();
		Util.generateCSVFile(projectLabel + "-degree-of-conformance", buffer.toString());
	}

}
