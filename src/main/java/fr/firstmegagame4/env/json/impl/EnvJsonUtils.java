package fr.firstmegagame4.env.json.impl;

import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class EnvJsonUtils {

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
}
