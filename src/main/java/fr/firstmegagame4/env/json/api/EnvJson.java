package fr.firstmegagame4.env.json.api;

import fr.firstmegagame4.env.json.impl.EnvJsonParser;
import net.minecraft.util.Identifier;

import java.nio.file.Path;
import java.util.List;

public interface EnvJson {

	static EnvJson parse(Path path) {
		return new EnvJsonParser(path).toEnvJson();
	}

    List<EnvJsonMember> members();

	Identifier apply(EnvJsonVisitor source);
}
