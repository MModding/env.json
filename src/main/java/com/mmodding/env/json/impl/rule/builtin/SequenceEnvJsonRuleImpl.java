package com.mmodding.env.json.impl.rule.builtin;

import com.mmodding.env.json.api.EnvJsonVisitor;
import com.mmodding.env.json.api.rule.EnvJsonRule;
import com.mmodding.env.json.api.rule.builtin.SequenceEnvJsonRule;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;

@ApiStatus.Internal
public record SequenceEnvJsonRuleImpl(List<EnvJsonRule> rules) implements SequenceEnvJsonRule {

	@Override
	public boolean apply(EnvJsonVisitor visitor) {
		for (EnvJsonRule rule : this.rules) {
			if (!rule.apply(visitor)) {
				return false;
			}
		}
		return true;
	}
}
