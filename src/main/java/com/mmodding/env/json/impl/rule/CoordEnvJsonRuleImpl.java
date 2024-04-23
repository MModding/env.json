package com.mmodding.env.json.impl.rule;

import com.mmodding.env.json.api.EnvJsonVisitor;
import com.mmodding.env.json.api.rule.CoordEnvJsonRule;
import org.jetbrains.annotations.ApiStatus;

import java.util.Arrays;

@ApiStatus.Internal
public class CoordEnvJsonRuleImpl extends EnvJsonRuleImpl implements CoordEnvJsonRule {

	private final Coord coord;
	private final Comparator comparator;
	private final int value;

    public CoordEnvJsonRuleImpl(Coord coord, Comparator comparator, int value) {
		super(
			switch (coord) {
				case X -> Type.X_COORD;
				case Y -> Type.Y_COORD;
				case Z -> Type.Z_COORD;
			}
		);
		this.coord = coord;
		this.comparator = comparator;
		this.value = value;
	}

	public static Comparator comparatorFromString(String string) {
		for (Comparator comparator : Comparator.values()) {
			if (Arrays.stream(comparator.representations()).toList().contains(string)) {
				return comparator;
			}
		}
		return null;
	}

    @Override
    public Coord coord() {
        return this.coord;
    }

    @Override
    public Comparator comparator() {
        return this.comparator;
    }

	@Override
	public int value() {
		return this.value;
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
