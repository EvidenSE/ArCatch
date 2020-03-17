package arcatch.metric.extractor.eh.smell;

import arcatch.metric.Measure;
import arcatch.metric.Metric;
import arcatch.model.Model;
import arcatch.util.Util;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtCatch;
import spoon.reflect.code.CtVariableAccess;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.visitor.filter.TypeFilter;

public class NoCIExtractor extends AbstractProcessor<CtClass<?>> {

	@Override
	public void process(CtClass<?> element) {

		if (Util.isValid(element)) {
			double numberOfCatchAndIgnores = 0.0;
			String qualifiedName = element.getQualifiedName();
			for (CtCatch catchBlock : element.getElements(new TypeFilter<CtCatch>(CtCatch.class))) {
				String parameterName = catchBlock.getParameter().getSimpleName();
				boolean isCatchingAndIgnoring = true;
				for (CtVariableAccess<?> variableAccess : catchBlock.getElements(new TypeFilter<>(CtVariableAccess.class))) {
					if (parameterName.equals(variableAccess.getVariable().getSimpleName())) {
						isCatchingAndIgnoring = false;
					}
				}
				if (isCatchingAndIgnoring) {
					numberOfCatchAndIgnores++;
				}
			}
			Model.addMeasure(qualifiedName, new Measure(Metric.NoCI, numberOfCatchAndIgnores));
		}
	}
}
