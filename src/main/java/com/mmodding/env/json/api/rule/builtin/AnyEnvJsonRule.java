package com.mmodding.env.json.api.rule.builtin;

import com.mmodding.env.json.api.rule.EnvJsonRule;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;

/**
 * Represents a sequence of combined rules with the OR operator.
 */
@ApiStatus.NonExtendable
public interface AnyEnvJsonRule extends EnvJsonRule {

	List<EnvJsonRule> rules();
}
