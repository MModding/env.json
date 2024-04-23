package com.mmodding.env.json.api.rule;

import java.util.List;

public interface AnyEnvJsonRule extends EnvJsonRule {

	List<EnvJsonRule> rules();
}
