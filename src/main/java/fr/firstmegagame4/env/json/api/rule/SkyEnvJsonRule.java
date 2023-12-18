package fr.firstmegagame4.env.json.api.rule;

public interface SkyEnvJsonRule extends EnvJsonRule {

	Localization localisation();

	enum Localization {
		BELOW,
		ABOVE
	}
}
