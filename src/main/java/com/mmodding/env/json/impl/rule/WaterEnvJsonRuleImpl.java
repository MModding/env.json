package com.mmodding.env.json.impl.rule;

import com.mmodding.env.json.api.EnvJsonVisitor;
import com.mmodding.env.json.api.rule.WaterEnvJsonRule;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class WaterEnvJsonRuleImpl extends EnvJsonRuleImpl implements WaterEnvJsonRule {

	private final Localization localization;

	public WaterEnvJsonRuleImpl(Localization localization) {
		super(Type.WATER);
		this.localization = localization;
	}

	@Override
	public boolean apply(EnvJsonVisitor visitor) {
		return visitor.applyWater(this.localization);
	}

	@Override
	public Localization localisation() {
		return this.localization;
	}
}
