package arcatch.dsl.rule.erosion.impl.relation;

import arcatch.dsl.rule.erosion.impl.kind.RuleKing;

public class BinaryRelation<L, R> {

	private RuleKing kind;

	private L left;

	private R right;

	public BinaryRelation(RuleKing kind, L left, R right) {
		super();
		this.kind = kind;
		this.left = left;
		this.right = right;
	}

	public RuleKing getKind() {
		return kind;
	}

	public void setKind(RuleKing kind) {
		this.kind = kind;
	}

	public L getLeft() {
		return left;
	}

	public void setLeft(L left) {
		this.left = left;
	}

	public R getRight() {
		return right;
	}

	public void setRight(R right) {
		this.right = right;
	}

}
