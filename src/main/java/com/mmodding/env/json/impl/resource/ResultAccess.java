package com.mmodding.env.json.impl.resource;

import net.minecraft.resource.InputSupplier;
import net.minecraft.resource.ResourcePack;
import org.jetbrains.annotations.ApiStatus;

import java.io.InputStream;

@ApiStatus.Internal
public interface ResultAccess {

	ResourcePack invokePack();

	InputSupplier<InputStream> invokeSupplier();

	int invokePackIndex();
}
