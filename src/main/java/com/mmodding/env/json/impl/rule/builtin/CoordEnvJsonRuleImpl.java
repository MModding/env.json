package com.mmodding.env.json.impl.rule.builtin;

import com.mmodding.env.json.api.EnvJsonVisitor;
import com.mmodding.env.json.api.rule.builtin.CoordEnvJsonRule;
import org.jetbrains.annotations.ApiStatus;

import java.util.Arrays;

@ApiStatus.Internal
public record CoordEnvJsonRuleImpl(Coord coord, Comparator comparator, int value) implements CoordEnvJsonRule {

	public static Comparator comparatorFromString(String string) {
		for (Comparator comparator : Comparator.values()) {
			if (Arrays.stream(comparator.representations()).toList().contains(string)) {
				return comparator;
			}
		}
		return null;
	}

	@Override
	public boolean apply(EnvJsonVisitor visitor) {
		return switch (this.coord) {
			case X -> visitor.applyXCoord(i -> this.comparator.operation().apply(i, this.value));
			case Y -> visitor.applyYCoord(i -> this.comparator.operation().apply(i, this.value));
			case Z -> visitor.applyZCoord(i -> this.comparator.operation().apply(i, this.value));
		};
	}
}
