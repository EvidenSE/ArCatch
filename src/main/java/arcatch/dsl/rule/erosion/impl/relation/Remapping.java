package arcatch.dsl.rule.erosion.impl.relation;

import arcatch.model.Unit;

public class Remapping {

	private Unit fromException;

	private Unit toException;

	public Remapping(Unit fromException, Unit toException) {
		this.fromException = fromException;
		this.toException = toException;
	}

	public Unit getFromException() {
		return fromException;
	}

	public void setFromException(Unit exception) {
		this.fromException = exception;
	}

	public Unit getToException() {
		return toException;
	}

	public void setToException(Unit exception) {
		this.toException = exception;
	}

}