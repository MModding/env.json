package com.mmodding.env.json.impl.rule.builtin;

import com.mmodding.env.json.api.EnvJsonVisitor;
import com.mmodding.env.json.api.RelativeLocation;
import com.mmodding.env.json.api.rule.builtin.VoidEnvJsonRule;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public record VoidEnvJsonRuleImpl(RelativeLocation location) implements VoidEnvJsonRule {

	@Override
	public boolean apply(EnvJsonVisitor visitor) {
		return visitor.applyVoid(this.location);
	}
}
