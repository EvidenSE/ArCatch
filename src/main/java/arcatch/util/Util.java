package arcatch.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import spoon.reflect.declaration.CtType;
import spoon.reflect.reference.CtTypeReference;

public class Util {

	private final static String REPORT_PATH = "./arcatchreports";

	public static boolean isValid(CtType<?> element) {
		return element != null && (element.isClass() || element.isInterface()) && !element.isAnonymous() && !element.isLocalType();
	}

	public static int getDepthOfInheritanceTree(CtTypeReference<?> type) {
		if (type.isShadow() || type.getSuperclass() == null) {
			return 0;
		} else {
			return 1 + getDepthOfInheritanceTree(type.getSuperclass());
		}

	}

	public static int getNumberOfAllMethods(CtTypeReference<?> type) {
		int numberOfMethods = 0;
		if (type == null || type.isShadow()) {
			return 0;
		} else {
			if (type.getDeclaredExecutables() != null) {
				numberOfMethods = (int) type.getDeclaredExecutables().stream().filter(e -> !e.isConstructor()).count();
			}
			numberOfMethods += getNumberOfAllMethods(type.getSuperclass());
		}
		return numberOfMethods;
	}

	public static void generateCSVFile(String content) {
		File directory = new File(REPORT_PATH);
		if (!directory.exists()) {
			directory.mkdir();
		}

		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		dateFormat.setTimeZone(calendar.getTimeZone());
		StringBuffer reportName = new StringBuffer("arcatch-report-");
		reportName.append(dateFormat.format(calendar.getTime()));
		reportName.append(".csv");
		String fullReportName = reportName.toString();

		File reportFile = new File(directory, fullReportName);
		try {
			FileWriter fileWriter = new FileWriter(reportFile, true);
			PrintWriter printWriter = new PrintWriter(fileWriter);
			printWriter.append(content);
			printWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void generateCSVFile(String label, String content) {
		File directory = new File(REPORT_PATH);
		if (!directory.exists()) {
			directory.mkdir();
		}

		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		//calendar.getTimeZone()
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		StringBuffer reportName = new StringBuffer(label);
		reportName.append("-");
		reportName.append(dateFormat.format(calendar.getTime()));
		reportName.append(".csv");
		String fullReportName = reportName.toString();

		File reportFile = new File(directory, fullReportName);
		try {
			FileWriter fileWriter = new FileWriter(reportFile, true);
			PrintWriter printWriter = new PrintWriter(fileWriter);
			printWriter.append(content);
			printWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void generateJSONFile(String content) {
		File directory = new File(REPORT_PATH);
		if (!directory.exists()) {
			directory.mkdir();
		}

		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		dateFormat.setTimeZone(calendar.getTimeZone());
		StringBuffer reportName = new StringBuffer("arcatch-report-");
		reportName.append(dateFormat.format(calendar.getTime()));
		reportName.append(".json");
		String fullReportName = reportName.toString();

		File reportFile = new File(directory, fullReportName);
		try {
			FileWriter fileWriter = new FileWriter(reportFile, true);
			PrintWriter printWriter = new PrintWriter(fileWriter);
			printWriter.append(content);
			printWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
