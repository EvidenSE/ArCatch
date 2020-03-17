package arcatch.metric.extractor.oo.code;

import arcatch.metric.Measure;
import arcatch.metric.Metric;
import arcatch.model.Model;
import arcatch.util.Util;
import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtClass;

public class NoAExtractor extends AbstractProcessor<CtClass<?>> {

	@Override
	public void process(CtClass<?> element) {

		if (Util.isValid(element)) {
			String qualifiedName = element.getQualifiedName();
			double numberOfAttributes = element.getFields().size();
			Model.addMeasure(qualifiedName, new Measure(Metric.NoA, numberOfAttributes));
		}
	}
}
