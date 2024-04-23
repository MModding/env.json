package com.mmodding.env.json.api;

import com.mmodding.env.json.api.rule.EnvJsonRule;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface EnvJsonMember {

	List<EnvJsonRule> rules();

	Identifier result();

	@Nullable
	Identifier apply(EnvJsonVisitor visitor);
}
