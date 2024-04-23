package com.mmodding.env.json.api.resource;

import com.mmodding.env.json.api.EnvJson;
import com.mmodding.env.json.impl.resource.ExtendedResourceImpl;
import net.minecraft.registry.VersionedIdentifier;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourcePack;
import net.minecraft.resource.metadata.ResourceMetadata;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

public interface ExtendedResource {

	static ExtendedResource of(Resource resource) {
		return ExtendedResourceImpl.of(resource);
	}

	ResourcePack getPack();

	String getPackId();

	Optional<VersionedIdentifier> getKnownPackInfo();

	InputStream getInputStream() throws IOException;

	BufferedReader getReader() throws IOException;

	ResourceMetadata getMetadata() throws IOException;

	EnvJson getEnvJson() throws IOException;

	Resource asResource();
}
