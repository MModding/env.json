package com.mmodding.env.json.impl.rule;

import com.mmodding.env.json.api.rule.EnvJsonRule;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public final class EnvJsonRuleImpl {

	public EnvJsonRuleImpl() {
		throw new IllegalStateException("EnvJsonRuleImpl only contains static definitions");
	}

	@SuppressWarnings("unchecked")
	public static <T extends EnvJsonRule> T safeCast(EnvJsonRule rule, Class<T> ignored) {
		return (T) rule;
	}
}
