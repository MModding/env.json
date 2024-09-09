package com.mmodding.env.json.api.rule.parsing;

import com.google.gson.JsonElement;
import com.mmodding.env.json.api.rule.EnvJsonRule;

@FunctionalInterface
public interface EnvJsonRuleParser<E extends JsonElement, R extends EnvJsonRule> {

	R parse(E element, ParserCallback callback);
}
