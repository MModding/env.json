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
import net.minecraft.util.JsonHelper;
import net.minecraft.util.crash.CrashReport;
import org.jetbrains.annotations.ApiStatus;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

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

	public EnvJsonParser(InputStream stream) {
		this.content = JsonHelper.deserializeArray(new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8)));
	}

	private static SequenceEnvJsonRule parseSequenceRule(JsonArray list) {
		return new SequenceEnvJsonRuleImpl(EnvJsonParser.parseRules(list));
	}

	private static AnyEnvJsonRule parseAnyRule(JsonArray list) {
		return new AnyEnvJsonRuleImpl(EnvJsonParser.parseRules(list));
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

	private static CoordEnvJsonRule parseCoordRule(CoordEnvJsonRule.Coord coord, JsonObject json) {
		CoordEnvJsonRule.Comparator comparator = CoordEnvJsonRule.Comparator.fromString(json.get("comparator").getAsString());
		int value = json.get("value").getAsInt();
		return new CoordEnvJsonRuleImpl(coord, comparator, value);
	}

	private static SubmergedEnvJsonRule parseSubmergedRule(boolean bool) {
		return new SubmergedEnvJsonRuleImpl(bool);
	}

	private static SkyEnvJsonRule parseSkyRule(String string) {
		return new SkyEnvJsonRuleImpl(SkyEnvJsonRule.Localization.valueOf(string.toUpperCase()));
	}

	private static WaterEnvJsonRule parseWaterRule(String string) {
		return new WaterEnvJsonRuleImpl(WaterEnvJsonRule.Localization.valueOf(string.toUpperCase()));
	}

	private static VoidEnvJsonRule parseVoidRule(String string) {
		return new VoidEnvJsonRuleImpl(VoidEnvJsonRule.Localization.valueOf(string.toUpperCase()));
	}

	private static List<EnvJsonRule> parseRules(JsonArray array) {
		List<EnvJsonRule> rules = new ArrayList<>();
		for (JsonElement element : array) {
			JsonObject rule = element.getAsJsonObject();
			EnvJsonRule.Type type = EnvJsonRule.Type.valueOf(rule.get("type").getAsString().toUpperCase());
			Supplier<JsonElement> supplier = () -> rule.get("rule");
			rules.add(
				switch (type) {
					case SEQUENCE -> EnvJsonParser.parseSequenceRule(supplier.get().getAsJsonArray());
					case ANY -> EnvJsonParser.parseAnyRule(supplier.get().getAsJsonArray());
					case DIMENSION -> EnvJsonParser.parseDimensionRule(supplier.get().getAsString());
					case BIOME -> EnvJsonParser.parseBiomeRule(supplier.get().getAsString());
					case X_COORD -> EnvJsonParser.parseCoordRule(CoordEnvJsonRule.Coord.X, supplier.get().getAsJsonObject());
					case Y_COORD -> EnvJsonParser.parseCoordRule(CoordEnvJsonRule.Coord.Y, supplier.get().getAsJsonObject());
					case Z_COORD -> EnvJsonParser.parseCoordRule(CoordEnvJsonRule.Coord.Z, supplier.get().getAsJsonObject());
					case SUBMERGED -> EnvJsonParser.parseSubmergedRule(supplier.get().getAsBoolean());
					case SKY -> EnvJsonParser.parseSkyRule(supplier.get().getAsString());
					case WATER -> EnvJsonParser.parseWaterRule(supplier.get().getAsString());
					case VOID -> EnvJsonParser.parseVoidRule(supplier.get().getAsString());
				}
			);
		}
		return rules;
	}

	private static EnvJsonMember parseMember(JsonObject json) {
		return new EnvJsonMemberImpl(
			EnvJsonParser.parseRules(json.getAsJsonArray("rules")),
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
