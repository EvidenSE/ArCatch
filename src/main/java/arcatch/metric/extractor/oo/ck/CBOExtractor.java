package arcatch.metric.extractor.oo.ck;

import arcatch.metric.Measure;
import arcatch.metric.Metric;
import arcatch.model.Model;
import arcatch.util.Util;
import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.reference.CtTypeReference;

public class CBOExtractor extends AbstractProcessor<CtClass<?>> {

	@Override
	public void process(CtClass<?> element) {

		if (Util.isValid(element)) {
			String qualifiedName = element.getQualifiedName();
			double couplingBetweenObjects = 0.0;
			for (CtTypeReference<?> referredType : element.getReferencedTypes()) {
				if (referredType != null && !referredType.isShadow()) {
					couplingBetweenObjects++;
				}
			}
			Model.addMeasure(qualifiedName, new Measure(Metric.CBO, couplingBetweenObjects));
		}
	}
}
