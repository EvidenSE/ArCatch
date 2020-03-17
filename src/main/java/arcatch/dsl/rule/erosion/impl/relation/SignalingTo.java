package arcatch.dsl.rule.erosion.impl.relation;

import java.util.Set;

import arcatch.model.Operation;
import arcatch.model.Unit;

public class SignalingTo {

	private Operation fromOperation;

	private Set<Unit> exceptions;

	private Operation toOperation;

	public SignalingTo(Operation fromOperation, Set<Unit> exceptions, Operation toOperation) {
		super();
		this.fromOperation = fromOperation;
		this.exceptions = exceptions;
		this.toOperation = toOperation;
	}

	public Operation getFromOperation() {
		return fromOperation;
	}

	public void setFromOperation(Operation fromOperation) {
		this.fromOperation = fromOperation;
	}

	public Set<Unit> getExceptions() {
		return exceptions;
	}

	public void setExceptions(Set<Unit> exceptions) {
		this.exceptions = exceptions;
	}

	public void addException(Unit exception) {
		this.exceptions.add(exception);
	}

	public Operation getToOperation() {
		return toOperation;
	}

	public void setToOperation(Operation toOperation) {
		this.toOperation = toOperation;
	}

}
