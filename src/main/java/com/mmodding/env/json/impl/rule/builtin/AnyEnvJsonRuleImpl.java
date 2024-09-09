package com.mmodding.env.json.impl.rule.builtin;

import com.mmodding.env.json.api.EnvJsonVisitor;
import com.mmodding.env.json.api.rule.builtin.AnyEnvJsonRule;
import com.mmodding.env.json.api.rule.EnvJsonRule;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;

@ApiStatus.Internal
public record AnyEnvJsonRuleImpl(List<EnvJsonRule> rules) implements AnyEnvJsonRule {

	@Override
	public boolean apply(EnvJsonVisitor visitor) {
		for (EnvJsonRule rule : this.rules) {
			if (rule.apply(visitor)) {
				return true;
			}
		}
		return false;
	}
}
