package arcatch.dsl.rule.erosion.grammar;

import arcatch.dsl.compartment.grammar.Compartment;

public interface AERBody {

	public AEROnlyCan only(Compartment... compartment);

	public AERCommon compartiment(Compartment... compartment);

}