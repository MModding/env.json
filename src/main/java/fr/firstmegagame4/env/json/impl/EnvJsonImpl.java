package fr.firstmegagame4.env.json.impl;

import fr.firstmegagame4.env.json.api.EnvJson;
import fr.firstmegagame4.env.json.api.EnvJsonMember;
import fr.firstmegagame4.env.json.api.EnvJsonVisitor;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;

@ApiStatus.Internal
public record EnvJsonImpl(List<EnvJsonMember> members) implements EnvJson {

	@Override
	public Identifier apply(EnvJsonVisitor source) {
		for (EnvJsonMember member : this.members) {
			Identifier result = member.apply(source);
			if (result != null) {
				return result;
			}
		}
		return null;
	}
}
