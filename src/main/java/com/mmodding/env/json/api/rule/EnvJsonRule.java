package com.mmodding.env.json.api.rule;

import com.mmodding.env.json.api.EnvJsonVisitor;
import com.mmodding.env.json.impl.rule.EnvJsonRuleImpl;

public interface EnvJsonRule {

	static <T extends EnvJsonRule> T safeCast(EnvJsonRule rule, Class<T> type) {
		return EnvJsonRuleImpl.safeCast(rule, type);
	}

	boolean apply(EnvJsonVisitor visitor);
}
