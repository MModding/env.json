package com.mmodding.env.json.api.rule;

import com.google.gson.JsonElement;
import com.mmodding.env.json.api.rule.parsing.EnvJsonRuleParser;
import com.mmodding.env.json.impl.rule.EnvJsonRulesImpl;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.NonExtendable
public final class EnvJsonRules {

	public EnvJsonRules() {
		throw new IllegalStateException("EnvJsonRules class only contains static definitions");
	}

	/**
	 * Allows to register a new {@link EnvJsonRuleParser} under an identifier
	 * @param identifier the identifier
	 * @param parser the parser
	 * @param <E> the class of the shape of the json element the parser should parse
	 */
	public static <E extends JsonElement> void register(Identifier identifier, EnvJsonRuleParser<E, ? extends EnvJsonRule> parser) {
		EnvJsonRulesImpl.REGISTRY.put(identifier, parser);
	}
}
