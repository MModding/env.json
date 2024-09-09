package com.mmodding.env.json.api.rule.parsing;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.mmodding.env.json.api.rule.EnvJsonRule;
import com.mojang.datafixers.util.Either;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.TagKey;

import java.util.List;

public interface ParserCallback {

	<E extends Enum<E>> E parseEnum(Class<E> enumeration, JsonPrimitive primitive);

	<T> Either<RegistryKey<T>, TagKey<T>> parseReference(RegistryKey<Registry<T>> registry, JsonPrimitive primitive);

	EnvJsonRule parseRule(JsonElement element);

	List<EnvJsonRule> parseRules(JsonArray array);
}
