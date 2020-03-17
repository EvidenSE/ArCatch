package arcatch.metric.extractor.eh.smell;

import arcatch.metric.Measure;
import arcatch.metric.Metric;
import arcatch.model.Model;
import arcatch.util.Util;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtCatch;
import spoon.reflect.code.CtReturn;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.visitor.filter.TypeFilter;

public class NoCRNExtractor extends AbstractProcessor<CtClass<?>> {

	@Override
	public void process(CtClass<?> element) {

		if (Util.isValid(element)) {
			String qualifiedName = element.getQualifiedName();
			double numberOfCatchAnaReturnNull = 0.0;

			for (CtCatch catchBlock : element.getElements(new TypeFilter<CtCatch>(CtCatch.class))) {
				for (CtReturn<?> returnType : catchBlock.getElements(new TypeFilter<CtReturn<?>>(CtReturn.class))) {
					if (returnType.getReturnedExpression() != null) {
						String expression = returnType.getReturnedExpression().toString();
						if (expression.equals("null")) {
							numberOfCatchAnaReturnNull++;
						}
					}
				}
			}
			Model.addMeasure(qualifiedName, new Measure(Metric.NoCRN, numberOfCatchAnaReturnNull));
		}
	}
}
