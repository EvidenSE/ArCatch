package arcatch.metric.extractor.eh.code;

import arcatch.metric.Measure;
import arcatch.metric.Metric;
import arcatch.model.Model;
import arcatch.util.Util;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtTry;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.visitor.filter.TypeFilter;

public class TLoCExtractor extends AbstractProcessor<CtClass<?>> {

	@Override
	public void process(CtClass<?> element) {

		if (Util.isValid(element)) {
			String qualifiedName = element.getQualifiedName();
			double tryLoc = 0.0;
			for (CtTry tryClause : element.getElements(new TypeFilter<CtTry>(CtTry.class))) {
				int startLine = tryClause.getBody().getPosition().getLine();
				int endLine = tryClause.getBody().getPosition().getEndLine();
				tryLoc += ((endLine - startLine) == 0) ? (1) : ((endLine - startLine) - 1);
			}
			Model.addMeasure(qualifiedName, new Measure(Metric.TLoC, tryLoc));
		}
	}
}