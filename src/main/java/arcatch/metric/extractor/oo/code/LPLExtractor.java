package arcatch.metric.extractor.oo.code;

import arcatch.metric.Measure;
import arcatch.metric.Metric;
import arcatch.model.Model;
import arcatch.util.Util;
import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;

public class LPLExtractor extends AbstractProcessor<CtClass<?>> {

	@Override
	public void process(CtClass<?> element) {

		if (Util.isValid(element)) {
			String qualifiedName = element.getQualifiedName();
			double longetParameterList = 0.0;

			for (CtMethod<?> method : element.getMethods()) {
				
				if (method.getParameters().size() > longetParameterList) {
					longetParameterList = method.getParameters().size();
				}
			}
			Model.addMeasure(qualifiedName, new Measure(Metric.LPL, longetParameterList));
		}
	}
}
