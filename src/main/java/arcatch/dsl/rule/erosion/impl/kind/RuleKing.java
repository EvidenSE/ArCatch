package arcatch.dsl.rule.erosion.impl.kind;

public enum RuleKing {

	Call("CALL"), Creation("CREATION"), Extension("EXTENSION"), Implementation("IMPLEMENTATION"), 
	Remapping("REMAPING"), Signaling("SIGNALING"), SignalingTo("SIGNALING_TO"), Raising("RAISING"), 
	Reraising("RERAISING"), Handling("HANDLING"), Drift("DRIFT");

	private String label;

	private RuleKing(String label) {
		this.label = label;
	}

	public String getLabel() {
		return this.label;
	}
}
