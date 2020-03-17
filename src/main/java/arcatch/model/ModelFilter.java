package arcatch.model;

import java.util.HashSet;
import java.util.Set;

import arcatch.dsl.rule.erosion.impl.kind.RuleKing;
import arcatch.dsl.rule.erosion.impl.relation.BinaryRelation;
import arcatch.dsl.rule.erosion.impl.relation.Remapping;
import arcatch.dsl.rule.erosion.impl.relation.SignalingTo;
import arcatch.dsl.rule.erosion.impl.relation.TernaryRelation;

public class ModelFilter {

	public static Set<BinaryRelation<Operation, Set<Operation>>> findCallsToOperationsOf(SearchPattern fromPattern,
			SearchPattern toPattern) {
		Set<BinaryRelation<Operation, Set<Operation>>> result = new HashSet<>();
		Set<Operation> operations = Model.findOperations(fromPattern);
		for (Operation operation : operations) {
			Set<Operation> callees = operation.findCallees(toPattern);
			if (!callees.isEmpty()) {
				result.add(new BinaryRelation<Operation, Set<Operation>>(RuleKing.Call, operation, callees));
			}
		}
		return result;
	}

	public static Set<BinaryRelation<Operation, Set<Operation>>> findCallsToComplementOperationsOf(
			SearchPattern fromPattern, SearchPattern toPattern) {
		Set<BinaryRelation<Operation, Set<Operation>>> result = new HashSet<>();
		Set<Operation> operations = Model.findOperations(fromPattern);
		for (Operation operation : operations) {
			Set<Operation> callees = operation.findCalleesComplement(toPattern);
			if (!callees.isEmpty()) {
				result.add(new BinaryRelation<Operation, Set<Operation>>(RuleKing.Call, operation, callees));
			}
		}
		return result;
	}

	public static Set<BinaryRelation<Operation, Set<Operation>>> complementAndFindCallsToOperationsOf(
			SearchPattern fromPattern, SearchPattern toPattern) {
		Set<BinaryRelation<Operation, Set<Operation>>> result = new HashSet<>();
		Set<Operation> operations = Model.findOperationComplement(fromPattern);
		for (Operation operation : operations) {
			Set<Operation> callees = operation.findCallees(toPattern);
			if (!callees.isEmpty()) {
				result.add(new BinaryRelation<Operation, Set<Operation>>(RuleKing.Call, operation, callees));
			}
		}
		return result;
	}

	public static Set<BinaryRelation<Operation, Set<Operation>>> findCallsToConstructorsOf(SearchPattern fromPattern,
			SearchPattern toPattern) {
		Set<BinaryRelation<Operation, Set<Operation>>> result = new HashSet<>();
		Set<Operation> operations = Model.findOperations(fromPattern);
		for (Operation operation : operations) {
			Set<Operation> callees = operation.findConstructorCallees(toPattern);
			if (!callees.isEmpty()) {
				result.add(new BinaryRelation<Operation, Set<Operation>>(RuleKing.Creation, operation, callees));
			}
		}
		return result;
	}

	public static Set<BinaryRelation<Operation, Set<Operation>>> findCallsToComplementConstructorsOf(
			SearchPattern fromPattern, SearchPattern toPattern) {
		Set<BinaryRelation<Operation, Set<Operation>>> result = new HashSet<>();
		Set<Operation> operations = Model.findOperations(fromPattern);
		for (Operation operation : operations) {
			Set<Operation> callees = operation.findConstructorCalleesComplement(toPattern);
			if (!callees.isEmpty()) {
				result.add(new BinaryRelation<Operation, Set<Operation>>(RuleKing.Creation, operation, callees));
			}
		}
		return result;
	}

	public static Set<BinaryRelation<Operation, Set<Operation>>> complementAndFindCallsToConstructorsOf(
			SearchPattern fromPattern, SearchPattern toPattern) {
		Set<BinaryRelation<Operation, Set<Operation>>> result = new HashSet<>();
		Set<Operation> operations = Model.findOperationComplement(fromPattern);
		for (Operation operation : operations) {
			Set<Operation> callees = operation.findConstructorCallees(toPattern);
			if (!callees.isEmpty()) {
				result.add(new BinaryRelation<Operation, Set<Operation>>(RuleKing.Creation, operation, callees));
			}
		}
		return result;
	}

