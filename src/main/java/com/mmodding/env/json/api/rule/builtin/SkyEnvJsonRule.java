package com.mmodding.env.json.api.rule.builtin;

import com.mmodding.env.json.api.RelativeLocation;
import com.mmodding.env.json.api.rule.EnvJsonRule;
import org.jetbrains.annotations.ApiStatus;

/**
 * Represents a rule checking if the object is below or above the sky limit.
 */
@ApiStatus.NonExtendable
public interface SkyEnvJsonRule extends EnvJsonRule {

	RelativeLocation location();
}
