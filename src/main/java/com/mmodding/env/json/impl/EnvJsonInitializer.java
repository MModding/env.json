package com.mmodding.env.json.impl;

import com.mmodding.env.json.impl.rule.EnvJsonRulesImpl;
import net.fabricmc.api.ModInitializer;

import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApiStatus.Internal
public class EnvJsonInitializer implements ModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("env_json");

	@Override
	public void onInitialize() {
		LOGGER.info("hello-world.env.json");
		EnvJsonRulesImpl.registerBuiltinRules();
	}

	public static String id() {
		return "env_json";
	}

	public static Identifier createId(String path) {
		return Identifier.of(EnvJsonInitializer.id(), path);
	}
}