	public static Set<BinaryRelation<Unit, Unit>> findExtensionsOf(SearchPattern fromPattern, SearchPattern toPattern) {
		Set<BinaryRelation<Unit, Unit>> result = new HashSet<>();
		Set<Unit> units = Model.findUnits(fromPattern);
		Set<Unit> parentCandidates = Model.findUnits(toPattern);
		for (Unit unit : units) {
			for (Unit parentCandidate : parentCandidates) {
				if (unit.isSubTypeOf(parentCandidate.getQualifiedName())) {
					result.add(new BinaryRelation<Unit, Unit>(RuleKing.Extension, unit, parentCandidate));
					break;
				}
			}
		}
		return result;
	}

	public static Set<BinaryRelation<Unit, Unit>> findComplementExtensionsOf(SearchPattern fromPattern,
			SearchPattern toPattern) {
		Set<BinaryRelation<Unit, Unit>> result = new HashSet<>();
		Set<Unit> units = Model.findUnits(fromPattern);
		Set<Unit> parentCandidates = Model.findUnitsComplement(toPattern);
		for (Unit unit : units) {
			for (Unit parentCandidate : parentCandidates) {
				if (unit.isSubTypeOf(parentCandidate.getQualifiedName())) {
					result.add(new BinaryRelation<Unit, Unit>(RuleKing.Extension, unit, parentCandidate));
					break;
				}
			}
		}
		return result;
	}

	public static Set<BinaryRelation<Unit, Unit>> complementAndFindExtensionsOf(SearchPattern fromPattern,
			SearchPattern toPattern) {
		Set<BinaryRelation<Unit, Unit>> result = new HashSet<>();
		Set<Unit> units = Model.findUnitsComplement(fromPattern);
		Set<Unit> parentCandidates = Model.findUnits(toPattern);
		for (Unit unit : units) {
			for (Unit parentCandidate : parentCandidates) {
				if (unit.isSubTypeOf(parentCandidate.getQualifiedName())) {
					result.add(new BinaryRelation<Unit, Unit>(RuleKing.Extension, unit, parentCandidate));
					break;
				}
			}
		}
		return result;
	}

	public static Set<BinaryRelation<Unit, Set<Unit>>> findImplementationsOf(SearchPattern fromPattern,
			SearchPattern toPattern) {
		Set<BinaryRelation<Unit, Set<Unit>>> result = new HashSet<>();
		Set<Unit> units = Model.findUnits(fromPattern);
		Set<Unit> interfaceCandidates = Model.findInterfaceUnits(toPattern);
		for (Unit unit : units) {
			Set<Unit> interfaces = new HashSet<>();
			for (Unit interfaceCandidate : interfaceCandidates) {
				if (unit.isImplementationOf(interfaceCandidate.getQualifiedName())) {
					interfaces.add(interfaceCandidate);
				}
			}
			if (!interfaces.isEmpty()) {
				result.add(new BinaryRelation<Unit, Set<Unit>>(RuleKing.Implementation, unit, interfaces));
			}
		}
		return result;
	}

	public static Set<BinaryRelation<Unit, Set<Unit>>> findComplementImplementationsOf(SearchPattern fromPattern,
			SearchPattern toPattern) {
		Set<BinaryRelation<Unit, Set<Unit>>> result = new HashSet<>();
		Set<Unit> units = Model.findUnits(fromPattern);
		Set<Unit> interfaceCandidates = Model.findInterfaceUnitsComplement(toPattern);
		for (Unit unit : units) {
			Set<Unit> interfaces = new HashSet<>();
			for (Unit interfaceCandidate : interfaceCandidates) {
				if (unit.isImplementationOf(interfaceCandidate.getQualifiedName())) {
					interfaces.add(interfaceCandidate);
				}
			}
			if (!interfaces.isEmpty()) {
				result.add(new BinaryRelation<Unit, Set<Unit>>(RuleKing.Implementation, unit, interfaces));
			}
		}
		return result;
	}

	public static Set<BinaryRelation<Unit, Set<Unit>>> complementAndFindImplementationsOf(SearchPattern fromPattern,
			SearchPattern toPattern) {
		Set<BinaryRelation<Unit, Set<Unit>>> result = new HashSet<>();
		Set<Unit> units = Model.findUnitsComplement(fromPattern);
		Set<Unit> interfaceCandidates = Model.findInterfaceUnits(toPattern);
		for (Unit unit : units) {
			Set<Unit> interfaces = new HashSet<>();
			for (Unit interfaceCandidate : interfaceCandidates) {
				if (unit.isImplementationOf(interfaceCandidate.getQualifiedName())) {
					interfaces.add(interfaceCandidate);
				}
			}
			if (!interfaces.isEmpty()) {
				result.add(new BinaryRelation<Unit, Set<Unit>>(RuleKing.Implementation, unit, interfaces));
			}
		}
		return result;
	}

