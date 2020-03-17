package arcatch.metric.extractor.eh.smell;

import arcatch.metric.Measure;
import arcatch.metric.Metric;
import arcatch.model.Model;
import arcatch.util.Util;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtCatch;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.visitor.filter.TypeFilter;

public class NoCAExtractor extends AbstractProcessor<CtClass<?>> {

	@Override
	public void process(CtClass<?> element) {

		if (Util.isValid(element)) {
			String qualifiedName = element.getQualifiedName();
			double numberOfCatchesAndAbort = 0.0;
			TypeFilter<CtInvocation<?>> invocationFilter = new TypeFilter<CtInvocation<?>>(CtInvocation.class);

			for (CtCatch catchBlock : element.getElements(new TypeFilter<CtCatch>(CtCatch.class))) {
				for (CtInvocation<?> invocation : catchBlock.getElements(invocationFilter)) {
					if (invocation.getTarget() != null && invocation.getExecutable() != null) {
						String targetClass = invocation.getTarget().toString();
						String calledMethodSignature = invocation.getExecutable().getSignature();
						if (targetClass.equals("java.lang.System") && calledMethodSignature.equals("exit(int)")) {
							numberOfCatchesAndAbort++;
						}
					}
				}
			}

			Model.addMeasure(qualifiedName, new Measure(Metric.NoCA, numberOfCatchesAndAbort));
		}
	}
}
