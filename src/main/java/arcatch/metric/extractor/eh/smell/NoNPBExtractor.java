package arcatch.metric.extractor.eh.smell;

import arcatch.metric.Measure;
import arcatch.metric.Metric;
import arcatch.model.Model;
import arcatch.util.Util;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtTry;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.visitor.filter.TypeFilter;

public class NoNPBExtractor extends AbstractProcessor<CtClass<?>> {

	@Override
	public void process(CtClass<?> element) {

		if (Util.isValid(element)) {
			String qualifiedName = element.getQualifiedName();
			double numberOfNestedProtectedBlocks = 0.0;

			for (CtTry tryClause : element.getElements(new TypeFilter<CtTry>(CtTry.class))) {
				if (!tryClause.getElements(new TypeFilter<CtTry>(CtTry.class)).isEmpty()) {
					numberOfNestedProtectedBlocks++;
				}
			}
			Model.addMeasure(qualifiedName, new Measure(Metric.NoNPB, numberOfNestedProtectedBlocks));
		}
	}
}
