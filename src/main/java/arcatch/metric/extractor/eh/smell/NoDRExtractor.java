package arcatch.metric.extractor.eh.smell;

import arcatch.metric.Measure;
import arcatch.metric.Metric;
import arcatch.model.Model;
import arcatch.util.Util;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtCatch;
import spoon.reflect.code.CtConstructorCall;
import spoon.reflect.code.CtThrow;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.visitor.filter.TypeFilter;

public class NoDRExtractor extends AbstractProcessor<CtClass<?>> {

	@Override
	public void process(CtClass<?> element) {

		if (Util.isValid(element)) {
			double numberOfDestructiveRemappings = 0.0;
			String qualifiedName = element.getQualifiedName();
			for (CtCatch catchBlock : element.getElements(new TypeFilter<CtCatch>(CtCatch.class))) {
				for (CtTypeReference<?> exceptionType : catchBlock.getParameter().getMultiTypes()) {
					String caughtExceptionQualifiedName = exceptionType.getQualifiedName();
					for (CtThrow throwStmt : catchBlock.getElements(new TypeFilter<CtThrow>(CtThrow.class))) {
						CtConstructorCall<?> constructorCall = null;
						if (throwStmt.getThrownExpression() instanceof CtConstructorCall<?>) {
							constructorCall = (CtConstructorCall<?>) throwStmt.getThrownExpression();
							long count = 0;
							if (constructorCall != null && !constructorCall.getArguments().isEmpty()) {
								count = constructorCall.getArguments().stream()
										.filter(a -> a != null && a.getType() != null && a.getType().getQualifiedName().equals(caughtExceptionQualifiedName)).count();
							}
							if (count == 0) {
								numberOfDestructiveRemappings++;
							}
						}
					}
				}
			}
			Model.addMeasure(qualifiedName, new Measure(Metric.NoDR, numberOfDestructiveRemappings));
		}
	}
}
