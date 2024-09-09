package com.mmodding.env.json.impl.rule.builtin;

import com.mmodding.env.json.api.EnvJsonVisitor;
import com.mmodding.env.json.api.rule.builtin.DimensionEnvJsonRule;
import com.mojang.datafixers.util.Either;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.World;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class DimensionEnvJsonRuleImpl implements DimensionEnvJsonRule {

	private final Either<RegistryKey<World>, TagKey<World>> either;

	public DimensionEnvJsonRuleImpl(Either<RegistryKey<World>, TagKey<World>> either) {
		this.either = either;
	}

	@Override
	public RegistryKey<World> dimension() {
		return this.either.left().orElseThrow();
	}

	@Override
	public TagKey<World> tag() {
		return this.either.right().orElseThrow();
	}

	@Override
	public boolean apply(EnvJsonVisitor visitor) {
		return this.either.map(visitor::applyDimensionKey, visitor::applyDimensionTag);
	}
}
