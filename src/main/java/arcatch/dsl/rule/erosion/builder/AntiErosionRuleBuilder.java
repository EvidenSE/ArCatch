package arcatch.dsl.rule.erosion.builder;

import java.util.ArrayList;
import java.util.List;

import arcatch.dsl.compartment.grammar.Compartment;
import arcatch.dsl.rule.erosion.grammar.AntiErosionRule;
import arcatch.dsl.rule.common.Criticality;
import arcatch.dsl.rule.erosion.grammar.AERBegin;
import arcatch.dsl.rule.erosion.grammar.AERBody;
import arcatch.dsl.rule.erosion.grammar.AERCanNot;
import arcatch.dsl.rule.erosion.grammar.AERCanOnly;
import arcatch.dsl.rule.erosion.grammar.AERCommon;
import arcatch.dsl.rule.erosion.grammar.AEREnd;
import arcatch.dsl.rule.erosion.grammar.AERLabel;
import arcatch.dsl.rule.erosion.grammar.AERMust;
import arcatch.dsl.rule.erosion.grammar.AEROnlyCan;
import arcatch.dsl.rule.erosion.grammar.AERTo;
import arcatch.dsl.rule.erosion.grammar.AERToOrEnd;
import arcatch.dsl.rule.erosion.impl.factory.AntiErosionRuleFactory;
import arcatch.dsl.rule.erosion.impl.factory.AntiErosionRuleType;

