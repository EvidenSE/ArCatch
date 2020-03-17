package arcatch.metric.extractor.oo.code;

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
import spoon.reflect.visitor.filter.TypeFilter;

public class CCCExtractor extends AbstractProcessor<CtClass<?>> {

	@Override
	public void process(CtClass<?> element) {

		if (Util.isValid(element)) {
			String qualifiedName = element.getQualifiedName();
			double cyclomaticComplexity = element.getMethods().size();

			for (CtIf ifstmt : element.getElements(new TypeFilter<CtIf>(CtIf.class))) {
				cyclomaticComplexity++;
				if (ifstmt.getElseStatement() != null) {
					cyclomaticComplexity++;
				}
			}

			cyclomaticComplexity += element.getElements(new TypeFilter<CtSwitch<?>>(CtSwitch.class)).size();

			cyclomaticComplexity += element.getElements(new TypeFilter<CtCase<?>>(CtCase.class)).size();

			cyclomaticComplexity += element.getElements(new TypeFilter<CtFor>(CtFor.class)).size();

			cyclomaticComplexity += element.getElements(new TypeFilter<CtForEach>(CtForEach.class)).size();

			cyclomaticComplexity += element.getElements(new TypeFilter<CtWhile>(CtWhile.class)).size();

			cyclomaticComplexity += element.getElements(new TypeFilter<CtDo>(CtDo.class)).size();

			cyclomaticComplexity += element.getElements(new TypeFilter<CtBreak>(CtBreak.class)).size();

			cyclomaticComplexity += element.getElements(new TypeFilter<CtContinue>(CtContinue.class)).size();

			for (CtBinaryOperator<?> operator : element.getElements(new TypeFilter<CtBinaryOperator<?>>(CtBinaryOperator.class))) {
				if (operator.getKind() == BinaryOperatorKind.AND || operator.getKind() == BinaryOperatorKind.OR) {
					cyclomaticComplexity++;
				}
			}
			
			cyclomaticComplexity += element.getElements(new TypeFilter<CtReturn<?>>(CtReturn.class)).size();
			Model.addMeasure(qualifiedName, new Measure(Metric.CCC, cyclomaticComplexity));
		}
	}
}
