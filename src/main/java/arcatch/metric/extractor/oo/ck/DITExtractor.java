package arcatch.metric.extractor.oo.ck;

import arcatch.metric.Measure;
import arcatch.metric.Metric;
import arcatch.model.Model;
import arcatch.util.Util;
import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtClass;

public class DITExtractor extends AbstractProcessor<CtClass<?>> {

	@Override
	public void process(CtClass<?> element) {

		if (Util.isValid(element)) {
			String qualifiedName = element.getQualifiedName();
			double depthOfInheritanceTree = 1.0;
			depthOfInheritanceTree += Util.getDepthOfInheritanceTree(element.getReference());
			Model.addMeasure(qualifiedName, new Measure(Metric.DIT, depthOfInheritanceTree));
		}
	}
}