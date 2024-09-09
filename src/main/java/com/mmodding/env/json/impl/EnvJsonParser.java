package com.mmodding.env.json.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mmodding.env.json.api.EnvJson;
import com.mmodding.env.json.api.EnvJsonMember;
import com.mmodding.env.json.api.rule.*;
import com.mmodding.env.json.api.rule.parsing.EnvJsonRuleParser;
import com.mmodding.env.json.impl.rule.EnvJsonRulesImpl;
import com.mmodding.env.json.impl.rule.parsing.ParserCallbackImpl;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.crash.CrashReport;
import org.jetbrains.annotations.ApiStatus;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

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

	public static EnvJsonRule parseRule(JsonObject json) {
		String string = json.get("type").getAsString();
		Identifier identifier = !string.contains(":") ? EnvJsonInitializer.createId(string) : Identifier.tryParse(string);
		return EnvJsonParser.parseRuleContent(identifier, json.get("rule"));
	}

	@SuppressWarnings("unchecked")
	private static <E extends JsonElement, R extends EnvJsonRule> R parseRuleContent(Identifier identifier, JsonElement element) {
		if (EnvJsonRulesImpl.REGISTRY.containsKey(identifier)) {
			EnvJsonRuleParser<E, R> parser = (EnvJsonRuleParser<E, R>) EnvJsonRulesImpl.REGISTRY.get(identifier);
			R rule = parser.parse((E) element, new ParserCallbackImpl());
			EnvJsonRulesImpl.LOADED_RULES.add(rule.getClass());
			return rule;
		}
		else {
			throw new RuntimeException("Could not find " + identifier + " env.json rule!");
		}
	}

	private static List<EnvJsonRule> parseRules(JsonArray array) {
		List<EnvJsonRule> rules = new ArrayList<>();
		for (JsonElement element : array) {
			rules.add(EnvJsonParser.parseRule(element.getAsJsonObject()));
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
