package com.mmodding.env.json.api.rule;

import java.util.List;

public interface SequenceEnvJsonRule extends EnvJsonRule {

	List<EnvJsonRule> rules();
}
