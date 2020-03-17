package arcatch.dsl.rule.erosion.grammar;

import arcatch.dsl.compartment.grammar.Compartment;

public interface AERCanOnly {

	public AEREnd canRaiseOnly(Compartment... exceptionals);

	public AEREnd canReraiseOnly(Compartment... exceptionals);

	public AERTo canRemapOnly(Compartment... exceptionals);

	public AERToOrEnd canSignalOnly(Compartment... exceptionals);

	public AEREnd canHandleOnly(Compartment... exceptionals);

	public AEREnd canCallOnly(Compartment... normals);

	public AEREnd canCreateOnly(Compartment... normals);

	public AEREnd canExtendOnly(Compartment... normals);
	
	public AEREnd canImplementOnly(Compartment... normals);

}
