package com.mmodding.env.json.impl;

import com.mmodding.env.json.api.EnvJson;
import com.mmodding.env.json.api.EnvJsonMember;
import com.mmodding.env.json.api.EnvJsonVisitor;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;

@ApiStatus.Internal
public record EnvJsonImpl(List<EnvJsonMember> members) implements EnvJson {

	@Override
	public Identifier apply(EnvJsonVisitor visitor) {
		for (EnvJsonMember member : this.members) {
			Identifier result = member.apply(visitor);
			if (result != null) {
				return result;
			}
		}
		return null;
	}
}
