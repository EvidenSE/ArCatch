package arcatch.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import arcatch.dsl.compartment.grammar.Compartment;
import arcatch.dsl.config.grammar.Configuration;
import arcatch.dsl.rule.common.DesignRule;
import arcatch.dsl.rule.erosion.impl.relation.Remapping;
import arcatch.model.impl.OperationImpl;
import arcatch.model.impl.PositionImpl;
import arcatch.model.impl.UnitImpl;
import arcatch.util.Util;
import spoon.Launcher;
import spoon.MavenLauncher;
import spoon.MavenLauncher.SOURCE_TYPE;
import spoon.SpoonAPI;
import spoon.reflect.code.CtCatch;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtThrow;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtConstructor;
import spoon.reflect.declaration.CtExecutable;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtType;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.visitor.filter.TypeFilter;

public class ModelBuilder {

	private static SpoonAPI spoon;

	private static Map<String, CtType<?>> typeMap = new HashMap<>();

	private static CtType<Throwable> throwableType;

	private static CtType<Exception> exceptionType;

	private static CtType<Error> errorType;

	private static CtType<Runtime> runtimeType;

	public static void setConfiguration(Configuration configuration) {
		Model.setConfiguration(configuration);
	}

	public static void addCompartment(Compartment compartment) {
		Model.addCompartment(compartment);
	}

	public static void addRule(DesignRule rule) {
		Model.addRule(rule);
	}

	public static void buildModel() {
		if (Model.isEmpty()) {
			if (Model.getConfiguration().isMavenBasedProject()) {
				buildModelBasedOnMavenProject(Model.getConfiguration().getProjectPath());

			} else if (!Model.getConfiguration().getDependencies().isEmpty()) {
				String[] dependencies = Model.getConfiguration().getDependencies()
						.toArray(new String[Model.getConfiguration().getDependencies().size()]);

				buildModel(Model.getConfiguration().getProjectPath(), dependencies, false);

			} else {
				buildModel(Model.getConfiguration().getProjectPath(), true);
			}
			extractFacts();
		}
	}

	private static void buildModel(String path, String[] dependencies, boolean withoutDependencies) {
		String[] fullPath = new String[dependencies.length + 1];
		fullPath[0] = path;
		for (int i = 1; i < fullPath.length; i++) {
			fullPath[i] = dependencies[i - 1];
		}
		spoon = new Launcher();
		spoon.getEnvironment().setNoClasspath(withoutDependencies);
		spoon.getEnvironment().setSourceClasspath(fullPath);
		spoon.addInputResource(path);
		spoon.buildModel();
	}

	private static void buildModel(String path, boolean withoutDependencies) {
		spoon = new Launcher();
		spoon.getEnvironment().setNoClasspath(withoutDependencies);
		spoon.getEnvironment().setSourceClasspath(new String[] { path });
		spoon.addInputResource(path);
		spoon.buildModel();
	}

	private static void buildModelBasedOnMavenProject(String path) {
		spoon = new MavenLauncher(path, SOURCE_TYPE.APP_SOURCE);
		spoon.getEnvironment().setNoClasspath(true);
		spoon.buildModel();
	}

	private static void extractFacts() {
		throwableType = spoon.getFactory().Type().get(Throwable.class);
		exceptionType = spoon.getFactory().Type().get(Exception.class);
		errorType = spoon.getFactory().Type().get(Error.class);
		runtimeType = spoon.getFactory().Type().get(Runtime.class);
		Set<CtType<?>> types = new HashSet<>();

		for (CtType<?> type : spoon.getModel().getElements(new TypeFilter<CtType<?>>(CtType.class))) {
			if (Util.isValid(type)) {
				Model.addUnit(extractUnitFacts(type));
				types.add(type);
				typeMap.put(type.getQualifiedName(), type);
			}
		}

		for (CtType<?> type : types) {
			upgradeUnitFacts(type);
		}
		buildCallGraph(types);
	}

