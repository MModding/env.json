package com.mmodding.env.json.api.rule;

import com.mmodding.env.json.impl.rule.CoordEnvJsonRuleImpl;

import java.util.function.BiFunction;

public interface CoordEnvJsonRule extends EnvJsonRule {

	Coord coord();

	Comparator comparator();

	int value();

	enum Coord {
		X,
		Y,
		Z
	}

	enum Comparator {
		INFERIOR((provided, constant) -> provided < constant, "<"),
		INFERIOR_FLAT_EQUALS((provided, constant) -> provided <= constant, "<=", "=<"),
		FLAT_EQUALS(Integer::equals, "=="),
		SUPERIOR_FLAT_EQUALS((provided, constant) -> provided >= constant, ">=", "=>"),
		SUPERIOR((provided, constant) -> provided > constant, ">");

		private final BiFunction<Integer, Integer, Boolean> operation;
		private final String[] representations;

		Comparator(BiFunction<Integer, Integer, Boolean> operation, String... representations) {
			this.operation = operation;
			this.representations = representations;
		}

		public static Comparator fromString(String string) {
			return CoordEnvJsonRuleImpl.comparatorFromString(string);
		}

		public BiFunction<Integer, Integer, Boolean> operation() {
			return this.operation;
		}

		public String[] representations() {
			return this.representations;
		}
	}
}
