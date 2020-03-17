package arcatch.metric.extractor.oo.ck;

import java.util.HashSet;
import java.util.Set;

import arcatch.metric.Measure;
import arcatch.metric.Metric;
import arcatch.model.Model;
import arcatch.util.Util;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.visitor.filter.TypeFilter;

public class RFCExtractor extends AbstractProcessor<CtClass<?>> {

	@Override
	public void process(CtClass<?> element) {

		if (Util.isValid(element)) {
			String qualifiedName = element.getQualifiedName();
			double responseForClass = element.getMethods().size();
			Set<String> methodCalls = new HashSet<>();
			TypeFilter<CtInvocation<?>> invocationFilter = new TypeFilter<CtInvocation<?>>(CtInvocation.class);
			for (CtMethod<?> method : element.getMethods()) {
				for (CtInvocation<?> invocation : method.getElements(invocationFilter)) {
					methodCalls.add(invocation.getExecutable().getSignature());
				}
			}
			responseForClass += methodCalls.size();
			Model.addMeasure(qualifiedName, new Measure(Metric.RFC, responseForClass));
		}
	}
}