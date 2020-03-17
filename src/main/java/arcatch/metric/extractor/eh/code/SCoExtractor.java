package arcatch.metric.extractor.eh.code;

import java.util.HashSet;
import java.util.Set;

import arcatch.metric.Measure;
import arcatch.metric.Metric;
import arcatch.model.Model;
import arcatch.util.Util;
import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.reference.CtTypeReference;

public class SCoExtractor extends AbstractProcessor<CtClass<?>> {

	@Override
	public void process(CtClass<?> element) {
		if (Util.isValid(element)) {
			String qualifiedName = element.getQualifiedName();
			Set<String> exceptions = new HashSet<>();
			for (CtMethod<?> method : element.getMethods()) {
				for (CtTypeReference<?> exceptionType : method.getThrownTypes()) {
					if (exceptionType != null)
						exceptions.add(exceptionType.getQualifiedName());
				}
			}
			double numberOfDifferentSignaledExceptions = exceptions.size();

			Model.addMeasure(qualifiedName, new Measure(Metric.SCo, numberOfDifferentSignaledExceptions));
		}
	}

}
