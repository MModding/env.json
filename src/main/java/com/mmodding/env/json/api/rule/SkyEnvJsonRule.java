package com.mmodding.env.json.api.rule;

public interface SkyEnvJsonRule extends EnvJsonRule {

	Localization localisation();

	enum Localization {
		BELOW,
		AT,
		ABOVE
	}
}
