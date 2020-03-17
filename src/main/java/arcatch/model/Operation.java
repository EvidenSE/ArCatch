package arcatch.model;

import java.util.Set;

import arcatch.dsl.rule.erosion.impl.relation.Remapping;
import arcatch.dsl.rule.erosion.impl.relation.SignalingTo;

public interface Operation {

	public Unit getOwner();

	public void setOwner(Unit owner);

	public Position getPosition();
	
	public void setPosition(Position position);
	
	public boolean isConstructor();

	public void setConstructor(boolean isConstructor);

	public boolean isAbstract();

	public void setAbstract(boolean isAbstract);

	public boolean isStatic();

	public void setStatic(boolean isStatic);

	public boolean isFinal();

	public void setFinal(boolean isFinal);

	public boolean isPublic();

	public void setPublic(boolean isPublic);

	public boolean isPrivate();

	public void setPrivate(boolean isPrivate);

	public boolean isProtected();

	public void setProtected(boolean isProtected);

	public boolean isPackage();

	public boolean isShadow();

	public void setShadow(boolean isShadow);

	public String getName();

	public void setName(String name);

	public String getQualifiedSignature();

	public String getSignature();

	public String getExtendedSignature();

	public void setSignature(String signature);

	public String getReturnTypeQualifiedName();

	public void setReturnTypeQualifiedName(String returnTypeQualifiedName);

	public boolean matches(SearchPattern pattern);

	public Set<Operation> getCallers();

	public Set<Operation> getConstructorCallers();

	public Set<Operation> findCallers(SearchPattern searchPattern);

	public boolean hasCallers();

	public void addCaller(Operation method);

	public Operation getCaller(String signature);

	public boolean hasCallees();

	public Set<Operation> getCallees();

	public Set<Operation> getConstructorCallees();

	public Set<Operation> findCallees(SearchPattern searchPattern);

	public Set<Operation> findCalleesComplement(SearchPattern searchPattern);

	public void addCallee(Operation operation);

	public Operation getCallee(String signature);

	public Set<Operation> findConstructorCallees(SearchPattern searchPattern);

	public Set<Operation> findConstructorCalleesComplement(SearchPattern searchPattern);

	public boolean isRaisingAnyException();

	public Set<Unit> getRaisedExceptions();

	public void setRaisedExceptions(Set<Unit> raisedExceptions);

	public void addRaisedException(Unit exception);

	public Set<Unit> findRaisedExceptions(SearchPattern searchPattern);

	public Set<Unit> findRaisedExceptionsComplement(SearchPattern searchPattern);

	public boolean isRaisingExceptions(SearchPattern searchPattern);

	public boolean isRaisingExceptionsComplement(SearchPattern searchPattern);

	public boolean isSignalingAnyException();

	public Set<Unit> getSignaledExceptions();

	public void setSignaledExceptions(Set<Unit> signaledExceptions);

	public void addSignaledException(Unit exception);

	public Set<Unit> findSignaledExceptions(SearchPattern searchPattern);

	public Set<Unit> findSignaledExceptionsComplement(SearchPattern searchPattern);

	public boolean isSignalingExceptions(SearchPattern searchPattern);

	public boolean isSignalingExceptionsComplement(SearchPattern searchPattern);

	public boolean isSignalingToAnyException();

	public Set<SignalingTo> getSignaledToExceptions();

	public Set<SignalingTo> findSignaledToExceptions(SearchPattern exceptionSearchPattern,
			SearchPattern toSearchPattern);

	public Set<SignalingTo> findSignaledToExceptionsComplement(SearchPattern exceptionSearchPattern,
			SearchPattern toSearchPattern);

	public boolean isSignalingToExceptions(SearchPattern exceptionSearchPattern, SearchPattern toSearchPattern);

	public boolean isSignalingToExceptionsComplement(SearchPattern exceptionSearchPattern,
			SearchPattern toSearchPattern);

	public boolean isHandlingAnyException();

	public Set<Unit> getHandledExceptions();

	public void setHandledExceptions(Set<Unit> handledExceptions);

	public void addHandledException(Unit exception);

	public Set<Unit> findHandledExceptions(SearchPattern searchPattern);

	public Set<Unit> findHandledExceptionsComplement(SearchPattern searchPattern);

	public boolean isHandlingExceptions(SearchPattern searchPattern);

	public boolean isHandlingExceptionsComplement(SearchPattern searchPattern);

	public boolean isRemappingAnyException();

	public Set<Remapping> getRemappedExceptions();

	public void setRemappedExceptions(Set<Remapping> remapped);

	public void addRemappedException(Unit from, Unit to);

	public Set<Remapping> findRemappedExceptions(SearchPattern fromSearchPattern, SearchPattern toSearchPattern);

	public Set<Remapping> findRemappedExceptionsComplement(SearchPattern fromSearchPattern,
			SearchPattern toSearchPattern);

	public boolean isRemappingExceptions(SearchPattern fromSearchPattern, SearchPattern toSearchPattern);

	public boolean isRemappingExceptionsComplement(SearchPattern fromSearchPattern, SearchPattern toSearchPattern);

	public boolean isReraisingAnyException();

	public Set<Unit> getReraisedExceptions();

	public void setReraisedExceptions(Set<Unit> reraisedExceptions);

	public void addReraisedException(Unit exception);

	public Set<Unit> findReraisedExceptions(SearchPattern searchPattern);

	public Set<Unit> findReraisedExceptionsComplement(SearchPattern searchPattern);

	public boolean isReraisingExceptions(SearchPattern searchPattern);

	public boolean isReraisingExceptionsComplement(SearchPattern searchPattern);

	public boolean equals(Object object);

	public int hashCode();

	public void print();
}
