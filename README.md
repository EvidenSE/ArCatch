# ArCatch

ArCatch is a tool for static-architecture conformance checking of exception handling design, which deals with the **Exception Handling Degradation** problem. ArCatch is composed of two main parts: 
* A specification language to express architectural elements, map the architectural elements into implementation ones, and define exception handling anti-degradation rules; and 
* A design rule checker to automatically perform the conformance checking and generates conformance reports. 

ArCatch is implemented in Java and its current version provides support only for Java programs.

### How to create the Jar file?

* mvn clean compile package

You can go to folder target/arcatch-2.0.0.jar to get the generated jar.

## ArCatch in 5 steps
------------------

1. Basic Configuration

    ```java
    ArCatchAPI.setConfiguration(ArCatchAPI
            .configurationBuilder()
            .projectNameAndVersion("FooSys", "1.0")
            .projectPath("<project-src-path>")
            .addDependency("<1st-dependecy-path>")
            .addDependency("<2nd-dependecy-path>")        
            .build());
    ```
    OR

    ```java
    ArCatchAPI.setConfiguration(ArCatchAPI
            .configurationBuilder()
            .projectNameAndVersion("FooSys", "1.0")
            .projectPathWithMaven("<path-to-the-project-pom>")       
            .build());
    ```

2. Compartment Specification
    ```java
    Compartment compA, compB, compE;
    compA = ArCatchAPI
              .compartmentBuilder()
              .compartment("A")
              .matching("foo.a.opt.*")
              .matching("foo.a.util.*")        
              .build();
		    
    ArCatchAPI.addCompartment(compA);		    

    compB = ArCatchAPI
              .compartmentBuilder()
              .compartment("B")
              .matching("foo.b.*")
              .build();
		    
    ArCatchAPI.addCompartment(compB);

    compE = ArCatchAPI
              .compartmentBuilder()
              .compartment("E")
              .matching("foo.exception.*")
              .build();

    ArCatchAPI.addCompartment(compE);;
    ```
3. Anti-Erosion Design Rule Especification
    ```java
    DesignRule AER01, AER02, AER03;

    AER01 = ArCatchAPI
              .ruleBuilder()
              .antiErosion()
              .criticality(Criticality.MEDIUM)
              .only(compB)
              .canSignal(compE)
              .to(compA)
              .build();

    AER02 = ArCatchAPI
              .ruleBuilder()
              .antiErosion()
              .criticality(Criticality.MEDIUM)
              .compartiment(compA)
              .mustHandle(compE)
              .build();

    AER03 = ArCatchAPI
              .ruleBuilder()
              .antiErosion()
              .criticality(Criticality.MEDIUM)
              .compartiment(compA)
              .cannotReraise(compE)
              .build();
    ```
4. Anti-Drift Design Rule Especification
    ```java
    DesignRule ADR01, ADR02, ADR03;

    ADR01 = ArCatchAPI
              .ruleBuilder()
              .antiDrift()
              .criticality(Criticality.WARNING)
              .compartment(compA,compB)
              .constrainedTo("(Ce/(Ce + Ca)) <= 0.3")
              .build());

    ADR02 = ArCatchAPI
              .ruleBuilder()
              .antiDrift()
              .criticality(Criticality.WARNING)
              .compartment(compA,compB)
              .constrainedTo("NoEH > 0.0")
              .build());

    ADR03 = ArCatchAPI
              .ruleBuilder()
              .antiDrift()
              .criticality(Criticality.WARNING)
              .compartment(compA,compB)
              .constrainedTo("NoDR > 0.0")
              .build());
    ```
5. Checking Rules
    ```java
    ArCatchAPI.addRule(AER01)
    ...
    ArCatchAPI.addRule(AER03)
    
    ArCatchAPI.addRule(ADR01)
    ...
    ArCatchAPI.addRule(ADR03)

    ArCatchAPI.check();
    ```

Source Code Metrics Supported
-----------------------------

ArCatch extracts ```45``` metrics from a software project's source code under analysis. This set is divided into two subsets of regular and exception handling-related metrics. The regular subset is composed of ```19``` classical metrics (see next). The EH-related subset, is composed of ```26``` metrics. Both sets of metrics (regular and exception handling-related) are used in the specification of anti-drift rules.

