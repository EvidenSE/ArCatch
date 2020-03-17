package arcatch.model.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import arcatch.dsl.rule.erosion.impl.relation.Remapping;
import arcatch.dsl.rule.erosion.impl.relation.SignalingTo;
import arcatch.model.Operation;
import arcatch.model.Position;
import arcatch.model.SearchPattern;
import arcatch.model.Unit;

public class OperationImpl implements Operation {

	private Unit owner;

	private Position position;

	private boolean isConstructor;

	private boolean isAbstract;

	private boolean isStatic;

	private boolean isFinal;

	private boolean isPublic;

	private boolean isPrivate;

	private boolean isProtected;

	private boolean isShadow;

	private String name;

	private String signature;

	private String returnTypeQualifiedName;

	private Map<String, Operation> callers;

	private Map<String, Operation> callees;

	private Map<String, Unit> raisedSet;

	private Map<String, Unit> signaledSet;

	private Map<String, Unit> handledSet;

	private Set<Remapping> remappedSet;

	private Map<String, Unit> reraisedSet;

	public OperationImpl() {
		callers = new HashMap<>();

		callees = new HashMap<>();

		raisedSet = new HashMap<>();

		signaledSet = new HashMap<>();

		handledSet = new HashMap<>();

		remappedSet = new HashSet<>();

		reraisedSet = new HashMap<>();

	}

	public Unit getOwner() {
		return owner;
	}

	public void setOwner(Unit owner) {
		this.owner = owner;
	}

	@Override
	public Position getPosition() {
		return this.position;
	}

	@Override
	public void setPosition(Position position) {
		this.position = position;
	}

	public boolean isConstructor() {
		return isConstructor;
	}

	public void setConstructor(boolean isConstructor) {
		this.isConstructor = isConstructor;
	}

	public boolean isAbstract() {
		return isAbstract;
	}

	public void setAbstract(boolean isAbstract) {
		this.isAbstract = isAbstract;
	}

	public boolean isStatic() {
		return isStatic;
	}

	public void setStatic(boolean isStatic) {
		this.isStatic = isStatic;
	}

	public boolean isFinal() {
		return isFinal;
	}

	public void setFinal(boolean isFinal) {
		this.isFinal = isFinal;
	}

