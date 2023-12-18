package fr.firstmegagame4.env.json.impl.rule;

import fr.firstmegagame4.env.json.api.EnvJsonVisitor;
import fr.firstmegagame4.env.json.api.rule.BiomeEnvJsonRule;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class BiomeEnvJsonRuleImpl extends EnvJsonRuleImpl implements BiomeEnvJsonRule {

	private final RegistryKey<Biome> biome;
	private final TagKey<Biome> tag;

	public BiomeEnvJsonRuleImpl(RegistryKey<Biome> biome) {
		super(Type.BIOME);
		this.biome = biome;
		this.tag = null;
	}

	public BiomeEnvJsonRuleImpl(TagKey<Biome> tag) {
		super(Type.BIOME);
		this.biome = null;
		this.tag = tag;
	}

	@Override
	public RegistryKey<Biome> biome() {
		return this.biome;
	}

	@Override
	public TagKey<Biome> tag() {
		return this.tag;
	}

	@Override
	public boolean apply(EnvJsonVisitor visitor) {
		if (this.biome != null) {
			return visitor.applyBiomeKey(this.biome);
		}
		else if (this.tag != null) {
			return visitor.applyBiomeTag(this.tag);
		}
		else {
			return false;
		}
	}
}
