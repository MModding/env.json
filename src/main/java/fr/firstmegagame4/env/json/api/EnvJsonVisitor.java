package fr.firstmegagame4.env.json.api;

import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public interface EnvJsonVisitor {

	boolean applyDimensionKey(RegistryKey<World> dimensionKey);

	boolean applyDimensionTag(TagKey<World> dimensionTag);

	boolean applyBiomeKey(RegistryKey<Biome> biomeKey);

	boolean applyBiomeTag(TagKey<Biome> biomeTag);

	Identifier apply(EnvJsonMember member);
}
