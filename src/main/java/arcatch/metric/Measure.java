package arcatch.metric;

public class Measure {

	private Metric metric;

	private double value;

	public Measure(Metric metric, double value) {
		this.metric = metric;
		this.value = value;
	}

	public Metric getMetric() {
		return metric;
	}

	public double getValue() {
		return value;
	}

}
