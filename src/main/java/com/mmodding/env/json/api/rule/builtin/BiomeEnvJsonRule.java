package com.mmodding.env.json.api.rule.builtin;

import com.mmodding.env.json.api.rule.EnvJsonRule;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.ApiStatus;

/**
 * Represents a biome rule where the object should be.
 */
@ApiStatus.NonExtendable
public interface BiomeEnvJsonRule extends EnvJsonRule {

	RegistryKey<Biome> biome();

	TagKey<Biome> tag();
}
