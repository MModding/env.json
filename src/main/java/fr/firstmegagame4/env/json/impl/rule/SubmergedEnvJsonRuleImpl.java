package fr.firstmegagame4.env.json.impl.rule;

import fr.firstmegagame4.env.json.api.EnvJsonVisitor;
import fr.firstmegagame4.env.json.api.rule.SubmergedEnvJsonRule;

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
