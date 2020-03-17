package arcatch.metric.extractor.oo.ck;

import arcatch.metric.Measure;
import arcatch.metric.Metric;
import arcatch.model.Model;
import arcatch.util.Util;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.BinaryOperatorKind;
import spoon.reflect.code.CtBinaryOperator;
import spoon.reflect.code.CtBreak;
import spoon.reflect.code.CtCase;
import spoon.reflect.code.CtContinue;
import spoon.reflect.code.CtDo;
import spoon.reflect.code.CtFor;
import spoon.reflect.code.CtForEach;
import spoon.reflect.code.CtIf;
import spoon.reflect.code.CtReturn;
import spoon.reflect.code.CtSwitch;
import spoon.reflect.code.CtWhile;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.visitor.filter.TypeFilter;

public class WMCExtractor extends AbstractProcessor<CtClass<?>> {

	@Override
	public void process(CtClass<?> element) {

		if (Util.isValid(element)) {
			String qualifiedName = element.getQualifiedName();
			double weightedMethodsPerClass = 0.0;

			for (CtMethod<?> method : element.getMethods()) {
				weightedMethodsPerClass += method.getElements(new TypeFilter<CtIf>(CtIf.class)).size();

				for (CtIf ifstmt : method.getElements(new TypeFilter<CtIf>(CtIf.class))) {
					if (ifstmt.getElseStatement() != null) {
						weightedMethodsPerClass++;
					}
				}

				weightedMethodsPerClass += method.getElements(new TypeFilter<CtSwitch<?>>(CtSwitch.class)).size();

				weightedMethodsPerClass += method.getElements(new TypeFilter<CtCase<?>>(CtCase.class)).size();

				weightedMethodsPerClass += method.getElements(new TypeFilter<CtFor>(CtFor.class)).size();

				weightedMethodsPerClass += method.getElements(new TypeFilter<CtForEach>(CtForEach.class)).size();

				weightedMethodsPerClass += method.getElements(new TypeFilter<CtWhile>(CtWhile.class)).size();

				weightedMethodsPerClass += method.getElements(new TypeFilter<CtDo>(CtDo.class)).size();

				weightedMethodsPerClass += method.getElements(new TypeFilter<CtBreak>(CtBreak.class)).size();

				weightedMethodsPerClass += method.getElements(new TypeFilter<CtContinue>(CtContinue.class)).size();

				for (CtBinaryOperator<?> operator : method.getElements(new TypeFilter<CtBinaryOperator<?>>(CtBinaryOperator.class))) {
					if (operator.getKind() == BinaryOperatorKind.AND || operator.getKind() == BinaryOperatorKind.OR) {
						weightedMethodsPerClass++;
					}
				}
				weightedMethodsPerClass += method.getElements(new TypeFilter<CtReturn<?>>(CtReturn.class)).size();
			}

			Model.addMeasure(qualifiedName, new Measure(Metric.WMC, weightedMethodsPerClass));
		}
	}
}
