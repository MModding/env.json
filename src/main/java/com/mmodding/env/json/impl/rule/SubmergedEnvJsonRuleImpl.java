package com.mmodding.env.json.impl.rule;

import com.mmodding.env.json.api.EnvJsonVisitor;
import com.mmodding.env.json.api.rule.SubmergedEnvJsonRule;

public class SubmergedEnvJsonRuleImpl extends EnvJsonRuleImpl implements SubmergedEnvJsonRule {

	private final boolean submerged;

	public SubmergedEnvJsonRuleImpl(boolean submerged) {
		super(Type.SUBMERGED);
		this.submerged = submerged;
	}

	@Override
	public boolean apply(EnvJsonVisitor visitor) {
		return visitor.applySubmerged(this.submerged);
	}

	@Override
	public boolean submerged() {
		return submerged;
	}
}
