package com.mmodding.env.json.impl.rule;

import com.mmodding.env.json.api.EnvJsonVisitor;
import com.mmodding.env.json.api.rule.SkyEnvJsonRule;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class SkyEnvJsonRuleImpl extends EnvJsonRuleImpl implements SkyEnvJsonRule {

	private final Localization localization;

	public SkyEnvJsonRuleImpl(Localization localization) {
		super(Type.SKY);
		this.localization = localization;
	}

	@Override
	public boolean apply(EnvJsonVisitor visitor) {
		return visitor.applySky(this.localization);
	}

	@Override
	public Localization localisation() {
		return this.localization;
	}
}
