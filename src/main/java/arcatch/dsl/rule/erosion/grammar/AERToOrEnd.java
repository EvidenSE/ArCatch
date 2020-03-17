package arcatch.dsl.rule.erosion.grammar;

import arcatch.dsl.compartment.grammar.Compartment;

public interface AERToOrEnd extends AEREnd {

	public AEREnd upTo(Compartment... normals);
}
