package fr.firstmegagame4.env.json.api.resource;

import fr.firstmegagame4.env.json.api.EnvJson;
import fr.firstmegagame4.env.json.impl.resource.ExtendedResourceImpl;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourcePack;
import net.minecraft.resource.metadata.ResourceMetadata;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;

public interface ExtendedResource {

	static ExtendedResource of(Resource resource) {
		return ExtendedResourceImpl.of(resource);
	}

	ResourcePack getPack();

	String getResourcePackName();

	boolean isAlwaysStable();

	InputStream getInputStream() throws IOException;

	BufferedReader getReader() throws IOException;

	ResourceMetadata getMetadata() throws IOException;

	EnvJson getEnvJson() throws IOException;

	Resource asResource();
}
