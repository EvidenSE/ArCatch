package arcatch.util;

import java.util.ArrayList;
import java.util.List;

public class Matrix {

	private String label;

	private List<String> rowLabels = new ArrayList<>();

	private List<String> columnLabels = new ArrayList<>();

	private double data[][] = null;

	private double[] rowAverages = null;

	private double[] columnAverages = null;

	private double[] rowOccurrences = null;

	private double[] columnOccurrences = null;

	private int rows;

	private int columns;

	private double averageDivisor;

	public Matrix(String label, int rows, int columns) {
		this.label = label;
		this.data = new double[rows][columns];
		this.rowAverages = new double[rows];
		this.columnAverages = new double[columns];
		this.rowOccurrences = new double[rows];
		this.columnOccurrences = new double[columns];
		this.rows = rows;
		this.columns = columns;
	}

	public Matrix(int rows, int columns) {
		this("", rows, columns);
	}

	public Matrix(int size) {
		this(size, size);
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}

	public double getAverageDivisor() {
		return averageDivisor;
	}

	public void setAverageDivisor(double averageDivisor) {
		this.averageDivisor = averageDivisor;
	}

	public String getRowLabel(int row) {
		return this.rowLabels.get(row);
	}

	public String getColumnLabel(int column) {
		return this.columnLabels.get(column);
	}

	public int getRowIndex(String label) {
		for (int index = 0; index < this.rowLabels.size(); index++) {
			if (this.rowLabels.get(index).equals(label)) {
				return index;
			}
		}
		return -1;
	}

	public int getColumnIndex(String label) {
		for (int index = 0; index < this.columnLabels.size(); index++) {
			if (this.columnLabels.get(index).equals(label)) {
				return index;
			}
		}
		return -1;
	}

	public double getData(int row, int column) {
		return this.data[row][column];
	}

	public void addData(String rowLabel, String columnLabel, double value) {
		this.data[getRow(rowLabel)][getColumn(columnLabel)] += value;
	}

	private int getRow(String key) {
		if (!this.rowLabels.contains(key)) {
			this.rowLabels.add(key);
		}

		for (int index = 0; index < this.rowLabels.size(); index++) {
			if (this.rowLabels.get(index).equals(key)) {
				return index;
			}
		}
		return -1;
	}

	private int getColumn(String key) {

		if (!this.columnLabels.contains(key)) {
			this.columnLabels.add(key);
		}

		for (int index = 0; index < this.columnLabels.size(); index++) {
			if (this.columnLabels.get(index).equals(key)) {
				return index;
			}
		}
		return -1;
	}

	public void comput() {
		computRowAverages();
		computColumnAverages();
		computRowOccurrences();
		computColumnOccurrences();
	}

	private void computRowAverages() {
		for (int r = 0; r < this.rows && r < this.rowLabels.size(); r++) {
			double sum = 0;
			for (int c = 0; c < this.columns && c < this.columnLabels.size(); c++) {
				sum += data[r][c];
			}
			double divisor = (averageDivisor == 0.0) ? (this.columnLabels.size()) : (averageDivisor);
			this.rowAverages[r] = sum / divisor;
		}
	}

	public double getRowAverage(String label) {
		return this.rowAverages[getRow(label)];
	}

	public double getRowAverage(int index) {
		return this.rowAverages[index];
	}

	private void computColumnAverages() {
		for (int c = 0; c < this.columns && c < this.columnLabels.size(); c++) {
			double sum = 0;
			for (int r = 0; r < this.rows && r < this.rowLabels.size(); r++) {
				sum += data[r][c];
			}
			this.columnAverages[c] = sum / this.rowLabels.size();
		}
	}

	public double getColumnAverage(String label) {
		return this.columnAverages[getRow(label)];
	}

	public double getColumnAverage(int index) {
		return this.columnAverages[index];
	}

	private void computRowOccurrences() {
		for (int r = 0; r < this.rows && r < this.rowLabels.size(); r++) {
			double sum = 0;
			for (int c = 0; c < this.columns && c < this.columnLabels.size(); c++) {
				if (data[r][c] > 0.0) {
					sum++;
				}
			}
			this.rowOccurrences[r] = sum;
		}
	}

	public double getRowOccurrence(String label) {
		return this.rowOccurrences[getRow(label)];
	}

	public double getRowOccurrence(int index) {
		return this.rowOccurrences[index];
	}

	private void computColumnOccurrences() {
		for (int c = 0; c < this.columns && c < this.columnLabels.size(); c++) {
			double sum = 0;
			for (int r = 0; r < this.rows && r < this.rowLabels.size(); r++) {
				if (data[r][c] > 0.0) {
					sum++;
				}
			}
			this.columnOccurrences[c] = sum;
		}
	}

	public double getColumnOccurrence(String label) {
		return this.columnOccurrences[getRow(label)];
	}

	public double getColumnOccurrence(int index) {
		return this.columnOccurrences[index];
	}

	public String toCSV() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(this.label);
		buffer.append(";");
		for (int c = 0; c < this.columns && c < this.columnLabels.size(); c++) {
			buffer.append(this.columnLabels.get(c));
			buffer.append(";");
		}
		buffer.append("Ocurrences");
		buffer.append(";");
		buffer.append("Class DeF");
		buffer.append("\n");

		for (int r = 0; r < this.rows && r < this.rowLabels.size(); r++) {
			buffer.append(this.rowLabels.get(r));
			buffer.append(";");
			for (int c = 0; c < this.columns && c < this.columnLabels.size(); c++) {
				buffer.append(this.data[r][c]);
				buffer.append(";");
			}
			buffer.append(this.rowOccurrences[r]);
			buffer.append(";");
			buffer.append(this.rowAverages[r]);
			buffer.append("\n");
		}

		buffer.append("Ocurrences");
		for (int c = 0; c < this.columns && c < this.columnLabels.size(); c++) {
			buffer.append(";");
			buffer.append(this.columnOccurrences[c]);
		}
		buffer.append("\n");
		buffer.append("Rule DeF");
		for (int c = 0; c < this.columns && c < this.columnLabels.size(); c++) {
			buffer.append(";");
			buffer.append(this.columnAverages[c]);
		}
		return buffer.toString();
	}
}