package com.mmodding.env.json.impl.resource;

import net.minecraft.resource.InputSupplier;
import net.minecraft.resource.ResourcePack;
import net.minecraft.util.Identifier;

import java.io.InputStream;
import java.util.Map;

public interface EntryListDuckInterface {

	Identifier env_json$getEnvJsonIdentifier();

	Map<ResourcePack, InputSupplier<InputStream>> env_json$getEnvJsonResources();

	void env_json$putEnvJsonSource(ResourcePack resourcePack, InputSupplier<InputStream> inputSupplier);
}
