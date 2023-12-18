package fr.firstmegagame4.env.json.api.rule;

public interface WaterEnvJsonRule extends EnvJsonRule {

	Localization localisation();

	enum Localization {
		BELOW,
		ABOVE
	}
}
