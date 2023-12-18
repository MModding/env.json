package fr.firstmegagame4.env.json.api.rule;

import java.util.List;

public interface SequenceEnvJsonRule extends EnvJsonRule {

	List<EnvJsonRule> rules();
}
