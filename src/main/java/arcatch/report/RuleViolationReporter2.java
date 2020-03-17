package arcatch.report;

import java.util.Set;

import arcatch.dsl.rule.erosion.grammar.AntiErosionRule;
import arcatch.dsl.rule.erosion.impl.AntiErosionRuleViolation;
import arcatch.dsl.rule.erosion.impl.kind.CallRule;
import arcatch.dsl.rule.erosion.impl.kind.CreateRule;
import arcatch.dsl.rule.erosion.impl.kind.ExtendRule;
import arcatch.dsl.rule.erosion.impl.kind.HandleRule;
import arcatch.dsl.rule.erosion.impl.kind.ImplementRule;
import arcatch.dsl.rule.erosion.impl.kind.RaiseRule;
import arcatch.dsl.rule.erosion.impl.kind.RemapRule;
import arcatch.dsl.rule.erosion.impl.kind.ReraiseRule;
import arcatch.dsl.rule.erosion.impl.kind.RuleKing;
import arcatch.dsl.rule.erosion.impl.kind.SignalRule;
import arcatch.dsl.rule.erosion.impl.kind.SignalToRule;
import arcatch.dsl.rule.erosion.impl.relation.BinaryRelation;
import arcatch.dsl.rule.erosion.impl.relation.TernaryRelation;
import arcatch.model.Model;
import arcatch.model.Operation;
import arcatch.model.Unit;
import arcatch.util.Util;

public class RuleViolationReporter2 implements Reporter {

