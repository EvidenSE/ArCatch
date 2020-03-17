package arcatch.dsl.rule.erosion.grammar;

import arcatch.dsl.compartment.grammar.Compartment;

public interface AEROnlyCan {

	public AEREnd canRaise(Compartment... exceptionals);

	public AEREnd canReraise(Compartment... exceptionals);

	public AERTo canRemap(Compartment... exceptionals);

	public AERToOrEnd canSignal(Compartment... exceptionals);

	public AEREnd canHandle(Compartment... exceptionals);

	public AEREnd canCall(Compartment... normals);

	public AEREnd canCreate(Compartment... normals);

	public AEREnd canExtend(Compartment... normals);
	
	public AEREnd canImplement(Compartment... normals);
}