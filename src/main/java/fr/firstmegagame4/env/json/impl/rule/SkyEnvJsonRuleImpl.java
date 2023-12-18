package fr.firstmegagame4.env.json.impl.rule;

import fr.firstmegagame4.env.json.api.EnvJsonVisitor;
import fr.firstmegagame4.env.json.api.rule.SkyEnvJsonRule;
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
