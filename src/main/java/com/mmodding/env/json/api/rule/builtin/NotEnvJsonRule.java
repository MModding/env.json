package com.mmodding.env.json.api.rule.builtin;

import com.mmodding.env.json.api.rule.EnvJsonRule;
import org.jetbrains.annotations.ApiStatus;

/**
 * Reverses the rule.
 */
@ApiStatus.NonExtendable
public interface NotEnvJsonRule extends EnvJsonRule {

	EnvJsonRule rule();
}
