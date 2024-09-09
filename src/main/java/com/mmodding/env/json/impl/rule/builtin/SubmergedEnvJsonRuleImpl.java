package com.mmodding.env.json.impl.rule.builtin;

import com.mmodding.env.json.api.EnvJsonVisitor;
import com.mmodding.env.json.api.rule.builtin.SubmergedEnvJsonRule;

public record SubmergedEnvJsonRuleImpl(boolean submerged) implements SubmergedEnvJsonRule {

	@Override
	public boolean apply(EnvJsonVisitor visitor) {
		return visitor.applySubmerged(this.submerged);
	}
}
