package fr.firstmegagame4.env.json.api.rule;

import net.minecraft.util.StringIdentifiable;

public interface CoordEnvJsonRule extends EnvJsonRule {

	Coord coord();

	Comparator comparator();

	enum Coord {
		X,
		Y,
		Z
	}

	enum Comparator implements StringIdentifiable {
		INFERIOR("<"),
		INFERIOR_FLAT_EQUALS("<="),
		FLAT_EQUALS("=="),
		SUPERIOR_FLAT_EQUALS(">="),
		SUPERIOR(">");

		private final String representation;

		Comparator(String representation) {
			this.representation = representation;
		}

		@Override
		public String asString() {
			return this.representation;
		}
	}
}
