package com.mmodding.env.json.impl.rule;

import com.mmodding.env.json.api.EnvJsonVisitor;
import com.mmodding.env.json.api.rule.AnyEnvJsonRule;
import com.mmodding.env.json.api.rule.EnvJsonRule;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;

@ApiStatus.Internal
public class AnyEnvJsonRuleImpl extends EnvJsonRuleImpl implements AnyEnvJsonRule {

	private final List<EnvJsonRule> rules;

	public AnyEnvJsonRuleImpl(List<EnvJsonRule> rules) {
		super(Type.ANY);
		this.rules = rules;
	}

	@Override
	public List<EnvJsonRule> rules() {
		return this.rules;
	}

	@Override
	public boolean apply(EnvJsonVisitor visitor) {
		for (EnvJsonRule rule : this.rules) {
			if (rule.apply(visitor)) {
				return true;
			}
		}
		return false;
	}
}
