package arcatch.dsl.rule.erosion.impl.factory;

public enum AntiErosionRuleType {

	CannotRaise, CannotReraise, CannotRemap, CannotSignal, CannotSignalUpTo, CannotHandle, CannotCall, CannotExtend, CannotImplement, CannotCreate,

	OnlyCanRaise, OnlyCanReraise, OnlyCanRemap, OnlyCanSignal, OnlyCanSignalUpTo, OnlyCanHandle, OnlyCanCall, OnlyCanExtend, OnlyCanImplement, OnlyCanCreate,

	CanOnlyRaise, CanOnlyReraise, CanOnlyRemap, CanOnlySignal, CanOnlySignalUpTo, CanOnlyHandle, CanOnlyCall, CanOnlyExtend, CanOnlyImplement, CanOnlyCreate,

	MustRaise, MustReraise, MustRemap, MustSignal, MustSignalUpTo, MustHandle, MustCall, MustExtend, MustImplement, MustCreate;

}
