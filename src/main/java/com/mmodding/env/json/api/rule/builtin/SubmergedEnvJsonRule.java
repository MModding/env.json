package com.mmodding.env.json.api.rule.builtin;

import com.mmodding.env.json.api.rule.EnvJsonRule;
import org.jetbrains.annotations.ApiStatus;

/**
 * Represents a rule checking if the object is surrounded by water.
 */
@ApiStatus.NonExtendable
public interface SubmergedEnvJsonRule extends EnvJsonRule {

	boolean submerged();
}