	private static Unit extractUnitFacts(CtType<?> type) {
		Unit unit = null;

		if (type.isClass()) {
			if (type.getSuperclass() != null) {
				unit = new UnitImpl(type.getQualifiedName(),
						new HashSet<String>(Arrays.asList(type.getSuperclass().getQualifiedName())));
				if (typeMap.containsKey(type.getSuperclass().getQualifiedName())) {
					Model.addUnit(extractUnitFacts(type.getSuperclass().getTypeDeclaration()));
				} else {
					Unit unitSuperClass = new UnitImpl(type.getSuperclass().getQualifiedName());
					unitSuperClass.setClass(true);
					unitSuperClass.setShadow(true);
					Model.addUnit(unitSuperClass);
				}
			} else {
				unit = new UnitImpl(type.getQualifiedName());
			}
			for (CtTypeReference<?> reference : type.getSuperInterfaces()) {
				unit.addInterfaceImplementationName(reference.getQualifiedName());
			}
		} else if (type.isInterface()) {
			if (type.getSuperInterfaces() != null && !type.getSuperInterfaces().isEmpty()) {
				Set<String> superIntefaces = new HashSet<String>();
				unit = new UnitImpl(type.getQualifiedName());
				for (CtTypeReference<?> superInterface : type.getSuperInterfaces()) {
					superIntefaces.add(superInterface.getQualifiedName());
					if (typeMap.containsKey(superInterface.getQualifiedName())) {
						Model.addUnit(extractUnitFacts(superInterface.getTypeDeclaration()));
					} else {
						Unit unitSuperClass = new UnitImpl(superInterface.getQualifiedName());
						unitSuperClass.setClass(true);
						unitSuperClass.setShadow(true);
						Model.addUnit(unitSuperClass);
					}
				}
				unit.setParentsQualifiedNames(superIntefaces);
			} else {
				unit = new UnitImpl(type.getQualifiedName());
			}
		}

		unit.setClass(type.isClass());
		unit.setAbstract(type.isAbstract());
		unit.setInterface(type.isInterface());
		unit.setShadow(type.isShadow());

		if (!type.isShadow()) {
			int begin = type.getPosition().getLine();
			int end = type.getPosition().getEndLine();
			unit.setPosition(new PositionImpl(begin, end));
		}

		if (type.isSubtypeOf(throwableType.getReference()) || type.isSubtypeOf(exceptionType.getReference())
				|| type.isSubtypeOf(errorType.getReference()) || type.isSubtypeOf(runtimeType.getReference())) {
			unit.setException(true);
			if (type.isSubtypeOf(runtimeType.getReference())) {
				unit.setUncheckedException(true);
			}
		}
		return unit;
	}

	private static void upgradeUnitFacts(CtType<?> type) {
		Unit unit = Model.getUnit(type.getQualifiedName());
		Set<CtExecutable<?>> executables = new HashSet<>();
		executables.addAll(type.getMethods());
		if (type instanceof CtClass<?>) {
			executables.addAll(((CtClass<?>) type).getConstructors());
		}
		for (CtExecutable<?> executable : executables) {
			if (executable != null) {
				Operation operation = extractExecutableFacts(executable);
				unit.addOperation(operation);
			}
		}
	}

	private static Operation extractExecutableFacts(CtExecutable<?> executable) {
		Operation operation = new OperationImpl();

		if (executable instanceof CtConstructor<?>) {
			operation.setConstructor(true);
			operation.setPublic(((CtConstructor<?>) executable).isPublic());
			operation.setPrivate(((CtConstructor<?>) executable).isPrivate());

		} else if (executable instanceof CtMethod<?>) {
			operation.setConstructor(false);
			operation.setPublic(((CtMethod<?>) executable).isPublic());
			operation.setPrivate(((CtMethod<?>) executable).isPrivate());
			operation.setProtected(((CtMethod<?>) executable).isProtected());
			operation.setAbstract(((CtMethod<?>) executable).isAbstract());
			operation.setFinal(((CtMethod<?>) executable).isFinal());
			operation.setStatic(((CtMethod<?>) executable).isStatic());
		}

		if (!executable.isImplicit()) {
			if (executable.getPosition().isValidPosition()) {
				int begin = executable.getPosition().getLine();
				int end = executable.getPosition().getEndLine();
				operation.setPosition(new PositionImpl(begin, end));
			}
		}
		operation.setName(executable.getSimpleName());
		operation.setSignature(executable.getSignature());
		operation.setReturnTypeQualifiedName(executable.getType().getQualifiedName());
		operation.setRaisedExceptions(extractRaisedExceptions(executable));
		operation.setSignaledExceptions(extractSignaledExceptions(executable));
		operation.setHandledExceptions(extractHandledExceptions(executable));
		operation.setReraisedExceptions(extractReraisedExceptions(executable));
		operation.setRemappedExceptions(extractRemappedExceptions(executable));
		return operation;
	}

