package com.mmodding.env.json.api.rule;

public interface WaterEnvJsonRule extends EnvJsonRule {

	Localization localisation();

	enum Localization {
		BELOW,
		AT,
		ABOVE
	}
}
