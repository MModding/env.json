package com.mmodding.env.json.impl.rule.builtin;

import com.mmodding.env.json.api.EnvJsonVisitor;
import com.mmodding.env.json.api.rule.builtin.BiomeEnvJsonRule;
import com.mojang.datafixers.util.Either;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class BiomeEnvJsonRuleImpl implements BiomeEnvJsonRule {

	private final Either<RegistryKey<Biome>, TagKey<Biome>> either;

	public BiomeEnvJsonRuleImpl(Either<RegistryKey<Biome>, TagKey<Biome>> either) {
		this.either = either;
	}

	@Override
	public RegistryKey<Biome> biome() {
		return this.either.left().orElseThrow();
	}

	@Override
	public TagKey<Biome> tag() {
		return this.either.right().orElseThrow();
	}

	@Override
	public boolean apply(EnvJsonVisitor visitor) {
		return this.either.map(visitor::applyBiomeKey, visitor::applyBiomeTag);
	}
}
