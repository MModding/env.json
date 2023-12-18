package fr.firstmegagame4.env.json.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.firstmegagame4.env.json.api.EnvJson;
import fr.firstmegagame4.env.json.api.EnvJsonMember;
import fr.firstmegagame4.env.json.api.rule.*;
import fr.firstmegagame4.env.json.impl.rule.*;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.crash.CrashReport;
import org.jetbrains.annotations.ApiStatus;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ApiStatus.Internal
public class EnvJsonParser {

	private final JsonArray content;

	public EnvJsonParser(Path path) {
		FileReader reader = null;
		try {
			reader = new FileReader(path.toFile());
		}
		catch (FileNotFoundException fileNotFoundException) {
			CrashReport.create(fileNotFoundException, "env.json file not found");
		}
		assert reader != null;
		this.content = JsonParser.parseReader(reader).getAsJsonArray();
	}

	private static SequenceEnvJsonRule parseSequenceRule(JsonObject json) {
		return new SequenceEnvJsonRuleImpl(EnvJsonParser.parseRules(json.getAsJsonObject()));
	}

	private static AnyEnvJsonRule parseAnyRule(JsonObject json) {
		return new AnyEnvJsonRuleImpl(EnvJsonParser.parseRules(json.getAsJsonObject()));
	}

	private static DimensionEnvJsonRule parseDimensionRule(String string) {
		if (string.startsWith("#")) {
			return new DimensionEnvJsonRuleImpl(EnvJsonUtils.tryParse(RegistryKeys.WORLD, string));
		}
		else {
			return new DimensionEnvJsonRuleImpl(RegistryKey.of(RegistryKeys.WORLD, Identifier.tryParse(string)));
		}
	}

	private static BiomeEnvJsonRule parseBiomeRule(String string) {
		if (string.startsWith("#")) {
			return new BiomeEnvJsonRuleImpl(EnvJsonUtils.tryParse(RegistryKeys.BIOME, string));
		}
		else {
			return new BiomeEnvJsonRuleImpl(RegistryKey.of(RegistryKeys.BIOME, Identifier.tryParse(string)));
		}
	}

	private static List<EnvJsonRule> parseRules(JsonObject json) {
		List<EnvJsonRule> rules = new ArrayList<>();
		for (Map.Entry<String, JsonElement> entry : json.entrySet()) {
			EnvJsonRule.Type type = EnvJsonRule.Type.valueOf(entry.getKey().toUpperCase());
			rules.add(
				switch (type) {
					case SEQUENCE -> EnvJsonParser.parseSequenceRule(entry.getValue().getAsJsonObject());
					case ANY -> EnvJsonParser.parseAnyRule(entry.getValue().getAsJsonObject());
					case DIMENSION -> EnvJsonParser.parseDimensionRule(entry.getValue().getAsString());
					case BIOME -> EnvJsonParser.parseBiomeRule(entry.getValue().getAsString());
					case X_COORD -> null;
					case Y_COORD -> null;
					case Z_COORD -> null;
					case SUBMERGED -> null;
					case SKY -> null;
					case WATER -> null;
					case VOID -> null;
				}
			);
		}
		return rules;
	}

	private static EnvJsonMember parseMember(JsonObject json) {
		return new EnvJsonMemberImpl(
			EnvJsonParser.parseRules(json.getAsJsonObject("rule")),
			Identifier.tryParse(json.get("result").getAsString())
		);
	}

	public EnvJson toEnvJson() {
		List<EnvJsonMember> members = new ArrayList<>();
		for (JsonElement member : this.content.asList()) {
			members.add(EnvJsonParser.parseMember(member.getAsJsonObject()));
		}
		return new EnvJsonImpl(members);
	}
}
