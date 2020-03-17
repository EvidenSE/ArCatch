package arcatch.model;

import java.util.Set;

import arcatch.dsl.compartment.grammar.Compartment;
import arcatch.metric.Measure;
import arcatch.metric.Metric;
import arcatch.metric.MetricSuite;

public interface Unit extends Comparable<Unit> {

	public Position getPosition();

	public void setPosition(Position position);

	public boolean isClass();

	public void setClass(boolean value);

	public boolean isInterface();

	public void setInterface(boolean value);

	public boolean isException();

	public void setException(boolean value);

	public boolean isUncheckedException();

	public void setUncheckedException(boolean value);

	public boolean isShadow();

	public void setShadow(boolean value);

	public boolean isAbstract();

	public void setAbstract(boolean value);

	public String getQualifiedName();

	public void setQualifiedName(String qualifiedName);

	public Set<String> getParentsQualifiedNames();

	public void setParentsQualifiedNames(Set<String> parentsQualifiedNames);

	public void addParentQualifiedName(String parentQualifiedName);

	public boolean isSubTypeOf(String qualifiedName);

	public boolean hasParent();

	public Set<String> getInterfaceImplementationNames();

	public void setInterfaceImplementationNames(Set<String> interfaceNames);

	public void addInterfaceImplementationName(String interfaceName);

	public boolean isImplementationOf(String qualifiedName);

	public boolean hasInterfaceImplementation();

	public MetricSuite getMetricSuite();

	public void setMetricSuite(MetricSuite suite);

	public void addMeasure(Measure measure);

	public Double getMetricValue(Metric metric);

	public Double getMetricValueByName(String name);

	public Set<Compartment> getOwners();

	public void setOwners(Set<Compartment> owners);

	public void addOwner(Compartment owner);

	public Operation getOperation(String signature);

	public Set<Operation> getOperations();

	public void addOperation(Operation operation);

	public Set<Operation> findOperations(SearchPattern searchPattern);

	public Set<Operation> getConstructors();

	public boolean hasOwner();

	public boolean equals(Object object);

	public int hashCode();
}
