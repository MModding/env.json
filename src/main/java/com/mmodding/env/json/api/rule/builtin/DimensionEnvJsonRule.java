package com.mmodding.env.json.api.rule.builtin;

import com.mmodding.env.json.api.rule.EnvJsonRule;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.World;
import org.jetbrains.annotations.ApiStatus;

/**
 * Represents a dimension rule where the object should be.
 */
@ApiStatus.NonExtendable
public interface DimensionEnvJsonRule extends EnvJsonRule {

	/**
	 * If this method returns null, then {@link DimensionEnvJsonRule#tag()} must return a nonnull value.
	 */
	RegistryKey<World> dimension();

	/**
	 * If this method returns null, then {@link DimensionEnvJsonRule#dimension()} must return a nonnull value.
	 */
	TagKey<World> tag();
}
