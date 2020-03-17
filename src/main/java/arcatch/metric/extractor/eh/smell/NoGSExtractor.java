package arcatch.metric.extractor.eh.smell;

import arcatch.metric.Measure;
import arcatch.metric.Metric;
import arcatch.model.Model;
import arcatch.util.Util;
import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.reference.CtTypeReference;

public class NoGSExtractor extends AbstractProcessor<CtClass<?>> {

	@Override
	public void process(CtClass<?> element) {

		if (Util.isValid(element)) {
			String qualifiedName = element.getQualifiedName();
			double numberOfGenericSignalings = 0.0;

			for (CtMethod<?> method : element.getMethods()) {
				for (CtTypeReference<?> exception : method.getThrownTypes()) {
					if (isGeneral(exception)) {
						numberOfGenericSignalings++;
					}
				}
			}
			Model.addMeasure(qualifiedName, new Measure(Metric.NoGS, numberOfGenericSignalings));
		}
	}

	private boolean isGeneral(CtTypeReference<?> exceptionType) {
		return exceptionType != null && (exceptionType.getQualifiedName().equals("java.lang.Throwable")
				|| exceptionType.getQualifiedName().equals("java.lang.Exception")
				|| exceptionType.getQualifiedName().equals("java.lang.Error")
				|| exceptionType.getQualifiedName().equals("java.lang.RuntimeException"));
	}

}
