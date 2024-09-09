package com.mmodding.env.json.impl.rule.parsing;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.mmodding.env.json.api.rule.EnvJsonRule;
import com.mmodding.env.json.api.rule.parsing.ParserCallback;
import com.mmodding.env.json.impl.EnvJsonParser;
import com.mmodding.env.json.impl.EnvJsonUtils;
import com.mojang.datafixers.util.Either;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;

@ApiStatus.Internal
public class ParserCallbackImpl implements ParserCallback {

	@Override
	public <E extends Enum<E>> E parseEnum(Class<E> enumeration, JsonPrimitive primitive) {
		return E.valueOf(enumeration, primitive.getAsString().toUpperCase());
	}

	@Override
	public <T> Either<RegistryKey<T>, TagKey<T>> parseReference(RegistryKey<Registry<T>> registry, JsonPrimitive primitive) {
		String string = primitive.getAsString();
		if (!string.startsWith("#")) {
			return Either.left(RegistryKey.of(registry, Identifier.tryParse(string)));
		}
		else {
			return Either.right(EnvJsonUtils.tryParse(registry, string));
		}
	}

	@Override
	public EnvJsonRule parseRule(JsonElement element) {
		return EnvJsonParser.parseRule(element.getAsJsonObject());
	}

	@Override
	public List<EnvJsonRule> parseRules(JsonArray array) {
		return array.asList().stream().map(this::parseRule).toList();
	}
}