	public static Set<BinaryRelation<Operation, Set<Unit>>> findRaisingsOf(SearchPattern fromPattern,
			SearchPattern toPattern) {
		Set<BinaryRelation<Operation, Set<Unit>>> result = new HashSet<>();
		Set<Operation> operations = Model.findOperations(fromPattern);
		for (Operation operation : operations) {
			if (operation.isRaisingExceptions(toPattern)) {
				Set<Unit> exceptions = operation.findRaisedExceptions(toPattern);
				result.add(new BinaryRelation<Operation, Set<Unit>>(RuleKing.Raising, operation, exceptions));
			}
		}
		return result;
	}

	public static Set<BinaryRelation<Operation, Set<Unit>>> findComplementRaisingsOf(SearchPattern fromPattern,
			SearchPattern toPattern) {
		Set<BinaryRelation<Operation, Set<Unit>>> result = new HashSet<>();
		Set<Operation> operations = Model.findOperations(fromPattern);
		for (Operation operation : operations) {
			if (operation.isRaisingExceptionsComplement(toPattern)) {
				Set<Unit> exceptions = operation.findRaisedExceptionsComplement(toPattern);
				result.add(new BinaryRelation<Operation, Set<Unit>>(RuleKing.Raising, operation, exceptions));
			}
		}
		return result;
	}

	public static Set<BinaryRelation<Operation, Set<Unit>>> complementAndFindRaisingsOf(SearchPattern fromPattern,
			SearchPattern toPattern) {
		Set<BinaryRelation<Operation, Set<Unit>>> result = new HashSet<>();
		Set<Operation> operations = Model.findOperationComplement(fromPattern);
		for (Operation operation : operations) {
			if (operation.isRaisingExceptions(toPattern)) {
				Set<Unit> exceptions = operation.findRaisedExceptions(toPattern);
				result.add(new BinaryRelation<Operation, Set<Unit>>(RuleKing.Raising, operation, exceptions));
			}
		}
		return result;
	}

	public static Set<BinaryRelation<Operation, Set<Unit>>> findReraisingsOf(SearchPattern fromPattern,
			SearchPattern toPattern) {
		Set<BinaryRelation<Operation, Set<Unit>>> result = new HashSet<>();
		Set<Operation> operations = Model.findOperations(fromPattern);
		for (Operation operation : operations) {
			if (operation.isRaisingExceptions(toPattern)) {
				Set<Unit> exceptions = operation.findRaisedExceptions(toPattern);
				result.add(new BinaryRelation<Operation, Set<Unit>>(RuleKing.Reraising, operation, exceptions));
			}
		}
		return result;
	}

	public static Set<BinaryRelation<Operation, Set<Unit>>> findComplementReraisingsOf(SearchPattern fromPattern,
			SearchPattern toPattern) {
		Set<BinaryRelation<Operation, Set<Unit>>> result = new HashSet<>();
		Set<Operation> operations = Model.findOperations(fromPattern);
		for (Operation operation : operations) {
			if (operation.isReraisingExceptionsComplement(toPattern)) {
				Set<Unit> exceptions = operation.findRaisedExceptionsComplement(toPattern);
				result.add(new BinaryRelation<Operation, Set<Unit>>(RuleKing.Reraising, operation, exceptions));
			}
		}
		return result;
	}

	public static Set<BinaryRelation<Operation, Set<Unit>>> complementAndFindReraisingsOf(SearchPattern fromPattern,
			SearchPattern toPattern) {
		Set<BinaryRelation<Operation, Set<Unit>>> result = new HashSet<>();
		Set<Operation> operations = Model.findOperationComplement(fromPattern);
		for (Operation operation : operations) {
			if (operation.isReraisingExceptions(toPattern)) {
				Set<Unit> exceptions = operation.findRaisedExceptions(toPattern);
				result.add(new BinaryRelation<Operation, Set<Unit>>(RuleKing.Reraising, operation, exceptions));
			}
		}
		return result;
	}

