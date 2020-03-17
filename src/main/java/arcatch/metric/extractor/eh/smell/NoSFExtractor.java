package arcatch.metric.extractor.eh.smell;

import arcatch.metric.Measure;
import arcatch.metric.Metric;
import arcatch.model.Model;
import arcatch.util.Util;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtTry;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.visitor.filter.TypeFilter;

public class NoSFExtractor extends AbstractProcessor<CtClass<?>> {

	@Override
	public void process(CtClass<?> element) {

		if (Util.isValid(element)) {
			String qualifiedName = element.getQualifiedName();
			double numberOfSignalingsInFinallyBlocks = 0.0;
			for (CtTry tryClause : element.getElements(new TypeFilter<CtTry>(CtTry.class))) {
				if (tryClause.getFinalizer() != null) {
					TypeFilter<CtInvocation<?>> invocationFilter = new TypeFilter<CtInvocation<?>>(CtInvocation.class);
					for (CtInvocation<?> invocation : tryClause.getElements(invocationFilter)) {
						if (invocation.getTarget() != null) {
							if (invocation.getExecutable() != null && invocation.getExecutable().getDeclaration() != null) {
								if (!invocation.getExecutable().getDeclaration().getThrownTypes().isEmpty()) {
									numberOfSignalingsInFinallyBlocks++;
								}
							}
						}
					}
				}
			}
			Model.addMeasure(qualifiedName, new Measure(Metric.NoSF, numberOfSignalingsInFinallyBlocks));
		}
	}
}
