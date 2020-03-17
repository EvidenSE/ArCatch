package arcatch.metric.extractor.oo.ck;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import arcatch.metric.Measure;
import arcatch.metric.Metric;
import arcatch.model.Model;
import arcatch.util.Util;
import spoon.processing.AbstractProcessor;
import spoon.reflect.CtModel;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtType;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.visitor.filter.TypeFilter;

public class NOCExtractor extends AbstractProcessor<CtClass<?>> {

	private static Map<String, String> superTypeMap = new HashMap<>();

	@Override
	public void process(CtClass<?> element) {

		if (Util.isValid(element)) {
			String qualifiedName = element.getQualifiedName();
			fillSuperTypeMapping(element.getFactory().getModel());
			double numberOfChildren = computNumberOfChildren(qualifiedName);
			Model.addMeasure(qualifiedName, new Measure(Metric.NOC, numberOfChildren));
		}
	}

	private static void fillSuperTypeMapping(CtModel model) {
		if (superTypeMap.isEmpty()) {
			Collection<CtType<?>> types = model.getElements(new TypeFilter<CtType<?>>(CtType.class)).stream()
					.filter(t -> Util.isValid(t)).collect(Collectors.<CtType<?>> toList());
			for (CtType<?> type : types) {
				CtTypeReference<?> parent = null;
				if (type.isClass()) {
					parent = type.getSuperclass();
				}
				if (parent != null && parent.getQualifiedName() != null) {
					superTypeMap.put(type.getQualifiedName(), parent.getQualifiedName());
				} else {
					superTypeMap.put(type.getQualifiedName(), "java.lang.Object");
				}
			}
		}
	}

	public static long computNumberOfChildren(String parentCandidateName) {
		return superTypeMap.values().stream().filter(name -> name.equals(parentCandidateName)).count();
	}
}
