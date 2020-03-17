package arcatch.metric.extractor.eh.code;

import arcatch.metric.Measure;
import arcatch.metric.Metric;
import arcatch.model.Model;
import arcatch.util.Util;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtCatch;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.visitor.filter.TypeFilter;

public class CLoCExtractor extends AbstractProcessor<CtClass<?>> {

	@Override
	public void process(CtClass<?> element) {

		if (Util.isValid(element)) {
			String qualifiedName = element.getQualifiedName();
			double catchLoc = 0.0;

			for (CtCatch catchClause : element.getElements(new TypeFilter<CtCatch>(CtCatch.class))) {
				int numberOfStatments = catchClause.getBody().getStatements().size();
				if (numberOfStatments > 0) {
					int startLine = catchClause.getBody().getPosition().getLine();
					int endLine = catchClause.getBody().getPosition().getEndLine();
					catchLoc += ((endLine - startLine) == 0) ? (1) : ((endLine - startLine) - 1);
				}
			}
			Model.addMeasure(qualifiedName, new Measure(Metric.CLoC, catchLoc));
		}
	}
}
