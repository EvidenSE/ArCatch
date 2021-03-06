package arcatch.metric.extractor.eh.code;

import java.util.HashSet;
import java.util.Set;

import arcatch.metric.Measure;
import arcatch.metric.Metric;
import arcatch.model.Model;
import arcatch.util.Util;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtThrow;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.visitor.filter.TypeFilter;

public class RCoExtractor extends AbstractProcessor<CtClass<?>> {

	@Override
	public void process(CtClass<?> element) {

		if (Util.isValid(element)) {
			String qualifiedName = element.getQualifiedName();
			Set<String> exceptions = new HashSet<>();
			for (CtThrow throwClause : element.getElements(new TypeFilter<CtThrow>(CtThrow.class))) {
				CtTypeReference<?> exceptionType = throwClause.getThrownExpression().getType();
				if (exceptionType != null)
					exceptions.add(exceptionType.getQualifiedName());
			}
			double numberOfDifferentRaisedExceptions = exceptions.size();

			Model.addMeasure(qualifiedName, new Measure(Metric.RCo, numberOfDifferentRaisedExceptions));
		}
	}

}
