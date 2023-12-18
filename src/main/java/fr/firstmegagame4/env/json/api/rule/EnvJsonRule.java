package fr.firstmegagame4.env.json.api.rule;

import fr.firstmegagame4.env.json.api.EnvJsonVisitor;
import fr.firstmegagame4.env.json.impl.rule.EnvJsonRuleImpl;

public interface EnvJsonRule {

	static <T extends EnvJsonRule> T safeCast(EnvJsonRule rule, Class<T> type) {
		return EnvJsonRuleImpl.safeCast(rule, type);
	}

	Type type();

	boolean apply(EnvJsonVisitor visitor);

	enum Type {

		/**
		 * Represents a sequence of combined rules with the AND operator.
		 */
		SEQUENCE,

		/**
		 * Represents a sequence of combined rules with the OR operator.
		 */
		ANY,

		/**
		 * Represents a dimension rule where the object should be.
		 */
		DIMENSION,

		/**
		 * Represents a biome rule where the object should be.
		 */
		BIOME,

		/**
		 * Represents an x coordinate comparator rule which is applied to the object.
		 */
		X_COORD,

		/**
		 * Represents a y coordinate comparator rule which is applied to the object.
		 */
		Y_COORD,

		/**
		 * Represents a z coordinate comparator rule which is applied to the object.
		 */
		Z_COORD,

		/**
		 * Represents a rule checking if the object os surrounded by water.
		 */
		SUBMERGED,

		/**
		 * Represents a rule checking if the object is below or above the sky limit.
		 */
		SKY,

		/**
		 * Represents a rule checking if the object is below or above the water level.
		 */
		WATER,

		/**
		 * Represents a rule checking if the object is below or above the void limit.
		 */
		VOID;
	}
}
