package com.mmodding.env.json.api.rule;

import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.World;

public interface DimensionEnvJsonRule extends EnvJsonRule {

	// If this method returns null, then the other one is nonnull.
	RegistryKey<World> dimension();

	// If this method returns null, then the other one is nonnull.
	TagKey<World> tag();
}
