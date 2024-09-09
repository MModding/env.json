package com.mmodding.env.json.impl.rule;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mmodding.env.json.api.RelativeLocation;
import com.mmodding.env.json.api.rule.EnvJsonRule;
import com.mmodding.env.json.api.rule.builtin.CoordEnvJsonRule;
import com.mmodding.env.json.api.rule.parsing.EnvJsonRuleParser;
import com.mmodding.env.json.api.rule.EnvJsonRules;
import com.mmodding.env.json.impl.EnvJsonInitializer;
import com.mmodding.env.json.impl.rule.builtin.*;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EnvJsonRulesImpl {

	public static final Map<Identifier, EnvJsonRuleParser<?, ?>> REGISTRY = new Object2ObjectOpenHashMap<>();

	public static final Set<Class<? extends EnvJsonRule>> LOADED_RULES = new HashSet<>();

	public static void registerBuiltinRules() {
		EnvJsonRules.<JsonArray>register(
			EnvJsonInitializer.createId("any"),
			(array, callback) -> new AnyEnvJsonRuleImpl(callback.parseRules(array))
		);
		EnvJsonRules.<JsonPrimitive>register(
			EnvJsonInitializer.createId("biome"),
			(primitive, callback) -> new BiomeEnvJsonRuleImpl(callback.parseReference(RegistryKeys.BIOME, primitive))
		);
		EnvJsonRules.register(EnvJsonInitializer.createId("x_coord"), EnvJsonRulesImpl.coordinate(CoordEnvJsonRule.Coord.X));
		EnvJsonRules.register(EnvJsonInitializer.createId("y_coord"), EnvJsonRulesImpl.coordinate(CoordEnvJsonRule.Coord.Y));
		EnvJsonRules.register(EnvJsonInitializer.createId("z_coord"), EnvJsonRulesImpl.coordinate(CoordEnvJsonRule.Coord.Z));
		EnvJsonRules.<JsonPrimitive>register(
			EnvJsonInitializer.createId("dimension"),
			(primitive, callback) -> new DimensionEnvJsonRuleImpl(callback.parseReference(RegistryKeys.WORLD, primitive))
		);
		EnvJsonRules.<JsonObject>register(
			EnvJsonInitializer.createId("not"),
			(object, callback) -> new NotEnvJsonRuleImpl(callback.parseRule(object))
		);
		EnvJsonRules.<JsonArray>register(
			EnvJsonInitializer.createId("sequence"),
			(array, callback) -> new SequenceEnvJsonRuleImpl(callback.parseRules(array))
		);
		EnvJsonRules.<JsonPrimitive>register(
			EnvJsonInitializer.createId("sky"),
			(primitive, callback) -> new SkyEnvJsonRuleImpl(callback.parseEnum(RelativeLocation.class, primitive))
		);
		EnvJsonRules.<JsonPrimitive>register(
			EnvJsonInitializer.createId("submerged"),
			(primitive, callback) -> new SubmergedEnvJsonRuleImpl(primitive.getAsBoolean())
		);
		EnvJsonRules.<JsonPrimitive>register(
			EnvJsonInitializer.createId("void"),
			(primitive, callback) -> new VoidEnvJsonRuleImpl(callback.parseEnum(RelativeLocation.class, primitive))
		);
		EnvJsonRules.<JsonPrimitive>register(
			EnvJsonInitializer.createId("water"),
			(primitive, callback) -> new WaterEnvJsonRuleImpl(callback.parseEnum(RelativeLocation.class, primitive))
		);
	}

	private static EnvJsonRuleParser<JsonObject, CoordEnvJsonRule> coordinate(CoordEnvJsonRule.Coord coordinate) {
		return (object, callback) -> {
			CoordEnvJsonRule.Comparator comparator = CoordEnvJsonRule.Comparator.fromString(
				object.getAsJsonPrimitive("comparator").getAsString()
			);
			int value = object.get("value").getAsInt();
			return new CoordEnvJsonRuleImpl(coordinate, comparator, value);
		};
	}
}
