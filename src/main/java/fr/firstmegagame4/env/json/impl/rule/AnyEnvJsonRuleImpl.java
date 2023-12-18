package fr.firstmegagame4.env.json.impl.rule;

import fr.firstmegagame4.env.json.api.EnvJsonVisitor;
import fr.firstmegagame4.env.json.api.rule.AnyEnvJsonRule;
import fr.firstmegagame4.env.json.api.rule.EnvJsonRule;

import java.util.List;

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
	public boolean apply(EnvJsonVisitor source) {
		for (EnvJsonRule rule : this.rules) {
			if (rule.apply(source)) {
				return true;
			}
		}
		return false;
	}
}
