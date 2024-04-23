package com.mmodding.env.json.impl;

import com.mmodding.env.json.api.EnvJsonVisitor;
import com.mmodding.env.json.api.EnvJsonMember;
import com.mmodding.env.json.api.rule.EnvJsonRule;
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
