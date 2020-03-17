package arcatch.dsl.rule.erosion.grammar;

import arcatch.dsl.compartment.grammar.Compartment;

public interface AERMust {

	public AEREnd mustRaise(Compartment... exceptionals);

	public AEREnd mustReraise(Compartment... exceptionals);

	public AERTo mustRemap(Compartment... exceptionals);

	public AERToOrEnd mustSignal(Compartment... exceptionals);

	public AEREnd mustHandle(Compartment... exceptionals);

	public AEREnd mustCall(Compartment... normals);

	public AEREnd mustCreate(Compartment... normals);

	public AEREnd mustExtend(Compartment... normals);
	
	public AEREnd mustImplement(Compartment... normals);

}
