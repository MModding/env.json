package fr.firstmegagame4.env.json.impl.rule;

import fr.firstmegagame4.env.json.api.EnvJsonVisitor;
import fr.firstmegagame4.env.json.api.rule.WaterEnvJsonRule;
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
