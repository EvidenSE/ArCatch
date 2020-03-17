package arcatch.dsl.rule.erosion.grammar;

import arcatch.dsl.compartment.grammar.Compartment;

public interface AERCanNot {

	public AEREnd cannotRaise(Compartment... exceptionals);

	public AEREnd cannotReraise(Compartment... exceptionals);

	public AERTo cannotRemap(Compartment... exceptionals);

	public AERToOrEnd cannotSignal(Compartment... exceptionals);

	public AEREnd cannotHandle(Compartment... exceptionals);

	public AEREnd cannotCall(Compartment... normals);

	public AEREnd cannotCreate(Compartment... normals);

	public AEREnd cannotExtend(Compartment... normals);
	
	public AEREnd cannotImplement(Compartment... normals);
}
