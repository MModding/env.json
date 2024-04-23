package com.mmodding.env.json.impl.rule;

import com.mmodding.env.json.api.EnvJsonVisitor;
import com.mmodding.env.json.api.rule.DimensionEnvJsonRule;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.World;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class DimensionEnvJsonRuleImpl extends EnvJsonRuleImpl implements DimensionEnvJsonRule {

	private final RegistryKey<World> dimension;
	private final TagKey<World> tag;

	public DimensionEnvJsonRuleImpl(RegistryKey<World> dimension) {
		super(Type.DIMENSION);
		this.dimension = dimension;
		this.tag = null;
	}

	public DimensionEnvJsonRuleImpl(TagKey<World> tag) {
		super(Type.DIMENSION);
		this.dimension = null;
		this.tag = tag;
	}

	@Override
	public RegistryKey<World> dimension() {
		return this.dimension;
	}

	@Override
	public TagKey<World> tag() {
		return this.tag;
	}

	@Override
	public boolean apply(EnvJsonVisitor visitor) {
		if (this.dimension != null) {
			return visitor.applyDimensionKey(this.dimension);
		}
		else if (this.tag != null) {
			return visitor.applyDimensionTag(this.tag);
		}
		else {
			return false;
		}
	}
}
