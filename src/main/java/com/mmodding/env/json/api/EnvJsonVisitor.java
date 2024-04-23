package com.mmodding.env.json.api;

import com.mmodding.env.json.api.rule.SkyEnvJsonRule;
import com.mmodding.env.json.api.rule.VoidEnvJsonRule;
import com.mmodding.env.json.api.rule.WaterEnvJsonRule;
import it.unimi.dsi.fastutil.ints.Int2BooleanFunction;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public interface EnvJsonVisitor {

	boolean applyDimensionKey(RegistryKey<World> dimensionKey);

	boolean applyDimensionTag(TagKey<World> dimensionTag);

	boolean applyBiomeKey(RegistryKey<Biome> biomeKey);

	boolean applyBiomeTag(TagKey<Biome> biomeTag);

	boolean applyXCoord(Int2BooleanFunction operation);

	boolean applyYCoord(Int2BooleanFunction operation);

	boolean applyZCoord(Int2BooleanFunction operation);

	boolean applySubmerged(boolean submerged);

	boolean applySky(SkyEnvJsonRule.Localization localization);

	boolean applyWater(WaterEnvJsonRule.Localization localization);

	boolean applyVoid(VoidEnvJsonRule.Localization localization);
}
