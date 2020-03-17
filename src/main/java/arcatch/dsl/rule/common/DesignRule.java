package arcatch.dsl.rule.common;

public interface DesignRule extends Comparable<DesignRule> {

	public String getId();

	public void setLabel(String label);

	public String getLabel();

	public Criticality getCriticality();

	public void setCriticality(Criticality criticality);

	public void setPassing(boolean status);

	public boolean isPassing();

	public boolean isChecked();

	public boolean check();
	
	public String getDescription();
	
}
