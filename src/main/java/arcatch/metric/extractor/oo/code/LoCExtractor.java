package arcatch.metric.extractor.oo.code;

import arcatch.metric.Measure;
import arcatch.metric.Metric;
import arcatch.model.Model;
import arcatch.util.Util;
import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtClass;

public class LoCExtractor extends AbstractProcessor<CtClass<?>> {

	@Override
	public void process(CtClass<?> element) {

		if (Util.isValid(element)) {
			String qualifiedName = element.getQualifiedName();
			int startLine = element.getPosition().getLine();
			int endLine = element.getPosition().getEndLine();
			double classLoC = ((endLine - startLine) == 0) ? (1) : ((endLine - startLine) - 1);
			Model.addMeasure(qualifiedName, new Measure(Metric.LoC, classLoC));
		}
	}
}