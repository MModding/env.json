package fr.firstmegagame4.env.json.impl.rule;

import fr.firstmegagame4.env.json.api.EnvJsonVisitor;
import fr.firstmegagame4.env.json.api.rule.EnvJsonRule;
import fr.firstmegagame4.env.json.api.rule.SequenceEnvJsonRule;

import java.util.List;

public class SequenceEnvJsonRuleImpl extends EnvJsonRuleImpl implements SequenceEnvJsonRule {

	private final List<EnvJsonRule> rules;

	public SequenceEnvJsonRuleImpl(List<EnvJsonRule> rules) {
		super(Type.SEQUENCE);
		this.rules = rules;
	}

	@Override
	public List<EnvJsonRule> rules() {
		return this.rules;
	}

	@Override
	public boolean apply(EnvJsonVisitor source) {
		for (EnvJsonRule rule : this.rules) {
			if (!rule.apply(source)) {
				return false;
			}
		}
		return true;
	}
}
