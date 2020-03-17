package arcatch.model.impl;

import arcatch.model.Position;

public class PositionImpl implements Position {

	private int begin;

	private int end;

	public PositionImpl(int begin, int end) {
		super();
		this.begin = begin;
		this.end = end;
	}

	public PositionImpl() {
		this(0, 0);
	}

	@Override
	public void setBegin(int value) {
		this.begin = value;
	}

	@Override
	public int getBegin() {
		return this.begin;
	}

	@Override
	public void setEnd(int value) {
		this.end = value;
	}

	@Override
	public int getEnd() {
		return this.end;
	}
}
