package arcatch.dsl.rule.common;

public enum Criticality {

	HIGH(4, "HIGH"), MEDIUM(3, "MEDIUM"), LOW(2, "LOW"), WARNING(1, "WARNING");

	private int value;

	private String label;

	Criticality(int value, String label) {
		this.value = value;
		this.label = label;
	}

	public int getValue() {
		return this.value;
	}

	public String getLabel() {
		return label;
	}
}