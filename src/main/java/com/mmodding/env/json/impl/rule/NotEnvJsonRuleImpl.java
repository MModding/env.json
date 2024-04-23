package com.mmodding.env.json.impl.rule;

import com.mmodding.env.json.api.EnvJsonVisitor;
import com.mmodding.env.json.api.rule.EnvJsonRule;
import com.mmodding.env.json.api.rule.NotEnvJsonRule;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class NotEnvJsonRuleImpl extends EnvJsonRuleImpl implements NotEnvJsonRule {

	private final EnvJsonRule rule;

	public NotEnvJsonRuleImpl(EnvJsonRule rule) {
		super(Type.NOT);
		this.rule = rule;
	}

	@Override
	public EnvJsonRule rule() {
		return this.rule;
	}

	@Override
	public boolean apply(EnvJsonVisitor visitor) {
		return !this.rule.apply(visitor);
	}
}
