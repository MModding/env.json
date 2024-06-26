package com.mmodding.env.json.impl.rule;

import com.mmodding.env.json.api.rule.EnvJsonRule;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
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
