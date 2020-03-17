package arcatch.metric.extractor.eh.code;

import arcatch.metric.Measure;
import arcatch.metric.Metric;
import arcatch.model.Model;
import arcatch.util.Util;
import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;

public class NoSExtractor extends AbstractProcessor<CtClass<?>> {

	@Override
	public void process(CtClass<?> element) {
		if (Util.isValid(element)) {
			String qualifiedName = element.getQualifiedName();
			double numberOfSignaling = 0.0;

			for (CtMethod<?> method : element.getMethods()) {
				numberOfSignaling += method.getThrownTypes().size();
			}
			Model.addMeasure(qualifiedName, new Measure(Metric.NoS, numberOfSignaling));
		}
	}
}