public class AntiErosionRuleBuilder implements AERBegin, AERLabel, AERBody, AERCommon, AEROnlyCan, AERCanOnly,
		AERCanNot, AERMust, AERTo, AERToOrEnd, AEREnd {

	private static long ID = 1;

	private String label = "";

	private AntiErosionRuleType ruleType;

	private Criticality criticality;

	private List<Compartment> fromNormalCompartments = new ArrayList<>();

	private List<Compartment> toNormalCompartments = new ArrayList<>();

	private List<Compartment> exceptionalCompartments = new ArrayList<>();

	private List<Compartment> toExceptionalCompartments = new ArrayList<>();

	@Override
	public AntiErosionRule build() {
		AntiErosionRule rule = AntiErosionRuleFactory.create(ruleType, genId());
		rule.setLabel(this.label);
		rule.setCriticality(criticality);
		rule.setFromNormalCompartments(fromNormalCompartments);
		rule.setToNormalCompartments(toNormalCompartments);
		rule.setExceptionalCompartments(exceptionalCompartments);
		rule.setToExceptionalCompartments(toExceptionalCompartments);
		return rule;
	}

	@Override
	public AEREnd upTo(Compartment... normals) {
		fillToNormalCompartments(normals);

		switch (this.ruleType) {
		case OnlyCanSignal:
			this.ruleType = AntiErosionRuleType.OnlyCanSignalUpTo;
			break;

		case CanOnlySignal:
			this.ruleType = AntiErosionRuleType.CanOnlySignalUpTo;
			break;

		case CannotSignal:
			this.ruleType = AntiErosionRuleType.CannotSignalUpTo;
			break;

		case MustSignal:
			this.ruleType = AntiErosionRuleType.MustSignalUpTo;
			break;

		default:
			break;
		}
		return this;
	}

	@Override
	public AEREnd to(Compartment... exceptionals) {
		fillToExceptionalCompartments(exceptionals);
		return this;
	}

	@Override
	public AEREnd mustRaise(Compartment... exceptionals) {
		fillExceptionalCompartments(exceptionals);
		this.ruleType = AntiErosionRuleType.MustRaise;
		return this;
	}

	@Override
	public AEREnd mustReraise(Compartment... exceptionals) {
		fillExceptionalCompartments(exceptionals);
		this.ruleType = AntiErosionRuleType.MustReraise;
		return this;
	}

	@Override
	public AERTo mustRemap(Compartment... exceptionals) {
		fillExceptionalCompartments(exceptionals);
		this.ruleType = AntiErosionRuleType.MustRemap;
		return this;
	}

	@Override
	public AERToOrEnd mustSignal(Compartment... exceptionals) {
		fillExceptionalCompartments(exceptionals);
		this.ruleType = AntiErosionRuleType.MustSignal;
		return this;
	}

	@Override
	public AEREnd mustHandle(Compartment... exceptionals) {
		fillExceptionalCompartments(exceptionals);
		this.ruleType = AntiErosionRuleType.MustHandle;
		return this;
	}

	@Override
	public AEREnd mustCall(Compartment... normals) {
		fillToNormalCompartments(normals);
		this.ruleType = AntiErosionRuleType.MustCall;
		return this;
	}

	@Override
	public AEREnd mustCreate(Compartment... normals) {
		fillToNormalCompartments(normals);
		this.ruleType = AntiErosionRuleType.MustCreate;
		return this;
	}

	@Override
	public AEREnd mustExtend(Compartment... normals) {
		fillToNormalCompartments(normals);
		this.ruleType = AntiErosionRuleType.MustExtend;
		return this;
	}

	@Override
	public AEREnd mustImplement(Compartment... normals) {
		fillToNormalCompartments(normals);
		this.ruleType = AntiErosionRuleType.MustImplement;
		return this;
	}

	@Override
	public AEREnd cannotRaise(Compartment... exceptionals) {
		fillExceptionalCompartments(exceptionals);
		this.ruleType = AntiErosionRuleType.CannotRaise;
		return this;
	}

	@Override
	public AEREnd cannotReraise(Compartment... exceptionals) {
		fillExceptionalCompartments(exceptionals);
		this.ruleType = AntiErosionRuleType.CannotReraise;
		return this;
	}

	@Override
	public AERTo cannotRemap(Compartment... exceptionals) {
		fillExceptionalCompartments(exceptionals);
		this.ruleType = AntiErosionRuleType.CannotRemap;
		return this;
	}

	@Override
	public AERToOrEnd cannotSignal(Compartment... exceptionals) {
		fillExceptionalCompartments(exceptionals);
		this.ruleType = AntiErosionRuleType.CannotSignal;
		return this;
	}

	@Override
	public AEREnd cannotHandle(Compartment... exceptionals) {
		fillExceptionalCompartments(exceptionals);
		this.ruleType = AntiErosionRuleType.CannotHandle;
		return this;
	}

	@Override
	public AEREnd cannotCall(Compartment... normals) {
		fillToNormalCompartments(normals);
		this.ruleType = AntiErosionRuleType.CannotCall;
		return this;
	}

	@Override
	public AEREnd cannotCreate(Compartment... normals) {
		fillToNormalCompartments(normals);
		this.ruleType = AntiErosionRuleType.CannotCreate;
		return this;
	}

	@Override
	public AEREnd cannotExtend(Compartment... normals) {
		fillToNormalCompartments(normals);
		this.ruleType = AntiErosionRuleType.CannotExtend;
		return this;
	}

	@Override
	public AEREnd cannotImplement(Compartment... normals) {
		fillToNormalCompartments(normals);
		this.ruleType = AntiErosionRuleType.CannotImplement;
		return this;
	}

	@Override
	public AEREnd canRaiseOnly(Compartment... exceptionals) {
		fillExceptionalCompartments(exceptionals);
		this.ruleType = AntiErosionRuleType.CanOnlyRaise;
		return this;
	}

	@Override
	public AEREnd canReraiseOnly(Compartment... exceptionals) {
		fillExceptionalCompartments(exceptionals);
		this.ruleType = AntiErosionRuleType.CanOnlyReraise;
		return this;
	}

	@Override
	public AERTo canRemapOnly(Compartment... exceptionals) {
		fillExceptionalCompartments(exceptionals);
		this.ruleType = AntiErosionRuleType.CanOnlyRemap;
		return this;
	}

	@Override
	public AERToOrEnd canSignalOnly(Compartment... exceptionals) {
		fillExceptionalCompartments(exceptionals);
		this.ruleType = AntiErosionRuleType.CanOnlySignal;
		return this;
	}

	@Override
	public AEREnd canHandleOnly(Compartment... exceptionals) {
		fillExceptionalCompartments(exceptionals);
		this.ruleType = AntiErosionRuleType.CanOnlyHandle;
		return this;
	}

	@Override
	public AEREnd canCallOnly(Compartment... normals) {
		fillToNormalCompartments(normals);
		this.ruleType = AntiErosionRuleType.CanOnlyCall;
		return this;
	}

	@Override
	public AEREnd canCreateOnly(Compartment... normals) {
		fillToNormalCompartments(normals);
		this.ruleType = AntiErosionRuleType.CanOnlyCreate;
		return this;
	}

	@Override
	public AEREnd canExtendOnly(Compartment... normals) {
		fillToNormalCompartments(normals);
		this.ruleType = AntiErosionRuleType.CanOnlyExtend;
		return this;
	}

	@Override
	public AEREnd canImplementOnly(Compartment... normals) {
		fillToNormalCompartments(normals);
		this.ruleType = AntiErosionRuleType.CanOnlyImplement;
		return this;
	}

	@Override
	public AEREnd canRaise(Compartment... exceptionals) {
		fillExceptionalCompartments(exceptionals);
		this.ruleType = AntiErosionRuleType.OnlyCanRaise;
		return this;
	}

	@Override
	public AEREnd canReraise(Compartment... exceptionals) {
		fillExceptionalCompartments(exceptionals);
		this.ruleType = AntiErosionRuleType.OnlyCanReraise;
		return this;
	}

	@Override
	public AERTo canRemap(Compartment... exceptionals) {
		fillExceptionalCompartments(exceptionals);
		this.ruleType = AntiErosionRuleType.OnlyCanRemap;
		return this;
	}

	@Override
	public AERToOrEnd canSignal(Compartment... exceptionals) {
		fillExceptionalCompartments(exceptionals);
		this.ruleType = AntiErosionRuleType.OnlyCanSignal;
		return this;
	}

	@Override
	public AEREnd canHandle(Compartment... exceptionals) {
		fillExceptionalCompartments(exceptionals);
		this.ruleType = AntiErosionRuleType.OnlyCanHandle;
		return this;
	}

	@Override
	public AEREnd canCall(Compartment... normals) {
		fillToNormalCompartments(normals);
		this.ruleType = AntiErosionRuleType.OnlyCanCall;
		return this;
	}

	@Override
	public AEREnd canCreate(Compartment... normals) {
		fillToNormalCompartments(normals);
		this.ruleType = AntiErosionRuleType.OnlyCanCreate;
		return this;
	}

	@Override
	public AEREnd canExtend(Compartment... normals) {
		fillToNormalCompartments(normals);
		this.ruleType = AntiErosionRuleType.OnlyCanExtend;
		return this;
	}

	@Override
	public AEREnd canImplement(Compartment... normals) {
		fillToNormalCompartments(normals);
		this.ruleType = AntiErosionRuleType.OnlyCanImplement;
		return this;
	}

	@Override
	public AEROnlyCan only(Compartment... normals) {
		this.fillFromNormalCompartments(normals);
		return this;
	}

	@Override
	public AERCommon compartiment(Compartment... normals) {
		this.fillFromNormalCompartments(normals);
		return this;
	}

	@Override
	public AERBody criticality(Criticality level) {
		this.criticality = level;
		return this;
	}

	@Override
	public AERBody label(String label) {
		this.label = label;
		return this;
	}

	private void fillFromNormalCompartments(Compartment... normals) {
		for (Compartment normal : normals) {
			this.fromNormalCompartments.add(normal);
		}
	}

	private void fillToNormalCompartments(Compartment... normals) {
		for (Compartment normal : normals) {
			this.toNormalCompartments.add(normal);
		}
	}

	private void fillExceptionalCompartments(Compartment... exceptionals) {
		for (Compartment exeption : exceptionals) {
			this.exceptionalCompartments.add(exeption);
		}
	}

	private void fillToExceptionalCompartments(Compartment... exceptionals) {
		for (Compartment exceptional : exceptionals) {
			this.toExceptionalCompartments.add(exceptional);
		}
	}

	private static String genId() {
		return (ID < 10) ? ("AER0" + ID++) : ("AER" + ID++);
	}

	public static void resetIdCount() {
		ID = 1;
	}

}