	private static Set<Unit> extractRaisedExceptions(CtExecutable<?> executable) {
		Set<Unit> exceptions = new HashSet<>();
		if (executable != null) {
			Unit raised;
			for (CtThrow throwStatment : executable.getElements(new TypeFilter<CtThrow>(CtThrow.class))) {
				CtTypeReference<?> exception = throwStatment.getThrownExpression().getType();
				if (exception != null) {
					raised = Model.getUnit(exception.getQualifiedName());
					if (raised == null) {
						if (typeMap.containsKey(exception.getQualifiedName())) {
							raised = extractUnitFacts(exception.getTypeDeclaration());
						} else {
							raised = new UnitImpl(exception.getQualifiedName());
							raised.setClass(true);
							raised.setShadow(true);
							raised.setException(true);
						}
						Model.addUnit(raised);
					}
					exceptions.add(raised);
				}
			}
		}
		return exceptions;

	}

	private static Set<Unit> extractSignaledExceptions(CtExecutable<?> executable) {
		Set<Unit> exceptions = new HashSet<>();
		if (executable != null) {
			Unit signaled;
			for (CtTypeReference<?> exception : executable.getThrownTypes()) {
				signaled = Model.getUnit(exception.getQualifiedName());
				if (signaled == null) {
					if (typeMap.containsKey(exception.getQualifiedName())) {
						signaled = extractUnitFacts(exception.getTypeDeclaration());
					} else {
						signaled = new UnitImpl(exception.getQualifiedName());
						signaled.setClass(true);
						signaled.setShadow(true);
						signaled.setException(true);
					}
					Model.addUnit(signaled);
				}
				exceptions.add(signaled);
			}
		}
		return exceptions;
	}

	private static Set<Unit> extractHandledExceptions(CtExecutable<?> executable) {
		Set<Unit> exceptions = new HashSet<>();
		if (executable != null) {
			for (CtCatch catchBlock : executable.getElements(new TypeFilter<CtCatch>(CtCatch.class))) {
				List<CtTypeReference<?>> caughtExceptions = catchBlock.getParameter().getMultiTypes();
				for (CtTypeReference<?> caughtException : caughtExceptions) {
					Unit handled = Model.getUnit(caughtException.getQualifiedName());
					if (handled == null) {
						if (typeMap.containsKey(caughtException.getQualifiedName())) {
							handled = extractUnitFacts(caughtException.getTypeDeclaration());
						} else {
							handled = new UnitImpl(caughtException.getQualifiedName());
							handled.setClass(true);
							handled.setShadow(true);
							handled.setException(true);
						}
						Model.addUnit(handled);
					}
					exceptions.add(handled);
				}
			}
		}
		return exceptions;
	}

	private static Set<Unit> extractReraisedExceptions(CtExecutable<?> executable) {
		Set<Unit> exceptions = new HashSet<>();
		if (executable != null) {
			for (CtCatch catchBlock : executable.getElements(new TypeFilter<CtCatch>(CtCatch.class))) {
				List<CtTypeReference<?>> caughtExceptions = catchBlock.getParameter().getMultiTypes();
				for (CtThrow throwStatment : catchBlock.getElements(new TypeFilter<CtThrow>(CtThrow.class))) {
					CtTypeReference<?> raisedException = throwStatment.getThrownExpression().getType();
					if (raisedException != null) {
						String raisedExceptionName = raisedException.getQualifiedName();
						for (CtTypeReference<?> caughtException : caughtExceptions) {
							if (caughtException.getQualifiedName().equals(raisedExceptionName)) {
								Unit exception = Model.getUnit(caughtException.getQualifiedName());
								if (exception == null) {
									if (typeMap.containsKey(caughtException.getQualifiedName())) {
										exception = extractUnitFacts(caughtException.getTypeDeclaration());
									} else {
										exception = new UnitImpl(caughtException.getQualifiedName());
										exception.setClass(true);
										exception.setShadow(true);
										exception.setException(true);
									}
									Model.addUnit(exception);
								}
								exceptions.add(exception);
							}
						}
					}
				}
			}
		}
		return exceptions;
	}

