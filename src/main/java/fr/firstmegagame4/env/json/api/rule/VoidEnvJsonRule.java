package fr.firstmegagame4.env.json.api.rule;

public interface VoidEnvJsonRule extends EnvJsonRule {

	Localization localisation();

	enum Localization {
		BELOW,
		AT,
		ABOVE
	}
}
