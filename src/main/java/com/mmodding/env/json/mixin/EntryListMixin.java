package com.mmodding.env.json.mixin;

import com.mmodding.env.json.impl.resource.EntryListDuckInterface;
import com.mmodding.env.json.impl.EnvJsonUtils;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.resource.InputSupplier;
import net.minecraft.resource.NamespaceResourceManager;
import net.minecraft.resource.ResourcePack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.InputStream;
import java.util.Map;

@Mixin(NamespaceResourceManager.EntryList.class)
public class EntryListMixin implements EntryListDuckInterface {

	@Unique
	@Final
	@Mutable
	private Identifier envJsonIdentifier;

	@Unique
	@Final
	@Mutable
	private Map<ResourcePack, InputSupplier<InputStream>> envJsonSources;

	@Inject(method = "<init>(Lnet/minecraft/util/Identifier;)V", at = @At("TAIL"))
	private void envJsonInit(Identifier id, CallbackInfo ci) {
		this.envJsonIdentifier = EnvJsonUtils.getEnvJsonPath(id);
		this.envJsonSources = new Object2ObjectArrayMap<>();
	}

	@Override
	public Identifier env_json$getEnvJsonIdentifier() {
		return this.envJsonIdentifier;
	}

	@Override
	public Map<ResourcePack, InputSupplier<InputStream>> env_json$getEnvJsonResources() {
		return this.envJsonSources;
	}

	@Override
	public void env_json$putEnvJsonSource(ResourcePack resourcePack, InputSupplier<InputStream> inputSupplier) {
		this.envJsonSources.put(resourcePack, inputSupplier);
	}
}