	public static Set<TernaryRelation<Operation, Unit, Unit>> findRemappingsOf(SearchPattern pattern,
			SearchPattern fromPattern, SearchPattern toPattern) {
		Set<TernaryRelation<Operation, Unit, Unit>> result = new HashSet<>();
		Set<Operation> operations = Model.findOperations(pattern);
		for (Operation operation : operations) {
			if (operation.isRemappingExceptions(fromPattern, toPattern)) {
				Set<Remapping> remapings = operation.findRemappedExceptions(fromPattern, toPattern);
				for (Remapping remaping : remapings) {
					result.add(new TernaryRelation<Operation, Unit, Unit>(RuleKing.Remapping, operation,
							remaping.getFromException(), remaping.getToException()));
				}
			}
		}
		return result;
	}

	public static Set<TernaryRelation<Operation, Unit, Unit>> findComplementRemappingsOf(SearchPattern pattern,
			SearchPattern fromPattern, SearchPattern toPattern) {
		Set<TernaryRelation<Operation, Unit, Unit>> result = new HashSet<>();
		Set<Operation> operations = Model.findOperations(pattern);
		for (Operation operation : operations) {
			if (operation.isRemappingExceptionsComplement(fromPattern, toPattern)) {
				Set<Remapping> remapings = operation.findRemappedExceptionsComplement(fromPattern, toPattern);
				for (Remapping remaping : remapings) {
					result.add(new TernaryRelation<Operation, Unit, Unit>(RuleKing.Remapping, operation,
							remaping.getFromException(), remaping.getToException()));
				}
			}
		}
		return result;
	}

	public static Set<TernaryRelation<Operation, Unit, Unit>> complementAndFindRemappingsOf(SearchPattern pattern,
			SearchPattern fromPattern, SearchPattern toPattern) {
		Set<TernaryRelation<Operation, Unit, Unit>> result = new HashSet<>();
		Set<Operation> operations = Model.findOperationComplement(pattern);
		for (Operation operation : operations) {
			if (operation.isRemappingExceptions(fromPattern, toPattern)) {
				Set<Remapping> remapings = operation.findRemappedExceptions(fromPattern, toPattern);
				for (Remapping remaping : remapings) {
					result.add(new TernaryRelation<Operation, Unit, Unit>(RuleKing.Remapping, operation,
							remaping.getFromException(), remaping.getToException()));
				}
			}
		}
		return result;
	}

	public static Set<BinaryRelation<Operation, Set<Unit>>> findSignalingsOf(SearchPattern fromPattern,
			SearchPattern toPattern) {
		Set<BinaryRelation<Operation, Set<Unit>>> result = new HashSet<>();
		Set<Operation> operations = Model.findOperations(fromPattern);
		for (Operation operation : operations) {
			if (operation.isSignalingExceptions(toPattern)) {
				Set<Unit> exceptions = operation.findSignaledExceptions(toPattern);
				result.add(new BinaryRelation<Operation, Set<Unit>>(RuleKing.Signaling, operation, exceptions));
			}
		}
		return result;
	}

	public static Set<BinaryRelation<Operation, Set<Unit>>> findComplementSignalingsOf(SearchPattern fromPattern,
			SearchPattern toPattern) {
		Set<BinaryRelation<Operation, Set<Unit>>> result = new HashSet<>();
		Set<Operation> operations = Model.findOperations(fromPattern);
		for (Operation operation : operations) {
			if (operation.isSignalingExceptionsComplement(toPattern)) {
				Set<Unit> exceptions = operation.findSignaledExceptionsComplement(toPattern);
				result.add(new BinaryRelation<Operation, Set<Unit>>(RuleKing.Signaling, operation, exceptions));
			}
		}
		return result;
	}

	public static Set<BinaryRelation<Operation, Set<Unit>>> complementAndFindSignalingsOf(SearchPattern fromPattern,
			SearchPattern toPattern) {
		Set<BinaryRelation<Operation, Set<Unit>>> result = new HashSet<>();
		Set<Operation> operations = Model.findOperationComplement(fromPattern);
		for (Operation operation : operations) {
			if (operation.isSignalingExceptions(toPattern)) {
				Set<Unit> exceptions = operation.findSignaledExceptions(toPattern);
				result.add(new BinaryRelation<Operation, Set<Unit>>(RuleKing.Signaling, operation, exceptions));
			}
		}
		return result;
	}