	public boolean isPublic() {
		return isPublic;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	public boolean isPrivate() {
		return isPrivate;
	}

	public void setPrivate(boolean isPrivate) {
		this.isPrivate = isPrivate;
	}

	public boolean isProtected() {
		return isProtected;
	}

	public void setProtected(boolean isProtected) {
		this.isProtected = isProtected;
	}

	public boolean isPackage() {
		return !(isPrivate || isPublic || isProtected);
	}

	public boolean isShadow() {
		return isShadow;
	}

	public void setShadow(boolean isShadow) {
		this.isShadow = isShadow;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getQualifiedSignature() {
		return this.owner.getQualifiedName() + "." + getSignature();
	}

	public String getSignature() {
		return signature;
	}

	public String getExtendedSignature() {
		return returnTypeQualifiedName + " " + signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getReturnTypeQualifiedName() {
		return returnTypeQualifiedName;
	}

	public void setReturnTypeQualifiedName(String returnTypeQualifiedName) {
		this.returnTypeQualifiedName = returnTypeQualifiedName;
	}

	public boolean matches(SearchPattern pattern) {
		final Pattern classPattern = Pattern.compile(pattern.getClassSearchPattern());
		final Pattern methodPattern = Pattern.compile(pattern.getCompiledMethodSearchPattern());
		if (classPattern.matcher(getOwner().getQualifiedName()).matches()) {
			if (methodPattern.matcher(getSignature()).matches()) {
				return true;
			}
		}
		return false;
	}

	public Set<Operation> getCallers() {
		return new HashSet<>(this.callers.values());
	}

	public Set<Operation> getConstructorCallers() {
		Set<Operation> constructors = new HashSet<>();
		for (Operation method : getCallers()) {
			if (method.isConstructor()) {
				constructors.add(method);
			}
		}
		return constructors;
	}

	public Set<Operation> findCallers(SearchPattern searchPattern) {
		Set<Operation> matched = new HashSet<>();
		final Pattern classPattern = Pattern.compile(searchPattern.getClassSearchPattern());
		final Pattern methodPattern = Pattern.compile(searchPattern.getCompiledMethodSearchPattern());
		for (Operation method : this.callers.values()) {
			if (classPattern.matcher(method.getOwner().getQualifiedName()).matches()) {
				if (methodPattern.matcher(method.getExtendedSignature()).matches()) {
					matched.add(method);
				}
			}
		}
		return matched;
	}

	public boolean hasCallers() {
		return !this.callers.isEmpty();
	}

	public void addCaller(Operation method) {
		this.callers.put(method.getSignature(), method);
	}

	public Operation getCaller(String signature) {
		return callers.get(signature);
	}

	public boolean hasCallees() {
		return !this.callees.isEmpty();
	}

	public Set<Operation> getCallees() {
		return new HashSet<>(this.callees.values());
	}

	public Set<Operation> getConstructorCallees() {
		Set<Operation> constructors = new HashSet<>();
		for (Operation method : getCallees()) {
			if (method.isConstructor()) {
				constructors.add(method);
			}
		}
		return constructors;
	}

	public Set<Operation> findCallees(SearchPattern searchPattern) {
		Set<Operation> matched = new HashSet<>();
		final Pattern classPattern = Pattern.compile(searchPattern.getClassSearchPattern());
		final Pattern methodPattern = Pattern.compile(searchPattern.getCompiledMethodSearchPattern());
		for (Operation method : this.callees.values()) {
			if (classPattern.matcher(method.getOwner().getQualifiedName()).matches()) {
				if (methodPattern.matcher(method.getExtendedSignature()).matches()) {
					matched.add(method);
				}
			}
		}
		return matched;
	}

	public Set<Operation> findCalleesComplement(SearchPattern searchPattern) {
		Set<Operation> callees = this.getCallees();
		Set<Operation> calleesFound = this.findCallees(searchPattern);
		callees.removeAll(calleesFound);
		return callees;
	}

	public void addCallee(Operation method) {
		this.callees.put(method.getSignature(), method);
	}

	public Operation getCallee(String signature) {
		return callees.get(signature);
	}

	public Set<Operation> findConstructorCallees(SearchPattern searchPattern) {
		Set<Operation> matched = new HashSet<>();
		final Pattern classPattern = Pattern.compile(searchPattern.getClassSearchPattern());
		for (Operation method : getConstructorCallees()) {
			if (classPattern.matcher(method.getOwner().getQualifiedName()).matches()) {
				matched.add(method);
			}
		}
		return matched;
	}

	public Set<Operation> findConstructorCalleesComplement(SearchPattern searchPattern) {
		Set<Operation> constructorCallees = this.getConstructorCallees();
		Set<Operation> constructorCalleesFound = this.findConstructorCallees(searchPattern);
		constructorCallees.removeAll(constructorCalleesFound);
		return constructorCallees;
	}

	public boolean isRaisingAnyException() {
		return !this.raisedSet.isEmpty();
	}

	public Set<Unit> getRaisedExceptions() {
		return new HashSet<>(this.raisedSet.values());
	}

	public void setRaisedExceptions(Set<Unit> raisedExceptions) {
		for (Unit exceptionalClass : raisedExceptions) {
			this.raisedSet.put(exceptionalClass.getQualifiedName(), exceptionalClass);
		}
	}

	public void addRaisedException(Unit exception) {
		this.raisedSet.put(exception.getQualifiedName(), exception);
	}

	public Set<Unit> findRaisedExceptions(SearchPattern searchPattern) {
		Set<Unit> matched = new HashSet<>();
		final Pattern pattern = Pattern.compile(searchPattern.getClassSearchPattern());
		for (Unit exception : raisedSet.values()) {
			if (pattern.matcher(exception.getQualifiedName()).matches()) {
				matched.add(exception);
			}
		}
		return matched;
	}

	public Set<Unit> findRaisedExceptionsComplement(SearchPattern searchPattern) {
		Set<Unit> notMatched = new HashSet<>();
		final Pattern pattern = Pattern.compile(searchPattern.getClassSearchPattern());
		for (Unit exception : raisedSet.values()) {
			if (!pattern.matcher(exception.getQualifiedName()).matches()) {
				notMatched.add(exception);
			}
		}
		return notMatched;
	}

	public boolean isRaisingExceptions(SearchPattern searchPattern) {
		return isRaisingAnyException() && !this.findRaisedExceptions(searchPattern).isEmpty();
	}

	public boolean isRaisingExceptionsComplement(SearchPattern searchPattern) {
		return isRaisingAnyException() && !this.findRaisedExceptionsComplement(searchPattern).isEmpty();
	}

	public boolean isSignalingAnyException() {
		return !this.signaledSet.isEmpty();
	}

	public Set<Unit> getSignaledExceptions() {
		return new HashSet<>(this.signaledSet.values());
	}

	public void setSignaledExceptions(Set<Unit> signaledExceptions) {
		for (Unit exceptionalClass : signaledExceptions) {
			this.signaledSet.put(exceptionalClass.getQualifiedName(), exceptionalClass);
		}

	}

	public void addSignaledException(Unit exception) {
		this.signaledSet.put(exception.getQualifiedName(), exception);
	}

	public Set<Unit> findSignaledExceptions(SearchPattern searchPattern) {
		Set<Unit> matched = new HashSet<>();
		final Pattern pattern = Pattern.compile(searchPattern.getClassSearchPattern());
		for (Unit exception : signaledSet.values()) {
			if (pattern.matcher(exception.getQualifiedName()).matches()) {
				matched.add(exception);
			}
		}
		return matched;
	}

	public Set<Unit> findSignaledExceptionsComplement(SearchPattern searchPattern) {
		Set<Unit> notMatched = new HashSet<>();
		final Pattern pattern = Pattern.compile(searchPattern.getClassSearchPattern());
		for (Unit exception : signaledSet.values()) {
			if (!pattern.matcher(exception.getQualifiedName()).matches()) {
				notMatched.add(exception);
			}
		}
		return notMatched;
	}

	public boolean isSignalingExceptions(SearchPattern searchPattern) {
		return this.isSignalingAnyException() && !this.findSignaledExceptions(searchPattern).isEmpty();
	}

	public boolean isSignalingExceptionsComplement(SearchPattern searchPattern) {
		return this.isSignalingAnyException() && !this.findSignaledExceptionsComplement(searchPattern).isEmpty();
	}

	public boolean isSignalingToAnyException() {
		return !this.signaledSet.isEmpty() && !this.callers.isEmpty();
	}

	public Set<SignalingTo> getSignaledToExceptions() {
		Set<SignalingTo> signalingToSet = new HashSet<>();
		for (Operation method : this.getCallers()) {
			signalingToSet.add(new SignalingTo(this, new HashSet<>(this.getSignaledExceptions()), method));
		}
		return signalingToSet;
	}

	public Set<SignalingTo> findSignaledToExceptions(SearchPattern exceptionSearchPattern,
			SearchPattern toSearchPattern) {
		Set<SignalingTo> signalingToSet = new HashSet<>();
		Set<Unit> exceptions = new HashSet<>(this.findSignaledExceptions(exceptionSearchPattern));
		for (Operation method : this.findCallers(toSearchPattern)) {
			signalingToSet.add(new SignalingTo(this, exceptions, method));
		}
		return signalingToSet;
	}

	public Set<SignalingTo> findSignaledToExceptionsComplement(SearchPattern exceptionSearchPattern,
			SearchPattern toSearchPattern) {
		Set<SignalingTo> signalingToSet = new HashSet<>();
		Set<Unit> exceptions = new HashSet<>(this.findSignaledExceptionsComplement(exceptionSearchPattern));
		for (Operation method : this.findCallers(toSearchPattern)) {
			signalingToSet.add(new SignalingTo(this, exceptions, method));
		}
		return signalingToSet;
	}

	public boolean isSignalingToExceptions(SearchPattern exceptionSearchPattern, SearchPattern toSearchPattern) {
		return isSignalingAnyException()
				&& !findSignaledToExceptions(exceptionSearchPattern, toSearchPattern).isEmpty();
	}

	public boolean isSignalingToExceptionsComplement(SearchPattern exceptionSearchPattern,
			SearchPattern toSearchPattern) {
		return isSignalingAnyException()
				&& !findSignaledToExceptionsComplement(exceptionSearchPattern, toSearchPattern).isEmpty();
	}

	public boolean isHandlingAnyException() {
		return !this.handledSet.isEmpty();
	}

	public Set<Unit> getHandledExceptions() {
		return new HashSet<>(this.handledSet.values());
	}

	public void setHandledExceptions(Set<Unit> handledExceptions) {
		for (Unit exceptionalClass : handledExceptions) {
			this.handledSet.put(exceptionalClass.getQualifiedName(), exceptionalClass);
		}
	}

	public void addHandledException(Unit exception) {
		this.handledSet.put(exception.getQualifiedName(), exception);
	}

	public Set<Unit> findHandledExceptions(SearchPattern searchPattern) {
		Set<Unit> matched = new HashSet<>();
		final Pattern pattern = Pattern.compile(searchPattern.getClassSearchPattern());
		for (Unit exception : handledSet.values()) {
			if (pattern.matcher(exception.getQualifiedName()).matches()) {
				matched.add(exception);
			}
		}
		return matched;
	}

	public Set<Unit> findHandledExceptionsComplement(SearchPattern searchPattern) {
		Set<Unit> notMatched = new HashSet<>();
		final Pattern pattern = Pattern.compile(searchPattern.getClassSearchPattern());
		for (Unit exception : handledSet.values()) {
			if (!pattern.matcher(exception.getQualifiedName()).matches()) {
				notMatched.add(exception);
			}
		}
		return notMatched;
	}

	public boolean isHandlingExceptions(SearchPattern searchPattern) {
		return isHandlingAnyException() && !this.findHandledExceptions(searchPattern).isEmpty();
	}

	public boolean isHandlingExceptionsComplement(SearchPattern searchPattern) {
		return isHandlingAnyException() && !this.findHandledExceptionsComplement(searchPattern).isEmpty();
	}

	public boolean isRemappingAnyException() {
		return !this.remappedSet.isEmpty();
	}

	public Set<Remapping> getRemappedExceptions() {
		return this.remappedSet;
	}

	public void setRemappedExceptions(Set<Remapping> remapped) {
		this.remappedSet = remapped;
	}

	public void addRemappedException(Unit from, Unit to) {
		this.remappedSet.add(new Remapping(from, to));
	}

	public Set<Remapping> findRemappedExceptions(SearchPattern fromSearchPattern, SearchPattern toSearchPattern) {
		Set<Remapping> matched = new HashSet<>();
		final Pattern fromPattern = Pattern.compile(fromSearchPattern.getClassSearchPattern());
		for (Remapping remapping : remappedSet) {
			if (fromPattern.matcher(remapping.getFromException().getQualifiedName()).matches()) {
				final Pattern toPattern = Pattern.compile(toSearchPattern.getClassSearchPattern());
				if (toPattern.matcher(remapping.getToException().getQualifiedName()).matches()) {
					matched.add(remapping);
				}
			}
		}
		return matched;
	}

	public Set<Remapping> findRemappedExceptionsComplement(SearchPattern fromSearchPattern,
			SearchPattern toSearchPattern) {
		Set<Remapping> notMatched = new HashSet<>();
		final Pattern fromPattern = Pattern.compile(fromSearchPattern.getClassSearchPattern());
		for (Remapping remapping : remappedSet) {
			if (fromPattern.matcher(remapping.getFromException().getQualifiedName()).matches()) {
				final Pattern toPattern = Pattern.compile(toSearchPattern.getClassSearchPattern());
				if (!toPattern.matcher(remapping.getToException().getQualifiedName()).matches()) {
					notMatched.add(remapping);
				}
			} else if (!fromPattern.matcher(remapping.getFromException().getQualifiedName()).matches()) {
				final Pattern toPattern = Pattern.compile(toSearchPattern.getClassSearchPattern());
				if (toPattern.matcher(remapping.getToException().getQualifiedName()).matches()) {
					notMatched.add(remapping);
				}
			} else if (!fromPattern.matcher(remapping.getFromException().getQualifiedName()).matches()) {
				final Pattern toPattern = Pattern.compile(toSearchPattern.getClassSearchPattern());
				if (!toPattern.matcher(remapping.getToException().getQualifiedName()).matches()) {
					notMatched.add(remapping);
				}
			}
		}
		return notMatched;
	}

	public boolean isRemappingExceptions(SearchPattern fromSearchPattern, SearchPattern toSearchPattern) {
		return isRemappingAnyException() && !this.findRemappedExceptions(fromSearchPattern, toSearchPattern).isEmpty();
	}

	public boolean isRemappingExceptionsComplement(SearchPattern fromSearchPattern, SearchPattern toSearchPattern) {
		return isRemappingAnyException()
				&& !this.findRemappedExceptionsComplement(fromSearchPattern, toSearchPattern).isEmpty();
	}

	public boolean isReraisingAnyException() {
		return !this.reraisedSet.isEmpty();
	}

	public Set<Unit> getReraisedExceptions() {
		return new HashSet<>(this.reraisedSet.values());
	}

	public void setReraisedExceptions(Set<Unit> reraisedExceptions) {
		for (Unit exceptionalClass : reraisedExceptions) {
			this.reraisedSet.put(exceptionalClass.getQualifiedName(), exceptionalClass);
		}
	}

	public void addReraisedException(Unit exception) {
		this.reraisedSet.put(exception.getQualifiedName(), exception);
	}

	public Set<Unit> findReraisedExceptions(SearchPattern searchPattern) {
		Set<Unit> matched = new HashSet<>();
		final Pattern pattern = Pattern.compile(searchPattern.getClassSearchPattern());
		for (Unit exception : reraisedSet.values()) {
			if (pattern.matcher(exception.getQualifiedName()).matches()) {
				matched.add(exception);
			}
		}
		return matched;
	}

	public Set<Unit> findReraisedExceptionsComplement(SearchPattern searchPattern) {
		Set<Unit> notMatched = new HashSet<>();
		final Pattern pattern = Pattern.compile(searchPattern.getClassSearchPattern());
		for (Unit exception : reraisedSet.values()) {
			if (!pattern.matcher(exception.getQualifiedName()).matches()) {
				notMatched.add(exception);
			}
		}
		return notMatched;
	}

	public boolean isReraisingExceptions(SearchPattern searchPattern) {
		return this.isRaisingAnyException() && !this.findReraisedExceptions(searchPattern).isEmpty();
	}

	public boolean isReraisingExceptionsComplement(SearchPattern searchPattern) {
		return this.isRaisingAnyException() && !this.findReraisedExceptionsComplement(searchPattern).isEmpty();
	}

	public boolean equals(Object object) {
		if (object != null && object instanceof Operation) {
			return this.getQualifiedSignature().equals(((Operation) object).getQualifiedSignature());
		}
		return false;
	}

	public int hashCode() {
		return this.getQualifiedSignature().hashCode();
	}

	public void print() {
		System.out.println("- Operation Signature: " + this.getSignature());
		if (this.isRaisingAnyException()) {
			System.out.println("-- Raised Exceptions: ");
			for (Unit exceptionalClass : this.getRaisedExceptions()) {
				System.out.println("--- " + exceptionalClass.getQualifiedName());
			}
		}

		if (this.isSignalingAnyException()) {
			System.out.println("-- Signaled Exceptions: ");
			for (Unit exceptionalClass : this.getSignaledExceptions()) {
				System.out.println("--- " + exceptionalClass.getQualifiedName());
			}
		}

		if (this.isHandlingAnyException()) {
			System.out.println("-- Handled Exceptions: ");
			for (Unit exceptionalClass : this.getHandledExceptions()) {
				System.out.println("--- " + exceptionalClass.getQualifiedName());
			}
		}

		if (this.isReraisingAnyException()) {
			System.out.println("-- Re-Raised Exceptions: ");
			for (Unit exceptionalClass : this.getReraisedExceptions()) {
				System.out.println("--- " + exceptionalClass.getQualifiedName());
			}
		}

		if (this.isRemappingAnyException()) {
			System.out.println("-- Remapped Exceptions: ");
			for (Remapping remapping : this.getRemappedExceptions()) {
				System.out.println("--- " + remapping.getFromException().getQualifiedName() + " ~> "
						+ remapping.getToException().getQualifiedName());
			}
		}

		if (this.hasCallees()) {
			System.out.println("-- Callee Methods: ");
			for (Operation callee : this.getCallees()) {
				System.out.println("---" + callee.getOwner().getQualifiedName() + "#" + callee.getSignature());
			}
		}

		if (this.hasCallers()) {
			System.out.println("-- Caller Methods: ");
			for (Operation caller : this.getCallers()) {
				System.out.println("---" + caller.getOwner().getQualifiedName() + "#" + caller.getSignature());
			}
		}

	}
}