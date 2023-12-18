package fr.firstmegagame4.env.json.impl.rule;

import fr.firstmegagame4.env.json.api.rule.EnvJsonRule;

public abstract class EnvJsonRuleImpl implements EnvJsonRule {

	private final Type type;

	public EnvJsonRuleImpl(Type type) {
		this.type = type;
	}

	@SuppressWarnings("unchecked")
	public static <T extends EnvJsonRule> T safeCast(EnvJsonRule rule, Class<T> ignored) {
		return (T) rule;
	}

	@Override
	public Type type() {
		return this.type;
	}
}