	public static Set<TernaryRelation<Operation, Set<Unit>, Operation>> findSignalingsOfUpTo(SearchPattern fromPattern,
			SearchPattern pattern, SearchPattern toPattern) {
		Set<TernaryRelation<Operation, Set<Unit>, Operation>> result = new HashSet<>();
		Set<Operation> operations = Model.findOperations(fromPattern);
		for (Operation operation : operations) {
			Set<SignalingTo> signalings = operation.findSignaledToExceptions(pattern, toPattern);
			for (SignalingTo signaling : signalings) {
				result.add(new TernaryRelation<Operation, Set<Unit>, Operation>(RuleKing.SignalingTo,
						signaling.getFromOperation(), signaling.getExceptions(), signaling.getToOperation()));
			}
		}
		return result;
	}

	public static Set<TernaryRelation<Operation, Set<Unit>, Operation>> findComplementSignalingsOfUpTo(
			SearchPattern fromPattern, SearchPattern pattern, SearchPattern toPattern) {
		Set<TernaryRelation<Operation, Set<Unit>, Operation>> result = new HashSet<>();
		Set<Operation> operations = Model.findOperations(fromPattern);
		for (Operation operation : operations) {
			Set<SignalingTo> signalings = operation.findSignaledToExceptionsComplement(pattern, toPattern);
			for (SignalingTo signaling : signalings) {
				result.add(new TernaryRelation<Operation, Set<Unit>, Operation>(RuleKing.SignalingTo,
						signaling.getFromOperation(), signaling.getExceptions(), signaling.getToOperation()));
			}
		}
		return result;
	}

	public static Set<TernaryRelation<Operation, Set<Unit>, Operation>> complementAndFindSignalingsOfUpTo(
			SearchPattern fromPattern, SearchPattern pattern, SearchPattern toPattern) {
		Set<TernaryRelation<Operation, Set<Unit>, Operation>> result = new HashSet<>();
		Set<Operation> operations = Model.findOperationComplement(fromPattern);
		for (Operation operation : operations) {
			Set<SignalingTo> signalings = operation.findSignaledToExceptions(pattern, toPattern);
			for (SignalingTo signaling : signalings) {
				result.add(new TernaryRelation<Operation, Set<Unit>, Operation>(RuleKing.SignalingTo,
						signaling.getFromOperation(), signaling.getExceptions(), signaling.getToOperation()));
			}
		}
		return result;
	}

	public static Set<BinaryRelation<Operation, Set<Unit>>> findHandlingsOf(SearchPattern fromPattern,
			SearchPattern toPattern) {
		Set<BinaryRelation<Operation, Set<Unit>>> result = new HashSet<>();
		Set<Operation> operations = Model.findOperations(fromPattern);
		for (Operation operation : operations) {
			if (operation.isHandlingExceptions(toPattern)) {
				Set<Unit> exceptions = operation.findHandledExceptions(toPattern);
				result.add(new BinaryRelation<Operation, Set<Unit>>(RuleKing.Handling, operation, exceptions));
			}
		}
		return result;
	}

	public static Set<BinaryRelation<Operation, Set<Unit>>> findComplementHandlingsOf(SearchPattern fromPattern,
			SearchPattern toPattern) {
		Set<BinaryRelation<Operation, Set<Unit>>> result = new HashSet<>();
		Set<Operation> operations = Model.findOperations(fromPattern);
		for (Operation operation : operations) {
			if (operation.isHandlingExceptionsComplement(toPattern)) {
				Set<Unit> exceptions = operation.findHandledExceptionsComplement(toPattern);
				result.add(new BinaryRelation<Operation, Set<Unit>>(RuleKing.Handling, operation, exceptions));
			}
		}
		return result;
	}

	public static Set<BinaryRelation<Operation, Set<Unit>>> complementAndFindHandlingsOf(SearchPattern fromPattern,
			SearchPattern toPattern) {
		Set<BinaryRelation<Operation, Set<Unit>>> result = new HashSet<>();
		Set<Operation> operations = Model.findOperationComplement(fromPattern);
		for (Operation operation : operations) {
			if (operation.isHandlingExceptions(toPattern)) {
				Set<Unit> exceptions = operation.findHandledExceptions(toPattern);
				result.add(new BinaryRelation<Operation, Set<Unit>>(RuleKing.Handling, operation, exceptions));
			}
		}
		return result;
	}
}
