package arcatch.metric.extractor.eh.code;

import arcatch.metric.Measure;
import arcatch.metric.Metric;
import arcatch.model.Model;
import arcatch.util.Util;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.visitor.filter.TypeFilter;

public class TFCExtractor extends AbstractProcessor<CtClass<?>> {

	@Override
	public void process(CtClass<?> element) {

		if (Util.isValid(element)) {
			String qualifiedName = element.getQualifiedName();
			double troublenessFactorPerClass = 0.0;
			TypeFilter<CtInvocation<?>> invocationFilter = new TypeFilter<CtInvocation<?>>(CtInvocation.class);

			for (CtInvocation<?> invocation : element.getElements(invocationFilter)) {
				if (invocation.getTarget() != null) {
					if (invocation.getExecutable() != null && invocation.getExecutable().getDeclaration() != null) {
						if (!invocation.getExecutable().getDeclaration().getThrownTypes().isEmpty()) {
							troublenessFactorPerClass++;
						}
					}
				}
			}
			Model.addMeasure(qualifiedName, new Measure(Metric.TFC, troublenessFactorPerClass));
		}
	}
}
