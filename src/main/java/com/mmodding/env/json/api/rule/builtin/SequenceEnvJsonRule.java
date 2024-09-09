package com.mmodding.env.json.api.rule.builtin;

import com.mmodding.env.json.api.rule.EnvJsonRule;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;

/**
 * Represents a sequence of combined rules with the AND operator.
 */
@ApiStatus.NonExtendable
public interface SequenceEnvJsonRule extends EnvJsonRule {

	List<EnvJsonRule> rules();
}
