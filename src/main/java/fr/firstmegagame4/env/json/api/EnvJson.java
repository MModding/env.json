package fr.firstmegagame4.env.json.api;

import fr.firstmegagame4.env.json.impl.EnvJsonParser;
import net.minecraft.util.Identifier;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;

public interface EnvJson {

	static EnvJson parse(Path path) {
		return new EnvJsonParser(path).toEnvJson();
	}

	static EnvJson parse(InputStream stream) {
		return new EnvJsonParser(stream).toEnvJson();
	}

    List<EnvJsonMember> members();

	Identifier apply(EnvJsonVisitor visitor);
}
