package fr.firstmegagame4.env.json.api.rule;

import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.biome.Biome;

public interface BiomeEnvJsonRule extends EnvJsonRule {

	RegistryKey<Biome> biome();

	TagKey<Biome> tag();
}
