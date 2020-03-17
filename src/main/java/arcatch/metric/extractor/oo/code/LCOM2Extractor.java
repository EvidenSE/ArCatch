package arcatch.metric.extractor.oo.code;

import arcatch.metric.Measure;
import arcatch.metric.Metric;
import arcatch.model.Model;
import arcatch.util.Util;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtFieldAccess;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtField;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.visitor.filter.TypeFilter;

public class LCOM2Extractor extends AbstractProcessor<CtClass<?>> {

	@Override
	public void process(CtClass<?> element) {

		if (Util.isValid(element)) {

			double lackOfCohesionOfMethods = 1.0;
			double numberOfMethods = element.getMethods().size();
			double numberOfAttributes = element.getFields().size();
			double summation = 0.0;
			String qualifiedName = element.getQualifiedName();

			if (numberOfMethods > 0 && numberOfAttributes > 0) {
				for (CtField<?> field : element.getFields()) {
					summation += numberOfMethodsAccessingField(element, field.getReference().getSimpleName());
				}
				lackOfCohesionOfMethods = 1.0 - (summation / (numberOfMethods * numberOfAttributes));
			}
			Model.addMeasure(qualifiedName, new Measure(Metric.LCOM2, lackOfCohesionOfMethods));
		}

	}

	private int numberOfMethodsAccessingField(CtClass<?> element, String fieldName) {
		int numberOfMethods = 0;
		for (CtMethod<?> method : element.getMethods()) {
			for (CtFieldAccess<?> access : method.getElements(new TypeFilter<CtFieldAccess<?>>(CtFieldAccess.class))) {
				if (fieldName.equals(access.getVariable().getSimpleName())) {
					numberOfMethods++;
					break;
				}
			}
		}
		return numberOfMethods;
	}
}
