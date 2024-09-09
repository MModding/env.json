package com.mmodding.env.json.impl.rule.builtin;

import com.mmodding.env.json.api.EnvJsonVisitor;
import com.mmodding.env.json.api.rule.EnvJsonRule;
import com.mmodding.env.json.api.rule.builtin.NotEnvJsonRule;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public record NotEnvJsonRuleImpl(EnvJsonRule rule) implements NotEnvJsonRule {

	@Override
	public boolean apply(EnvJsonVisitor visitor) {
		return !this.rule.apply(visitor);
	}
}