	private static Set<Remapping> extractRemappedExceptions(CtExecutable<?> executable) {
		Set<Remapping> remappings = new HashSet<>();
		if (executable != null) {
			for (CtCatch catchBlock : executable.getElements(new TypeFilter<CtCatch>(CtCatch.class))) {
				List<CtTypeReference<?>> caughtExceptions = catchBlock.getParameter().getMultiTypes();
				for (CtThrow throwStatment : catchBlock.getElements(new TypeFilter<CtThrow>(CtThrow.class))) {
					CtTypeReference<?> raisedException = throwStatment.getThrownExpression().getType();
					if (raisedException != null) {
						for (CtTypeReference<?> caughtException : caughtExceptions) {
							Unit raised = Model.getUnit(raisedException.getQualifiedName());
							if (raised == null) {
								if (typeMap.containsKey(raisedException.getQualifiedName())) {
									raised = extractUnitFacts(raisedException.getTypeDeclaration());
								} else {
									raised = new UnitImpl(raisedException.getQualifiedName());
									raised.setClass(true);
									raised.setShadow(true);
									raised.setException(true);
								}
								Model.addUnit(raised);
							}

							Unit caught = Model.getUnit(caughtException.getQualifiedName());
							if (caught == null) {
								if (typeMap.containsKey(caughtException.getQualifiedName())) {
									caught = extractUnitFacts(caughtException.getTypeDeclaration());
								} else {
									caught = new UnitImpl(caughtException.getQualifiedName());
									caught.setClass(true);
									caught.setShadow(true);
									caught.setException(true);
								}
								Model.addUnit(caught);
							}

							if (!caught.getQualifiedName().equals(raised.getQualifiedName())) {
								remappings.add(new Remapping(caught, raised));
							}
						}
					}
				}
			}
		}
		return remappings;
	}

	private static void buildCallGraph(Set<CtType<?>> types) {
		for (CtType<?> callerType : types) {
			Unit callerUnit = Model.getUnit(callerType.getQualifiedName());
			if (callerUnit == null || callerUnit.isInterface()) {
				continue;
			}

			for (CtMethod<?> callerTypeMethod : callerType.getMethods()) {

				Operation callerUnitOperation = callerUnit.getOperation(callerTypeMethod.getReference().getSignature());
				if (callerUnitOperation == null) {
					continue;
				}

				TypeFilter<CtInvocation<?>> invocationFilter = new TypeFilter<CtInvocation<?>>(CtInvocation.class);
				for (CtInvocation<?> calleeInvocation : callerTypeMethod.getElements(invocationFilter)) {

					CtTypeReference<?> calleeReferenceType = calleeInvocation.getExecutable().getDeclaringType();
					if (calleeReferenceType != null) {
						Unit calleeUnit = Model.getUnit(calleeReferenceType.getQualifiedName());
						if (calleeUnit == null) {
							if (typeMap.containsKey(calleeReferenceType.getQualifiedName())) {
								calleeUnit = extractUnitFacts(calleeReferenceType.getTypeDeclaration());
							} else {
								calleeUnit = new UnitImpl(calleeReferenceType.getQualifiedName());
								calleeUnit.setClass(true);
								calleeUnit.setShadow(true);
							}
							Model.addUnit(calleeUnit);
						}
						Operation calleeUnitOperation = calleeUnit
								.getOperation(calleeInvocation.getExecutable().getSignature());
						if (calleeUnitOperation == null) {

							if (calleeInvocation.getExecutable().getExecutableDeclaration() != null) {
								// if (calleeInvocation.getExecutable().getDeclaration() != null) {
								calleeUnitOperation = extractExecutableFacts(
										calleeInvocation.getExecutable().getExecutableDeclaration());
								// calleeUnitOperation =
								// extractExecutableFacts(calleeInvocation.getExecutable().getDeclaration());
								calleeUnit.addOperation(calleeUnitOperation);
							}
						}
						if (callerUnitOperation != null && calleeUnitOperation != null)
							Model.addOperationCall(callerUnitOperation, calleeUnitOperation);
					}
				}
			}
		}
	}

