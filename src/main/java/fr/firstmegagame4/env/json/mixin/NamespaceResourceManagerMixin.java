package fr.firstmegagame4.env.json.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import fr.firstmegagame4.env.json.api.EnvJson;
import fr.firstmegagame4.env.json.impl.resource.ResourceDuckInterface;
import net.minecraft.resource.*;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Mixin(NamespaceResourceManager.class)
public class NamespaceResourceManagerMixin {

	@Unique
	private static final EnvJson ENV_JSON_NONE = null;

	@Shadow
	@Final
	protected List<NamespaceResourceManager.FilterablePack> packList;

	@Shadow
	@Final
	private ResourceType type;

	@ModifyExpressionValue(method = "getResource", at = @At(value = "INVOKE", target = "Lnet/minecraft/resource/NamespaceResourceManager;createResource(Lnet/minecraft/resource/ResourcePack;Lnet/minecraft/util/Identifier;Lnet/minecraft/resource/InputSupplier;Lnet/minecraft/resource/InputSupplier;)Lnet/minecraft/resource/Resource;"))
	private Resource onResourceCreated(Resource original, Identifier identifier, @Local(ordinal = 0) int i) {
		((ResourceDuckInterface) original).env_json$initEnvJsonSupplier(this.createEnvJsonSupplier(identifier, i));
		return original;
	}

	@WrapOperation(method = "getAllResources", at = @At(value = "INVOKE", target = "Lnet/minecraft/resource/ResourcePack;open(Lnet/minecraft/resource/ResourceType;Lnet/minecraft/util/Identifier;)Lnet/minecraft/resource/InputSupplier;"))
	private InputSupplier<InputStream> onResourceAdd(ResourcePack instance, ResourceType resourceType, Identifier identifier, Operation<InputSupplier<InputStream>> original, @Local(ordinal = 0) boolean bl, @Share("envJsonInputSupplier") LocalRef<InputSupplier<EnvJson>> ref) {
		InputSupplier<InputStream> inputSupplier = original.call(instance, resourceType, identifier);
		Identifier envJsonPath = this.getEnvJsonPath(identifier);
		if (inputSupplier != null) {
			InputSupplier<EnvJson> envJsonInputSupplier;
			if (bl) {
				envJsonInputSupplier = () -> null;
			}
			else {
				envJsonInputSupplier = () -> {
					InputSupplier<InputStream> supplier = instance.open(resourceType, envJsonPath);
					return supplier != null ? this.loadEnvJson(supplier) : NamespaceResourceManagerMixin.ENV_JSON_NONE;
				};
			}
			ref.set(envJsonInputSupplier);
		}
		return inputSupplier;
	}

	@WrapOperation(method = "getAllResources", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z"))
	private <E> boolean onResourceCreated(List<E> instance, E e, Operation<Boolean> original, @Share("envJsonInputSupplier") LocalRef<InputSupplier<EnvJson>> ref) {
		((ResourceDuckInterface) e).env_json$initEnvJsonSupplier(ref.get());
		// noinspection MixinExtrasOperationParameters
		return original.call(instance, e);
	}

	@Unique
	private Identifier getEnvJsonPath(Identifier identifier) {
		return identifier.withPath(identifier.getPath() + ".env.json");
	}

	@Unique
	private InputSupplier<EnvJson> createEnvJsonSupplier(Identifier identifier, int index) {
		return () -> {
			Identifier envJsonPath = this.getEnvJsonPath(identifier);
			// noinspection OverflowingLoopIndex
			for (int i = this.packList.size() - 1; i >= index; i++) {
				NamespaceResourceManager.FilterablePack filterablePack = this.packList.get(i);
				ResourcePack resourcePack = filterablePack.underlying();
				if (resourcePack != null) {
					InputSupplier<InputStream> inputSupplier = resourcePack.open(this.type, envJsonPath);
					if (inputSupplier != null) {
						return this.loadEnvJson(inputSupplier);
					}
				}
				if (filterablePack.isFiltered(envJsonPath)) {
					break;
				}
			}
			return null;
		};
	}

	@Unique
	private EnvJson loadEnvJson(InputSupplier<InputStream> supplier) throws IOException {
		InputStream inputStream = supplier.get();
		EnvJson envJson = EnvJson.parse(inputStream);
		inputStream.close();
		return envJson;
	}
}
