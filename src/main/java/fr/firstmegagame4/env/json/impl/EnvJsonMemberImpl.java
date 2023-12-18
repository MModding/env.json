package fr.firstmegagame4.env.json.impl;

import fr.firstmegagame4.env.json.api.EnvJsonMember;
import fr.firstmegagame4.env.json.api.rule.EnvJsonRule;
import fr.firstmegagame4.env.json.api.EnvJsonVisitor;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@ApiStatus.Internal
public record EnvJsonMemberImpl(List<EnvJsonRule> rules, Identifier result) implements EnvJsonMember {

	@Override
	@Nullable
	public Identifier apply(EnvJsonVisitor visitor) {
		for (EnvJsonRule rule : this.rules) {
			if (rule.apply(visitor)) {
				return this.result;
			}
		}
		return null;
	}
}