1. Classical Metrics

	|Metric      |Meaning                                                 |
	|:-----------|:-------------------------------------------------------|
	|WMC	     |**Weighted Methods per Class**. It counts the number of branch instructions in a class.|
	|DIT	     |**Depth of Inheritance Tree**. It counts the number of "fathers" a class has.|
	|NOC	     |**Number of Children**. It conts the number of immediate sub-classes subordinated to a class in the class hierarchy.|
	|CBO	     |**Coupling Between Objects**. It is a count of the number of non-inheritance related couples with other classes.|
	|RFC         |**Response For Class**. It counts the number of unique method invocations in a class.|
	|LCOM        |**Lack of Cohesion of Methods v1**. It measures the cohesiveness of a class.|
	|LCOM2       |**Lack of Cohesion of Methods v2**. It measures the cohesiveness of a class.|
	|LCOM3       |**Lack of Cohesion of Methods v3**. It measures the cohesiveness of a class.|
	|CCC         |**Class Cyclomatic Complexity**. It counts the  Thomas J. McCabe's cyclomatic complexity of a class.|
	|LoC         |**Lines of Code**. It counts the number of lines of code of a class.|
	|NoI         |**Number of Invovations**. It counts the number of invocations performed by a class.|
	|NoM         |**Number of Methods**. It counts the number of methods of a class.|
	|NoPM        |**Number of Public Methods**. It counts the number of public methods of a class.|
	|NoA         |**Number of Declared Attributes**. It counts the number of declared attributes of a class.|
	|NoPA        |**Number of Public Attributes**. It counts the number of public attributes of a class.|
	|Ca          |**Afferent Coupling**. Is counts how many other classes use the specific class. |
	|Ce          |**Efferent Coupling**. It counts of how many different classes are used by the specific class.|
	|LMLoC       |**Longest Method Lines of Code**. It counts the lines of code of the longest method of a class.|
	|LPL         |**Longest Parameter List**. It counts the number of parameters of the method with the longest parameter list of a class.|

2. Exception Handling Metrics

	|Metric      |Meaning                                                 |
	|:-----------|:-------------------------------------------------------|
	|ECC         |**Exceptional Cyclomatic Complexity**. It counts the sum of cyclomatic complexity inside try, catch, and finally blocks plus the number of throw and methods with throws of a class.|
	|TCC         |**Try Block Cyclomatic Complexity**. It computes the cyclomatic complexity inside a try block of a class.|
	|HCC         |**Catch Block Cyclomatic Complexity**. It computes the cyclomatic complexity inside a catch block of a class.|
	|FCC         |**Finally Block Cyclomatic Complexity**. It computes the cyclomatic complexity inside a finally block of a class.|
	|TFC         |**Troubleness Factor per Class**. It counts the number of methods called by a class that signals an exception back.|
	|TLoC        |**Try Block Lines of Code**. It counts the number of lines of code of all try blocks of a class.|
	|CLoC        |**Catch Block Lines of Code**. It counts the number of lines of code of all catch blocks of a class.|
	|FLoC        |**Finally Block Lines of Code**. It counts the number of lines of code of all finally blocks of a class.|
	|RCo         |**Raising Coupling**. It counts the number of different exceptions types raised (throw) by a class.|
	|SCo         |**Signaling  Coupling**. It counts the number of different exceptions types signaled (throws) by a class.|
	|HCo         |**Handling  Coupling**. It counts the number of different exceptions types handled (catch) by a class.|
	|NoR         |**Number of Raisings**. It counts the number of exceptions raised (throw) by a class.|
	|NoS         |**Number of Signalings**. It counts the number of exceptions signaled (throws) by a class.|
	|NoH         |**Number of Handlings**. It counts the number of exceptions handled (catch) by a class.|
	|NoCRN       |**Number of Catch and Return Null**. It counts the number of handlers that catches an exception an returns null.|
	|NoCA        |**Number of Catch and Abort**. It counts the number of handlers that catches an exception and exits.|
	|NoOC        |**Number of Over-Catches**. It counts the number of handlers that catches multiple exception types.|
	|NoOCA       |**Number of Over-Catches and Abort**. It counts the number of handlers that catches multiple exception types and exits.|
	|NoNPB       |**Number of Nested Protected Blocks**. It counts the number of protected block (try) declared inside an existent protected block.|       
	|NoGH        |**Number of Generic Handlings**. It counts the number of general (```Exception``` or ```Throwable```) exceptions handled by a class.|
	|NoGS        |**Number of Generic Signalings**. It counts the number of general (```Exception``` or ```Throwable```) exceptions signaled by a class.|     
	|NoEH        |**Number of Empty Handlings**. It counts the number of empty catch blocks of a class.|
	|NoDR        |**Number of Destructive Remappings**. It counts the number of exception type remappings that no preserving the exception stack trace.| 
	|NoCI        |**Number of Catch and Ignore**. It counts the number of handlers who catches an exception and do not uses it anymore in the handler block.|
	|NoSF        |**Number of Signalings in Finally Blocks**. It counts the number of exception raisings in cleanup (finally) blocks.|
	|NoSKS       |**Number of Signaling the Kitchen Sink**. It counts the number of signaling methods that signals more then one exception type.|
