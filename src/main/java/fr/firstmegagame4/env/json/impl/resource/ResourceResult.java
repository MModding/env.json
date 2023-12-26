package fr.firstmegagame4.env.json.impl.resource;

import net.minecraft.resource.InputSupplier;
import net.minecraft.resource.ResourcePack;
import org.jetbrains.annotations.ApiStatus;

import java.io.InputStream;

@ApiStatus.Internal
public record ResourceResult(ResourcePack pack, InputSupplier<InputStream> supplier, int packIndex) {
}
