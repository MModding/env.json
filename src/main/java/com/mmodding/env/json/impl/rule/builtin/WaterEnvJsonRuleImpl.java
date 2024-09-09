package com.mmodding.env.json.impl.rule.builtin;

import com.mmodding.env.json.api.EnvJsonVisitor;
import com.mmodding.env.json.api.RelativeLocation;
import com.mmodding.env.json.api.rule.builtin.WaterEnvJsonRule;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public record WaterEnvJsonRuleImpl(RelativeLocation location) implements WaterEnvJsonRule {

	@Override
	public boolean apply(EnvJsonVisitor visitor) {
		return visitor.applyWater(this.location);
	}
}
