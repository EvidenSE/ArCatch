package arcatch.model.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import arcatch.dsl.compartment.grammar.Compartment;
import arcatch.metric.Measure;
import arcatch.metric.Metric;
import arcatch.metric.MetricSuite;
import arcatch.model.Operation;
import arcatch.model.Position;
import arcatch.model.SearchPattern;
import arcatch.model.Unit;

public class UnitImpl implements Unit {

	private boolean isClass;

	private Position position;

	private boolean isInterface;

	private boolean isShadow;

	private boolean isAbstract;

	private boolean isException;

	private boolean isUncheckedException;

	private Set<Compartment> owners;

	private String qualifiedName;

	private Set<String> parentsQualifiedNames = new HashSet<>();

	private Set<String> implementationNames;

	private MetricSuite metricSuite;

	private Set<Operation> operations;

	public UnitImpl(String qualifiedName, Set<String> parentsQualifiedNames) {
		super();
		this.qualifiedName = qualifiedName;
		this.parentsQualifiedNames = parentsQualifiedNames;
		this.metricSuite = new MetricSuite(this);
		this.operations = new HashSet<>();
		this.implementationNames = new HashSet<String>();
		this.owners = new HashSet<>();
	}

	public UnitImpl(String qualifiedName) {
		this(qualifiedName, new HashSet<String>(Arrays.asList("java.lang.Object")));
	}

	@Override
	public boolean isClass() {
		return isClass;
	}

	@Override
	public void setClass(boolean value) {
		this.isClass = value;
	}

	@Override
	public Position getPosition() {
		return this.position;
	}

	@Override
	public void setPosition(Position position) {
		this.position = position;
	}

	@Override
	public boolean isInterface() {
		return isInterface;
	}

	@Override
	public void setInterface(boolean value) {
		this.isInterface = value;
	}

	@Override
	public boolean isException() {
		return isException;
	}

	@Override
	public void setException(boolean value) {
		this.isException = value;
	}

	@Override
	public boolean isUncheckedException() {
		return isException && isUncheckedException;
	}

	@Override
	public void setUncheckedException(boolean value) {
		this.isUncheckedException = value;
	}

	@Override
	public boolean isShadow() {
		return isShadow;
	}

	@Override
	public void setShadow(boolean value) {
		this.isShadow = value;
	}

	@Override
	public boolean isAbstract() {
		return isAbstract;
	}

	@Override
	public void setAbstract(boolean value) {
		this.isAbstract = value;
	}

	public String getQualifiedName() {
		return qualifiedName;
	}

	public void setQualifiedName(String qualifiedName) {
		this.qualifiedName = qualifiedName;
	}

	public Set<String> getParentsQualifiedNames() {
		return parentsQualifiedNames;
	}

	public void setParentsQualifiedNames(Set<String> parentsQualifiedNames) {
		this.parentsQualifiedNames = parentsQualifiedNames;
	}

	public void addParentQualifiedName(String parentQualifiedName) {
		this.parentsQualifiedNames.add(parentQualifiedName);
	}

	public boolean isSubTypeOf(String qualifiedName) {
		return hasParent() && this.parentsQualifiedNames.contains(qualifiedName);
	}

	public boolean hasParent() {
		return !this.parentsQualifiedNames.isEmpty();
	}

	public MetricSuite getMetricSuite() {
		return metricSuite;
	}

	public void setMetricSuite(MetricSuite suite) {
		this.metricSuite = suite;
	}

	public void addMeasure(Measure measure) {
		this.metricSuite.addMeasure(measure);
	}

	@Override
	public Double getMetricValue(Metric metric) {
		for (Measure measure : this.metricSuite.getMeasures()) {
			if (measure.getMetric() == metric) {
				return measure.getValue();
			}
		}
		return 0.0;
	}

	@Override
	public Double getMetricValueByName(String name) {
		for (Measure measure : this.metricSuite.getMeasures()) {
			if (measure.getMetric().getShortName().equals(name)) {
				return measure.getValue();
			}
		}
		return 0.0;
	}

	public Set<Compartment> getOwners() {
		return owners;
	}

	public void setOwners(Set<Compartment> owners) {
		this.owners = owners;
	}

	public void addOwner(Compartment owner) {
		this.owners.add(owner);
	}

	@Override
	public boolean hasOwner() {
		return !this.owners.isEmpty();
	}

	public Operation getOperation(String signature) {
		for (Operation operation : this.operations) {
			if (signature.equals(operation.getSignature())) {
				return operation;
			}
		}
		return null;
	}

	public void addOperation(Operation operation) {
		operation.setOwner(this);
		this.operations.add(operation);
	}

	@Override
	public Set<Operation> getOperations() {
		return this.operations;
	}

	public Set<Operation> findOperations(SearchPattern searchPattern) {
		Set<Operation> matched = new HashSet<>();
		final Pattern pattern = Pattern.compile(searchPattern.getCompiledMethodSearchPattern());
		for (Operation operation : operations) {
			if (operation.isConstructor()) {
				matched.add(operation);
			} else if (pattern.matcher(operation.getExtendedSignature()).matches()) {
				matched.add(operation);
			}
		}
		return matched;
	}

	@Override
	public Set<Operation> getConstructors() {
		Set<Operation> constructors = new HashSet<>();
		for (Operation operation : getOperations()) {
			if (operation.isConstructor()) {
				constructors.add(operation);
			}
		}
		return constructors;
	}

	@Override
	public Set<String> getInterfaceImplementationNames() {
		return this.implementationNames;
	}

	@Override
	public void setInterfaceImplementationNames(Set<String> interfaceNames) {
		this.implementationNames = interfaceNames;
	}

	@Override
	public boolean isImplementationOf(String qualifiedName) {
		return hasInterfaceImplementation() && this.implementationNames.contains(qualifiedName);
	}

	@Override
	public boolean hasInterfaceImplementation() {
		return !this.implementationNames.isEmpty();
	}

	@Override
	public void addInterfaceImplementationName(String interfaceName) {
		this.implementationNames.add(interfaceName);
	}

	@Override
	public boolean equals(Object object) {
		if (object != null && object instanceof Unit) {
			return this.getQualifiedName().equals(((Unit) object).getQualifiedName());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.getQualifiedName().hashCode();
	}

	@Override
	public int compareTo(Unit object) {
		if (object != null && object instanceof Unit) {
			return this.getQualifiedName().compareTo(((Unit) object).getQualifiedName());
		}
		return 0;
	}

}
