package arcatch.metric.extractor.eh.code;

import arcatch.metric.Measure;
import arcatch.metric.Metric;
import arcatch.model.Model;
import arcatch.util.Util;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtThrow;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.visitor.filter.TypeFilter;

public class NoRExtractor extends AbstractProcessor<CtClass<?>> {

	@Override
	public void process(CtClass<?> element) {
		if (Util.isValid(element)) {
			String qualifiedName = element.getQualifiedName();
			double numberOfRaising = (double) element.getElements(new TypeFilter<CtThrow>(CtThrow.class)).size();
			Model.addMeasure(qualifiedName, new Measure(Metric.NoR, numberOfRaising));
		}
	}
}
