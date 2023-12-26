package fr.firstmegagame4.env.json.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import fr.firstmegagame4.env.json.api.EnvJson;
import fr.firstmegagame4.env.json.impl.EnvJsonUtils;
import fr.firstmegagame4.env.json.impl.resource.ResourceDuckInterface;
import net.fabricmc.fabric.impl.resource.loader.GroupResourcePack;
import net.minecraft.resource.InputSupplier;
import net.minecraft.resource.ResourcePack;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.io.InputStream;

// I know I shouldn't use an impl class, but I have no choice
// Should try to load it with QSL when this one will be available for 1.20.4
@SuppressWarnings("UnstableApiUsage")
@Mixin(value = GroupResourcePack.class, remap = false)
public class GroupResourcePackMixin {

	@WrapOperation(method = "appendResources", at = @At(value = "INVOKE", target = "Lnet/minecraft/resource/ResourcePack;open(Lnet/minecraft/resource/ResourceType;Lnet/minecraft/util/Identifier;)Lnet/minecraft/resource/InputSupplier;"))
	private InputSupplier<InputStream> onResourceAdd(ResourcePack instance, ResourceType resourceType, Identifier identifier, Operation<InputSupplier<InputStream>> original, @Share("envJsonInputSupplier") LocalRef<InputSupplier<EnvJson>> ref) {
		InputSupplier<InputStream> inputSupplier = original.call(instance, resourceType, identifier);
		Identifier envJsonPath = EnvJsonUtils.getEnvJsonPath(identifier);
		if (inputSupplier != null) {
			InputSupplier<EnvJson> envJsonInputSupplier = () -> {
				InputSupplier<InputStream> supplier = instance.open(resourceType, envJsonPath);
				return supplier != null ? EnvJsonUtils.loadEnvJson(supplier) : EnvJsonUtils.ENV_JSON_NONE;
			};
			ref.set(envJsonInputSupplier);
		}
		return inputSupplier;
	}

	@ModifyArg(method = "appendResources", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z"))
	private <E> E onResourceCreated(E e, @Share("envJsonInputSupplier") LocalRef<InputSupplier<EnvJson>> ref) {
		((ResourceDuckInterface) e).env_json$initEnvJsonSupplier(ref.get());
		return e;
	}
}
