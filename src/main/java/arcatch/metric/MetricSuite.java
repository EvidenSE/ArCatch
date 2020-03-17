package arcatch.metric;

import java.util.Collection;
import java.util.Vector;

import arcatch.model.Unit;

public class MetricSuite {

	private Unit owner;

	private Collection<Measure> measures;

	public MetricSuite(Unit owner) {
		this.owner = owner;
		this.measures = new Vector<>();
	}

	public Unit getOwner() {
		return owner;
	}

	public void setOwner(Unit owner) {
		this.owner = owner;
	}

	public Collection<Measure> getMeasures() {
		return measures;
	}

	public void addMeasure(Measure measure) {
		if (measures.stream().filter(m -> m.getMetric().getShortName().equals(measure.getMetric().getShortName())).count() == 0) {
			this.measures.add(measure);
		}
	}

}
