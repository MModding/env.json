package com.mmodding.env.json.impl.rule;

import com.mmodding.env.json.api.EnvJsonVisitor;
import com.mmodding.env.json.api.rule.VoidEnvJsonRule;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class VoidEnvJsonRuleImpl extends EnvJsonRuleImpl implements VoidEnvJsonRule {

	private final Localization localization;

	public VoidEnvJsonRuleImpl(Localization localization) {
		super(Type.VOID);
		this.localization = localization;
	}

	@Override
	public boolean apply(EnvJsonVisitor visitor) {
		return visitor.applyVoid(this.localization);
	}

	@Override
	public Localization localisation() {
		return this.localization;
	}
}
