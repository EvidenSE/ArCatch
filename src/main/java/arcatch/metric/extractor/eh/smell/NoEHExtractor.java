package arcatch.metric.extractor.eh.smell;

import arcatch.metric.Measure;
import arcatch.metric.Metric;
import arcatch.model.Model;
import arcatch.util.Util;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtCatch;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.visitor.filter.TypeFilter;

public class NoEHExtractor extends AbstractProcessor<CtClass<?>> {

	@Override
	public void process(CtClass<?> element) {

		if (Util.isValid(element)) {
			String qualifiedName = element.getQualifiedName();

			double numberOfEmptyHandlers = 0.0;

			for (CtCatch ctCatch : element.getElements(new TypeFilter<CtCatch>(CtCatch.class))) {
				if (ctCatch != null && ctCatch.getBody().getStatements().isEmpty()) {
					numberOfEmptyHandlers++;
				}
			}
			Model.addMeasure(qualifiedName, new Measure(Metric.NoEH, numberOfEmptyHandlers));
		}
	}
}
