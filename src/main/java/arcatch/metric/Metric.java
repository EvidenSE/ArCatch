package arcatch.metric;

public enum Metric {

	WMC("WMC", "Weighted Methods per Class", "Classic metric proposed by Chidamber and Kemerer."), 
	
	DIT("DIT", "Depth of Inheritance Tree", "Classic metric proposed by Chidamber and Kemerer."), 
	
	NOC("NOC", "Number of Children", "Classic metric proposed by Chidamber and Kemerer."), 
	
	CBO("CBO", "Coupling Between Objects", "Classic metric proposed by Chidamber and Kemerer."), 
	
	RFC("RFC", "Response For Class", "Classic metric proposed by Chidamber and Kemerer."), 
	
	LCOM("LCOM", "Lack of Cohesion of Methods v1", "Classic metric proposed by Chidamber and Kemerer."), 
	
	LCOM2("LCOM2", "Lack of Cohesion of Methods v2", "A variation of LCOM"), 
	
	LCOM3("LCOM3", "Lack of Cohesion of Methods v3", "A variation of LCOM2"), 	

	CCC("CCC", "Class Cyclomatic Complexity", "Computes the Thomas J. McCabe cyclomatic complexity of a class."),
	
	LoC("LoC", "Lines of Code", "The number of the lines of code of a class."), 

	NoI("NoI", "Number of Invovations", "The number of all invocations performed by a class."),
	
	NoM("NoM", "Number of Methods", "The number of all declared methods of a class."),
	
	NoPM("NoPM", "Number of Public Methods", "The number of all declared public methods of a class."),
	
	NoA("NoA", "Number of Declared Attributes", "The number of all declared attributes of a class."),
	
	NoPA("NoPA", "Number of Public Attributes", "The number of all declared public attributes of a class."),
	
	Ca("Ca", "Afferent Coupling", "The number of classes that depend upon this class."),
	
	Ce("Ce", "Efferent Coupling", "The number of classes that this class depends upon."), 
	
	LMLoC("LMLoC", "Longest Method Lines of Code", "The number of the lines of code of the longest declared method of a class."),
	
	LPL("LPL", "Longest Parameter List", "The longest parameter list of a declared method of a class."),
	
	ECC("ECC", "Exceptional Cyclomatic Complexity", "The cyclomatic complexty of inside try, catch, and finally blocks plus the number of throw and methods with throws of a class."),
	
	TCC("TCC", "Try Block Cyclomatic Complexity", "The cyclomatic complexty inside a try block of a class."),
	
	HCC("HCC", "Catch Block Cyclomatic Complexity", "The cyclomatic complexty inside a catch block of a class."),
	
	FCC("FCC", "Finally Block Cyclomatic Complexity", "The cyclomatic complexty inside a finally block of a class."),
	
	TFC("TFC", "Troubleness Factor per Class", "The number of methods called by a class that signals an exception back."),
	
	TLoC("TLoC", "Try Block Lines of Code", "The number of lines of code of all try blocks of a class."), 
	
	CLoC("CLoC", "Catch Block Lines of Code", "The number of lines of code of all catch blocks of a class."), 
	
	FLoC("FLoC", "Finally Block Lines of Code", "The number of lines of code of all finally blocks of a class."), 
	
	RCo("RCo", "Raising Coupling", "The number of different exceptions types raised (throw) by a class."), 
	
	SCo("SCo", "Signaling  Coupling", "The number of different exceptions types signaled (throws) by a class."), 
	
	HCo("HCo", "Hadling  Coupling", "The number of different exceptions types handled (catch) by a class."), 
	
	NoR("NoR", "Number of Raisings", "The number of exceptions raised (throw) by a class."), 
	
	NoS("NoS", "Number of Signalings", "The number of exceptions signaled (throws) by a class."), 
	
	NoH("NoH", "Number fo Handlings", "The number of exceptions handled (catch) by a class."), 
	
	NoCRN("NoCRN", "Number of Catch and Return Null", "The number of handlers that catches an exception an returns null."),
	
	NoCA("NoCA", "Number of Catch and Abort", "The number of handlers that catches an exception and exits."),
	
	NoOC("NoOC", "Number of Over-Catches", "The number of handlers that catches multiple exception types."),
	
	NoOCA("NoOCA", "Number of Over-Catches and Abort", "The number of handlers that catches multiple exception types and exits."),
	
	NoNPB("NoNPB", "Number of Nested Protected Blocks", "The number of protected block (try) declared inside an existent protected block."),
	
	NoGH("NoGH", "Number of Generic Handlings", "The number of general exceptions handled by a class."), 
	
	NoGS("NoGS", "Number of Generic Signalings", "The number of general exceptions signaled by a class."), 
	
	NoEH("NoEH", "Number of Empty Handlings", "The number of empty catch blocks of a class."), 
	
	NoDR("NoDR", "Number of Destructive Remappings", "The number of exception type remappings that no preserving the exception stack trace."), 
	
	NoCI("NoCI", "Number of Catch and Ignore", "The number of handlers who catches an exception and do not uses it anymore in the handler block."),
	
	NoSF("NoSF", "Number of Signalings in Finally Blocks", "The number of exception raisings in cleanup (finally) blocks."),
	
	NoSKS("NoSKS", "Number of Signaling the Kitchen Sink", "The number of signalers that signals more then one exception type.");
	
	private final String shortName;
	
	private final String fullName;
	
	private final String description;

	Metric(String shortName, String fullName) {
		this.shortName = shortName;
		this.fullName = fullName;
		this.description = "";		
	}
	
	Metric(String shortName, String fullName, String description) {
		this.shortName = shortName;
		this.fullName = fullName;
		this.description = description;
	}

	public String getShortName() {
		return shortName;
	}
	
	public String getFullName() {
		return fullName;
	}
	
	public String getDescription() {
		return description;
	}
}
