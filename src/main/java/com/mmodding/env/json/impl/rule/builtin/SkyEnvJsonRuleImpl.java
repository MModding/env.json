package com.mmodding.env.json.impl.rule.builtin;

import com.mmodding.env.json.api.EnvJsonVisitor;
import com.mmodding.env.json.api.RelativeLocation;
import com.mmodding.env.json.api.rule.builtin.SkyEnvJsonRule;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public record SkyEnvJsonRuleImpl(RelativeLocation location) implements SkyEnvJsonRule {

	@Override
	public boolean apply(EnvJsonVisitor visitor) {
		return visitor.applySky(this.location);
	}
}
