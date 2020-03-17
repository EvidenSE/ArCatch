package arcatch.metric.extractor.oo.code;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
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

public class CaExtractor extends AbstractProcessor<CtClass<?>> {

	private static Map<String, Set<String>> typeDependecyMap = new HashMap<>();

	@Override
	public void process(CtClass<?> element) {

		if (Util.isValid(element)) {
			String qualifiedName = element.getQualifiedName();
			fillTypeDependencyMapping(element.getFactory().getModel());
			double afferentCoupling = computAfferentCoupling(qualifiedName);
			Model.addMeasure(qualifiedName, new Measure(Metric.Ca, afferentCoupling));
		}
	}

	private static void fillTypeDependencyMapping(CtModel model) {
		if (typeDependecyMap.isEmpty()) {
			Collection<CtType<?>> types = model.getElements(new TypeFilter<CtType<?>>(CtType.class)).stream()
					.filter(t -> Util.isValid(t)).collect(Collectors.<CtType<?>> toList());
			for (CtType<?> type : types) {
				typeDependecyMap.put(type.getQualifiedName(), new HashSet<>());
				for (CtTypeReference<?> referredType : type.getReferencedTypes()) {
					if (referredType != null && !referredType.isShadow() && referredType.getQualifiedName() != null) {
						typeDependecyMap.get(type.getQualifiedName()).add(referredType.getQualifiedName());
					}
				}
			}
		}
	}

	private static long computAfferentCoupling(String typeQualifiedName) {
		return typeDependecyMap.values().stream().filter(set -> set.contains(typeQualifiedName)).count();
	}
}
