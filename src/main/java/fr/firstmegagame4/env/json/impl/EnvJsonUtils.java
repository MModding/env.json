package fr.firstmegagame4.env.json.impl;

import fr.firstmegagame4.env.json.api.EnvJson;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.resource.InputSupplier;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;

import java.io.IOException;
import java.io.InputStream;

@ApiStatus.Internal
public class EnvJsonUtils {

	public static final EnvJson ENV_JSON_NONE = null;

	public static final InputSupplier<EnvJson> ENV_JSON_NONE_SUPPLIER = () -> EnvJsonUtils.ENV_JSON_NONE;

	private EnvJsonUtils() {
		throw new RuntimeException("Class EnvJsonUtils only contains static definitions");
	}

	public static <T> TagKey<T> tryParse(RegistryKey<? extends Registry<T>> registry, String string) {
		if (string.startsWith("#")) {
			return TagKey.of(registry, Identifier.tryParse(string.substring(1)));
		}
		else {
			throw new IllegalArgumentException("Not a valid tag representation");
		}
	}

	public static boolean isEnvJson(Identifier identifier) {
		return identifier.getPath().endsWith(".env.json");
	}

	public static Identifier getEnvJsonFileName(Identifier identifier) {
		String path;
		if (identifier.getPath().contains("-")) {
			int index = identifier.getPath().lastIndexOf("-");
			path = identifier.getPath().substring(0, index) + "." + identifier.getPath().substring(index + 1);
		}
		else {
			path = identifier.getPath();
		}
		return identifier.withPath(path.substring(0, path.length() - ".env.json".length()));
	}

	public static Identifier getEnvJsonPath(Identifier identifier) {
		String path;
		if (identifier.getPath().contains(".")) {
			int index = identifier.getPath().lastIndexOf(".");
			path = identifier.getPath().substring(0, index) + "-" + identifier.getPath().substring(index + 1);
		}
		else {
			path = identifier.getPath();
		}
		return identifier.withPath(path + ".env.json");
	}

	public static InputSupplier<EnvJson> getEnvJsonSupplier(InputSupplier<InputStream> supplier) {
		return () -> EnvJsonUtils.loadEnvJson(supplier);
	}

	public static EnvJson loadEnvJson(InputSupplier<InputStream> supplier) throws IOException {
		InputStream inputStream = supplier.get();
		EnvJson envJson = EnvJson.parse(inputStream);
		inputStream.close();
		return envJson;
	}
}