	public static void extractMetrics() {

		spoon.addProcessor("arcatch.metric.extractor.oo.code.LoCExtractor");

		spoon.addProcessor("arcatch.metric.extractor.oo.ck.NOCExtractor");
		spoon.addProcessor("arcatch.metric.extractor.oo.ck.CBOExtractor");
		spoon.addProcessor("arcatch.metric.extractor.oo.ck.DITExtractor");
		spoon.addProcessor("arcatch.metric.extractor.oo.ck.RFCExtractor");
		spoon.addProcessor("arcatch.metric.extractor.oo.ck.WMCExtractor");
		spoon.addProcessor("arcatch.metric.extractor.oo.ck.LCOMExtractor");

		spoon.addProcessor("arcatch.metric.extractor.oo.code.LCOM2Extractor");
		spoon.addProcessor("arcatch.metric.extractor.oo.code.LCOM3Extractor");
		spoon.addProcessor("arcatch.metric.extractor.oo.code.NoAExtractor");
		spoon.addProcessor("arcatch.metric.extractor.oo.code.NoPAExtractor");
		spoon.addProcessor("arcatch.metric.extractor.oo.code.NoMExtractor");
		spoon.addProcessor("arcatch.metric.extractor.oo.code.NoPMExtractor");
		spoon.addProcessor("arcatch.metric.extractor.oo.code.NoIExtractor");
		spoon.addProcessor("arcatch.metric.extractor.oo.code.CaExtractor");
		spoon.addProcessor("arcatch.metric.extractor.oo.code.CeExtractor");
		spoon.addProcessor("arcatch.metric.extractor.oo.code.CCCExtractor");
		spoon.addProcessor("arcatch.metric.extractor.oo.code.LMLoCExtractor");
		spoon.addProcessor("arcatch.metric.extractor.oo.code.LPLExtractor");

		spoon.addProcessor("arcatch.metric.extractor.eh.code.TLoCExtractor");
		spoon.addProcessor("arcatch.metric.extractor.eh.code.CLoCExtractor");
		spoon.addProcessor("arcatch.metric.extractor.eh.code.FLoCExtractor");
		spoon.addProcessor("arcatch.metric.extractor.eh.code.ECCExtractor");
		spoon.addProcessor("arcatch.metric.extractor.eh.code.FCCExtractor");
		spoon.addProcessor("arcatch.metric.extractor.eh.code.HCCExtractor");
		spoon.addProcessor("arcatch.metric.extractor.eh.code.TCCExtractor");
		spoon.addProcessor("arcatch.metric.extractor.eh.code.RCoExtractor");
		spoon.addProcessor("arcatch.metric.extractor.eh.code.SCoExtractor");
		spoon.addProcessor("arcatch.metric.extractor.eh.code.HCoExtractor");
		spoon.addProcessor("arcatch.metric.extractor.eh.code.NoRExtractor");
		spoon.addProcessor("arcatch.metric.extractor.eh.code.NoSExtractor");
		spoon.addProcessor("arcatch.metric.extractor.eh.code.NoHExtractor");
		spoon.addProcessor("arcatch.metric.extractor.eh.code.TFCExtractor");

		spoon.addProcessor("arcatch.metric.extractor.eh.smell.NoCAExtractor");
		spoon.addProcessor("arcatch.metric.extractor.eh.smell.NoCIExtractor");
		spoon.addProcessor("arcatch.metric.extractor.eh.smell.NoCRNExtractor");
		spoon.addProcessor("arcatch.metric.extractor.eh.smell.NoDRExtractor");
		spoon.addProcessor("arcatch.metric.extractor.eh.smell.NoEHExtractor");
		spoon.addProcessor("arcatch.metric.extractor.eh.smell.NoGHExtractor");
		spoon.addProcessor("arcatch.metric.extractor.eh.smell.NoGSExtractor");
		spoon.addProcessor("arcatch.metric.extractor.eh.smell.NoNPBExtractor");
		spoon.addProcessor("arcatch.metric.extractor.eh.smell.NoOCExtractor");
		spoon.addProcessor("arcatch.metric.extractor.eh.smell.NoOCAExtractor");
		spoon.addProcessor("arcatch.metric.extractor.eh.smell.NoSFExtractor");
		spoon.addProcessor("arcatch.metric.extractor.eh.smell.NoSKSExtractor");

		spoon.process();
	}

}