	@Override
	public void print() {
		StringBuffer report = new StringBuffer("Rule Id;Rule Label;Rule Description;Criticality;Violation Kind;FomUnit Id;Violation Label;ToUnit Id\n");
		for (AntiErosionRule rule : Model.getFailingAntiErosionRules()) {
			StringBuffer buffer = new StringBuffer();
			buffer.append(rule.getId());
			buffer.append(";");
			buffer.append(rule.getLabel());
			buffer.append(";");
			buffer.append(rule.getDescription().trim());
			buffer.append(";");
			buffer.append(rule.getCriticality().getLabel());
			buffer.append(";");
			AntiErosionRuleViolation violation = rule.getViolation();
			if (rule instanceof CallRule) {
				buffer.append(RuleKing.Call.getLabel());
				buffer.append(";");
				String firstPart = buffer.toString();
				if (violation.getOperationToOperationsViolations() != null
						&& !violation.getOperationToOperationsViolations().isEmpty()) {
					for (BinaryRelation<Operation, Set<Operation>> violationInstance : violation
							.getOperationToOperationsViolations()) {
						buffer = new StringBuffer(firstPart);
						buffer.append(violationInstance.getLeft().getOwner().getQualifiedName());
						buffer.append(";");
						String secondPart = buffer.toString();
						for (Operation operation : violationInstance.getRight()) {
							buffer = new StringBuffer(secondPart);
							buffer.append(operation.getSignature());
							buffer.append(";");
							buffer.append(operation.getOwner().getQualifiedName());
							buffer.append("\n");
							report.append(buffer.toString());
						}
					}
				}
			} else if (rule instanceof CreateRule) {
				buffer.append(RuleKing.Creation.getLabel());
				buffer.append(";");
				String firstPart = buffer.toString();
				if (violation.getOperationToOperationsViolations() != null
						&& !violation.getOperationToOperationsViolations().isEmpty()) {
					for (BinaryRelation<Operation, Set<Operation>> violationInstance : violation
							.getOperationToOperationsViolations()) {
						buffer = new StringBuffer(firstPart);
						buffer.append(violationInstance.getLeft().getOwner().getQualifiedName());
						buffer.append(";");
						String secondPart = buffer.toString();
						for (Operation operation : violationInstance.getRight()) {
							buffer = new StringBuffer(secondPart);
							buffer.append(operation.getSignature());
							buffer.append(";");
							buffer.append(operation.getOwner().getQualifiedName());
							buffer.append("\n");
							report.append(buffer.toString());
						}
					}
				}
			} else if (rule instanceof ExtendRule) {
				buffer.append(RuleKing.Extension.getLabel());
				buffer.append(";");
				String firstPart = buffer.toString();
				if (violation.getUnitToUnitViolations() != null && !violation.getUnitToUnitViolations().isEmpty()) {
					for (BinaryRelation<Unit, Unit> violationInstance : violation.getUnitToUnitViolations()) {
						buffer = new StringBuffer(firstPart);
						buffer.append(violationInstance.getLeft().getQualifiedName());
						buffer.append(";");
						buffer.append("extends");
						buffer.append(";");
						buffer.append(violationInstance.getRight().getQualifiedName());
						buffer.append("\n");
						report.append(buffer.toString());
					}
				}
			} else if (rule instanceof ImplementRule) {
				buffer.append(RuleKing.Implementation.getLabel());
				buffer.append(";");
				String firstPart = buffer.toString();
				if (violation.getUnitToUnitViolations() != null && !violation.getUnitToUnitViolations().isEmpty()) {
					for (BinaryRelation<Unit, Unit> violationInstance : violation.getUnitToUnitViolations()) {
						buffer = new StringBuffer(firstPart);
						buffer.append(violationInstance.getLeft().getQualifiedName());
						buffer.append(";");
						buffer.append("implements");
						buffer.append(";");
						buffer.append(violationInstance.getRight().getQualifiedName());
						buffer.append("\n");
						report.append(buffer.toString());
					}
				}
			} else if (rule instanceof RaiseRule) {
				buffer.append(RuleKing.Raising.getLabel());
				buffer.append(";");
				String firstPart = buffer.toString();
				if (violation.getOperationToUnitsViolations() != null
						&& !violation.getOperationToUnitsViolations().isEmpty()) {
					for (BinaryRelation<Operation, Set<Unit>> violationInstance : violation
							.getOperationToUnitsViolations()) {
						buffer = new StringBuffer(firstPart);
						buffer.append(violationInstance.getLeft().getOwner().getQualifiedName());
						buffer.append(";");
						String secondPart = buffer.toString();
						for (Unit unit : violationInstance.getRight()) {
							buffer = new StringBuffer(secondPart);
							buffer.append(violationInstance.getLeft().getSignature());
							buffer.append(";");
							buffer.append(unit.getQualifiedName());
							buffer.append("\n");
							report.append(buffer.toString());
						}
					}
				}
			} else if (rule instanceof HandleRule) {
				buffer.append(RuleKing.Handling.getLabel());
				buffer.append(";");
				String firstPart = buffer.toString();
				if (violation.getOperationToUnitsViolations() != null
						&& !violation.getOperationToUnitsViolations().isEmpty()) {
					for (BinaryRelation<Operation, Set<Unit>> violationInstance : violation
							.getOperationToUnitsViolations()) {
						buffer = new StringBuffer(firstPart);
						buffer.append(violationInstance.getLeft().getOwner().getQualifiedName());
						buffer.append(";");
						String secondPart = buffer.toString();
						if (!violationInstance.getRight().isEmpty()) {
							for (Unit unit : violationInstance.getRight()) {
								buffer = new StringBuffer(secondPart);
								buffer.append(violationInstance.getLeft().getSignature());
								buffer.append(";");
								buffer.append(unit.getQualifiedName());
								buffer.append("\n");
								report.append(buffer.toString());
							}
						} else {
							buffer = new StringBuffer(secondPart);
							buffer.append(";");
							buffer.append("\n");
							report.append(buffer.toString());
						}
					}
				}
			} else if (rule instanceof SignalRule) {
				buffer.append(RuleKing.Signaling.getLabel());
				buffer.append(";");
				String firstPart = buffer.toString();
				if (violation.getOperationToUnitsViolations() != null
						&& !violation.getOperationToUnitsViolations().isEmpty()) {
					for (BinaryRelation<Operation, Set<Unit>> violationInstance : violation
							.getOperationToUnitsViolations()) {
						buffer = new StringBuffer(firstPart);
						buffer.append(violationInstance.getLeft().getOwner().getQualifiedName());
						buffer.append(";");
						String secondPart = buffer.toString();
						if (!violationInstance.getRight().isEmpty()) {
							for (Unit unit : violationInstance.getRight()) {
								buffer = new StringBuffer(secondPart);
								buffer.append(violationInstance.getLeft().getSignature());
								buffer.append(";");
								buffer.append(unit.getQualifiedName());
								buffer.append("\n");
								report.append(buffer.toString());
							}
						} else {
							buffer = new StringBuffer(secondPart);
							buffer.append(";");
							buffer.append("\n");
							report.append(buffer.toString());
						}
					}
				}
			} else if (rule instanceof RemapRule) {
				buffer.append(RuleKing.Remapping.getLabel());
				buffer.append(";");
				String firstPart = buffer.toString();
				if (violation.getOperationToUnitToUnitViolations() != null
						&& !violation.getOperationToUnitToUnitViolations().isEmpty()) {
					for (TernaryRelation<Operation, Unit, Unit> violationInstance : violation
							.getOperationToUnitToUnitViolations()) {
						buffer = new StringBuffer(firstPart);
						buffer.append(violationInstance.getLeft().getOwner().getQualifiedName());
						buffer.append(";");
						buffer.append(violationInstance.getMiddle().getQualifiedName());
						buffer.append(";");
						buffer.append(violationInstance.getRight().getQualifiedName());
						buffer.append("\n");
						report.append(buffer.toString());
					}
				}
			} else if (rule instanceof ReraiseRule) {
				buffer.append(RuleKing.Reraising.getLabel());
				buffer.append(";");
				String firstPart = buffer.toString();
				if (violation.getOperationToUnitsViolations() != null
						&& !violation.getOperationToUnitsViolations().isEmpty()) {
					for (BinaryRelation<Operation, Set<Unit>> violationInstance : violation
							.getOperationToUnitsViolations()) {
						buffer = new StringBuffer(firstPart);
						buffer.append(violationInstance.getLeft().getOwner().getQualifiedName());
						buffer.append(";");
						String secondPart = buffer.toString();
						if (!violationInstance.getRight().isEmpty()) {
							for (Unit unit : violationInstance.getRight()) {
								buffer = new StringBuffer(secondPart);
								buffer.append(violationInstance.getLeft().getSignature());
								buffer.append(";");
								buffer.append(unit.getQualifiedName());
								buffer.append("\n");
								report.append(buffer.toString());
							}
						} else {
							buffer = new StringBuffer(secondPart);
							buffer.append(";");
							buffer.append("\n");
							report.append(buffer.toString());
						}
					}
				}
			} else if (rule instanceof SignalToRule) {
				buffer.append(RuleKing.SignalingTo.getLabel());
				buffer.append(";");
				String firstPart = buffer.toString();
				if (violation.getOperationToUnitsToOperationViolations() != null
						&& !violation.getOperationToUnitsToOperationViolations().isEmpty()) {
					for (TernaryRelation<Operation, Set<Unit>, Operation> violationInstance : violation
							.getOperationToUnitsToOperationViolations()) {
						buffer = new StringBuffer(firstPart);
						buffer.append(violationInstance.getLeft().getOwner().getQualifiedName());
						buffer.append(";");
						String secondPart = buffer.toString();
						if (!violationInstance.getMiddle().isEmpty()) {
							for (Unit unit : violationInstance.getMiddle()) {
								buffer = new StringBuffer(secondPart);
								buffer.append(unit.getQualifiedName());
								buffer.append(";");
								buffer.append(violationInstance.getRight().getOwner().getQualifiedName());
								buffer.append("\n");
								report.append(buffer.toString());
							}
						} else {
							buffer = new StringBuffer(secondPart);
							buffer.append(";");
							buffer.append("\n");
							report.append(buffer.toString());
						}
					}
				}
			}
		}
		String projectLabel = Model.getConfiguration().getProjectName().toLowerCase().trim();
		projectLabel += Model.getConfiguration().getProjectVersion().toLowerCase().trim();
		Util.generateCSVFile(projectLabel + "-rule-violation", report.toString());
	}
}