package arcatch.metric.extractor.oo.code;

import arcatch.metric.Measure;
import arcatch.metric.Metric;
import arcatch.model.Model;
import arcatch.util.Util;
import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;

public class LMLoCExtractor extends AbstractProcessor<CtClass<?>> {

	@Override
	public void process(CtClass<?> element) {

		if (Util.isValid(element)) {
			String qualifiedName = element.getQualifiedName();
			double longetMethod = 0.0;

			if (!element.isInterface()) {
				for (CtMethod<?> method : element.getMethods()) {
					if (!method.isAbstract()) {
						if (method.getBody() != null && method.getBody().getStatements() != null
								&& method.getBody().getStatements().size() > 0) {
							int startLine = method.getBody().getPosition().getLine();
							int endLine = method.getBody().getPosition().getEndLine();
							if (longetMethod < (endLine - startLine)) {
								longetMethod = endLine - startLine;
							}
						}
					}
				}
			}
			Model.addMeasure(qualifiedName, new Measure(Metric.LMLoC, longetMethod));
		}
	}
}
