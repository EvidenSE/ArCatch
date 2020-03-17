package arcatch.metric.extractor.eh.smell;

import arcatch.metric.Measure;
import arcatch.metric.Metric;
import arcatch.model.Model;
import arcatch.util.Util;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtCatch;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.visitor.filter.TypeFilter;

public class NoGHExtractor extends AbstractProcessor<CtClass<?>> {

	@Override
	public void process(CtClass<?> element) {

		if (Util.isValid(element)) {
			String qualifiedName = element.getQualifiedName();
			double numberOfGenericHandlings = 0.0;

			for (CtCatch catchBlock : element.getElements(new TypeFilter<CtCatch>(CtCatch.class))) {
				if (catchBlock.getParameter().getMultiTypes() != null) {
					for (CtTypeReference<?> exceptionType : catchBlock.getParameter().getMultiTypes()) {
						if (isGeneral(exceptionType)) {
							numberOfGenericHandlings++;
						}
					}
				} else if (isGeneral(catchBlock.getParameter().getType())) {
					numberOfGenericHandlings++;
				}

			}
			Model.addMeasure(qualifiedName, new Measure(Metric.NoGH, numberOfGenericHandlings));
		}
	}

	private boolean isGeneral(CtTypeReference<?> exceptionType) {
		return exceptionType != null && (exceptionType.getQualifiedName().equals("java.lang.Throwable")
				|| exceptionType.getQualifiedName().equals("java.lang.Exception")
				|| exceptionType.getQualifiedName().equals("java.lang.Error")
				|| exceptionType.getQualifiedName().equals("java.lang.RuntimeException"));
	}

}
