package fr.firstmegagame4.env.json.mixin;

import net.minecraft.resource.InputSupplier;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourcePack;
import net.minecraft.resource.metadata.ResourceMetadata;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.io.InputStream;

@Mixin(Resource.class)
public interface ResourceAccessor {

	@Accessor
	ResourcePack getPack();

	@Accessor
	InputSupplier<InputStream> getInputSupplier();

	@Accessor
	InputSupplier<ResourceMetadata> getMetadataSupplier();

	@Nullable
	@Accessor("metadata")
	ResourceMetadata getRawMetadata();

	@Accessor("metadata")
	void setRawMetaData(ResourceMetadata metadata);
}
