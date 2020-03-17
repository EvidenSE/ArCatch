package arcatch.dsl.rule.erosion.impl.relation;

import arcatch.dsl.rule.erosion.impl.kind.RuleKing;

public class TernaryRelation<L, M, R> {

	private RuleKing kind;

	private L left;

	private M middle;

	private R right;

	public TernaryRelation(RuleKing kind, L left, M middle, R right) {
		super();
		this.kind = kind;
		this.left = left;
		this.middle = middle;
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

	public M getMiddle() {
		return middle;
	}

	public void setMiddle(M middle) {
		this.middle = middle;
	}

	public R getRight() {
		return right;
	}

	public void setRight(R right) {
		this.right = right;
	}

}
