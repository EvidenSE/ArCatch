package arcatch.metric.extractor.eh.smell;

import arcatch.metric.Measure;
import arcatch.metric.Metric;
import arcatch.model.Model;
import arcatch.util.Util;
import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;

public class NoSKSExtractor extends AbstractProcessor<CtClass<?>> {

	@Override
	public void process(CtClass<?> element) {

		if (Util.isValid(element)) {
			String qualifiedName = element.getQualifiedName();
			double numberOfSignalingTheKitchenSink = 0.0;

			for (CtMethod<?> method : element.getMethods()) {
				if (method.getThrownTypes().size() > 1)
					numberOfSignalingTheKitchenSink++;
			}
			Model.addMeasure(qualifiedName, new Measure(Metric.NoSKS, numberOfSignalingTheKitchenSink));
		}
	}
}
