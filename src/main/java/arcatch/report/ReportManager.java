package arcatch.report;

import java.util.HashSet;
import java.util.Set;

public class ReportManager {

	private static Set<Reporter> reporters = new HashSet<>();

	public static boolean isEmpty() {
		return reporters.isEmpty();
	}

	public static void clear() {
		reporters.clear();
	}

	public static void add(Reporter reporter) {
		reporters.add(reporter);
	}

	public static void generateReports() {
		for (Reporter reporter : reporters) {
			reporter.print();
		}
	}
}
