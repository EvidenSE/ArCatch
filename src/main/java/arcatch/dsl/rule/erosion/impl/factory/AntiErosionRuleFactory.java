package arcatch.dsl.rule.erosion.impl.factory;

import arcatch.dsl.rule.erosion.grammar.AntiErosionRule;
import arcatch.dsl.rule.erosion.impl.canOnly.CanOnlyCall;
import arcatch.dsl.rule.erosion.impl.canOnly.CanOnlyCreate;
import arcatch.dsl.rule.erosion.impl.canOnly.CanOnlyExtend;
import arcatch.dsl.rule.erosion.impl.canOnly.CanOnlyHandle;
import arcatch.dsl.rule.erosion.impl.canOnly.CanOnlyImplement;
import arcatch.dsl.rule.erosion.impl.canOnly.CanOnlyRaise;
import arcatch.dsl.rule.erosion.impl.canOnly.CanOnlyRemap;
import arcatch.dsl.rule.erosion.impl.canOnly.CanOnlyReraise;
import arcatch.dsl.rule.erosion.impl.canOnly.CanOnlySignal;
import arcatch.dsl.rule.erosion.impl.canOnly.CanOnlySignalTo;
import arcatch.dsl.rule.erosion.impl.cannot.CannotCall;
import arcatch.dsl.rule.erosion.impl.cannot.CannotCreate;
import arcatch.dsl.rule.erosion.impl.cannot.CannotExtend;
import arcatch.dsl.rule.erosion.impl.cannot.CannotHandle;
import arcatch.dsl.rule.erosion.impl.cannot.CannotImplement;
import arcatch.dsl.rule.erosion.impl.cannot.CannotRaise;
import arcatch.dsl.rule.erosion.impl.cannot.CannotRemap;
import arcatch.dsl.rule.erosion.impl.cannot.CannotReraise;
import arcatch.dsl.rule.erosion.impl.cannot.CannotSignal;
import arcatch.dsl.rule.erosion.impl.cannot.CannotSignalTo;
import arcatch.dsl.rule.erosion.impl.must.MustCall;
import arcatch.dsl.rule.erosion.impl.must.MustCreate;
import arcatch.dsl.rule.erosion.impl.must.MustExtend;
import arcatch.dsl.rule.erosion.impl.must.MustHandle;
import arcatch.dsl.rule.erosion.impl.must.MustImplement;
import arcatch.dsl.rule.erosion.impl.must.MustRaise;
import arcatch.dsl.rule.erosion.impl.must.MustRemap;
import arcatch.dsl.rule.erosion.impl.must.MustReraise;
import arcatch.dsl.rule.erosion.impl.must.MustSignal;
import arcatch.dsl.rule.erosion.impl.must.MustSignalTo;
import arcatch.dsl.rule.erosion.impl.onlyCan.OnlyCanCall;
import arcatch.dsl.rule.erosion.impl.onlyCan.OnlyCanCreate;
import arcatch.dsl.rule.erosion.impl.onlyCan.OnlyCanExtend;
import arcatch.dsl.rule.erosion.impl.onlyCan.OnlyCanHandle;
import arcatch.dsl.rule.erosion.impl.onlyCan.OnlyCanImplement;
import arcatch.dsl.rule.erosion.impl.onlyCan.OnlyCanRaise;
import arcatch.dsl.rule.erosion.impl.onlyCan.OnlyCanRemap;
import arcatch.dsl.rule.erosion.impl.onlyCan.OnlyCanReraise;
import arcatch.dsl.rule.erosion.impl.onlyCan.OnlyCanSignal;
import arcatch.dsl.rule.erosion.impl.onlyCan.OnlyCanSignalTo;

public class AntiErosionRuleFactory {

	public static AntiErosionRule create(AntiErosionRuleType type, String id) {
		AntiErosionRule rule = null;
		switch (type) {

		case CannotRaise:
			rule = new CannotRaise(id);
			break;

		case CannotReraise:
			rule = new CannotReraise(id);
			break;

		case CannotRemap:
			rule = new CannotRemap(id);
			break;

		case CannotSignal:
			rule = new CannotSignal(id);
			break;

		case CannotSignalUpTo:
			rule = new CannotSignalTo(id);
			break;

		case CannotHandle:
			rule = new CannotHandle(id);
			break;

		case CannotCall:
			rule = new CannotCall(id);
			break;

		case CannotExtend:
			rule = new CannotExtend(id);
			break;

		case CannotCreate:
			rule = new CannotCreate(id);
			break;

		case CannotImplement:
			rule = new CannotImplement(id);
			break;

		case CanOnlyRaise:
			rule = new CanOnlyRaise(id);
			break;

		case CanOnlyReraise:
			rule = new CanOnlyReraise(id);
			break;

		case CanOnlyRemap:
			rule = new CanOnlyRemap(id);
			break;

		case CanOnlySignal:
			rule = new CanOnlySignal(id);
			break;

		case CanOnlySignalUpTo:
			rule = new CanOnlySignalTo(id);
			break;

		case CanOnlyHandle:
			rule = new CanOnlyHandle(id);
			break;

		case CanOnlyCall:
			rule = new CanOnlyCall(id);
			break;

		case CanOnlyExtend:
			rule = new CanOnlyExtend(id);
			break;

		case CanOnlyImplement:
			rule = new CanOnlyImplement(id);
			break;

		case CanOnlyCreate:
			rule = new CanOnlyCreate(id);
			break;

		case OnlyCanRaise:
			rule = new OnlyCanRaise(id);
			break;

		case OnlyCanReraise:
			rule = new OnlyCanReraise(id);
			break;

		case OnlyCanRemap:
			rule = new OnlyCanRemap(id);
			break;

		case OnlyCanSignal:
			rule = new OnlyCanSignal(id);
			break;

		case OnlyCanSignalUpTo:
			rule = new OnlyCanSignalTo(id);
			break;

		case OnlyCanHandle:
			rule = new OnlyCanHandle(id);
			break;

		case OnlyCanCall:
			rule = new OnlyCanCall(id);
			break;

		case OnlyCanExtend:
			rule = new OnlyCanExtend(id);
			break;

		case OnlyCanImplement:
			rule = new OnlyCanImplement(id);
			break;

		case OnlyCanCreate:
			rule = new OnlyCanCreate(id);
			break;

		case MustRaise:
			rule = new MustRaise(id);
			break;

		case MustReraise:
			rule = new MustReraise(id);
			break;

		case MustRemap:
			rule = new MustRemap(id);
			break;

		case MustSignal:
			rule = new MustSignal(id);
			break;

		case MustSignalUpTo:
			rule = new MustSignalTo(id);
			break;

		case MustHandle:
			rule = new MustHandle(id);
			break;

		case MustCall:
			rule = new MustCall(id);
			break;

		case MustExtend:
			rule = new MustExtend(id);
			break;

		case MustImplement:
			rule = new MustImplement(id);
			break;

		case MustCreate:
			rule = new MustCreate(id);
			break;

		default:
			break;
		}

		return rule;
	}
}
