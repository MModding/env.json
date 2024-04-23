package com.mmodding.env.json.impl.resource;

import com.mmodding.env.json.api.EnvJson;
import com.mmodding.env.json.api.resource.ExtendedResource;
import com.mmodding.env.json.mixin.ResourceAccessor;
import net.minecraft.resource.InputSupplier;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourcePack;
import net.minecraft.resource.metadata.ResourceMetadata;
import org.jetbrains.annotations.ApiStatus;

import java.io.IOException;
import java.io.InputStream;

@ApiStatus.Internal
public class ExtendedResourceImpl extends Resource implements ExtendedResource {

	public static ExtendedResource of(Resource resource) {
		ResourceAccessor accessor = (ResourceAccessor) resource;
		ResourceDuckInterface ducked = (ResourceDuckInterface) resource;
		return new ExtendedResourceImpl(accessor.getPack(), accessor.getInputSupplier(), accessor.getMetadataSupplier(), ducked.env_json$getEnvJsonSupplier(), accessor.getRawMetadata(), ducked.env_json$getEnvJson());
	}

	private ExtendedResourceImpl(ResourcePack pack, InputSupplier<InputStream> inputSupplier, InputSupplier<ResourceMetadata> metadataSupplier, InputSupplier<EnvJson> envJsonSupplier, ResourceMetadata resourceMetadata, EnvJson envJson) {
		super(pack, inputSupplier, metadataSupplier);
		((ResourceAccessor) this).setRawMetaData(resourceMetadata);
		ResourceDuckInterface ducked = (ResourceDuckInterface) this;
		ducked.env_json$initEnvJsonSupplier(envJsonSupplier);
		ducked.env_json$initEnvJson(envJson);
	}

	@Override
	public EnvJson getEnvJson() throws IOException {
		ResourceDuckInterface ducked = (ResourceDuckInterface) this;
		return ducked.env_json$getEnvJson() == null ? ducked.en_json$applyEnvJson() : ducked.env_json$getEnvJson();
	}

	@Override
	public Resource asResource() {
		return this;
	}
}
