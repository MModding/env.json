package fr.firstmegagame4.env.json.impl;

import net.fabricmc.api.ModInitializer;

import org.jetbrains.annotations.ApiStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApiStatus.Internal
public class EnvJsonInitializer implements ModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("env_json");

	@Override
	public void onInitialize() {
		LOGGER.info("hello-world.env.json");
	}
}
