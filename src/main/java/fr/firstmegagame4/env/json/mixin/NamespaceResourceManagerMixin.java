package fr.firstmegagame4.env.json.mixin;

import com.google.common.collect.Iterators;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import fr.firstmegagame4.env.json.api.EnvJson;
import fr.firstmegagame4.env.json.impl.EnvJsonInitializer;
import fr.firstmegagame4.env.json.impl.EnvJsonUtils;
import fr.firstmegagame4.env.json.impl.resource.EntryListDuckInterface;
import fr.firstmegagame4.env.json.impl.resource.ResourceDuckInterface;
import fr.firstmegagame4.env.json.impl.resource.ResourceResult;
import fr.firstmegagame4.env.json.impl.resource.ResultAccess;
import net.minecraft.resource.*;
import net.minecraft.resource.metadata.ResourceMetadata;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.InputStream;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

@Mixin(NamespaceResourceManager.class)
public abstract class NamespaceResourceManagerMixin {

	@Shadow
	@Final
	protected List<NamespaceResourceManager.FilterablePack> packList;

	@Shadow
	@Final
	private ResourceType type;

	@Shadow
	private static Resource createResource(ResourcePack pack, Identifier id, InputSupplier<InputStream> supplier, InputSupplier<ResourceMetadata> metadataSupplier) {
		throw new AssertionError();
	}

	@Shadow
	static Identifier getMetadataPath(Identifier id) {
		throw new AssertionError();
	}

	@Shadow
	private static InputSupplier<ResourceMetadata> getMetadataSupplier(InputSupplier<InputStream> supplier) {
		throw new AssertionError();
	}

	@ModifyExpressionValue(method = "getResource", at = @At(value = "INVOKE", target = "Lnet/minecraft/resource/NamespaceResourceManager;createResource(Lnet/minecraft/resource/ResourcePack;Lnet/minecraft/util/Identifier;Lnet/minecraft/resource/InputSupplier;Lnet/minecraft/resource/InputSupplier;)Lnet/minecraft/resource/Resource;"))
	private Resource onResourceCreated(Resource original, Identifier identifier, @Local(ordinal = 0) int i) {
		((ResourceDuckInterface) original).env_json$initEnvJsonSupplier(this.createEnvJsonSupplier(identifier, i));
		return original;
	}

	@WrapOperation(method = "getAllResources", at = @At(value = "INVOKE", target = "Lnet/minecraft/resource/ResourcePack;open(Lnet/minecraft/resource/ResourceType;Lnet/minecraft/util/Identifier;)Lnet/minecraft/resource/InputSupplier;"))
	private InputSupplier<InputStream> onResourceAdd(ResourcePack instance, ResourceType resourceType, Identifier identifier, Operation<InputSupplier<InputStream>> original, @Local boolean bl, @Share("envJsonInputSupplier") LocalRef<InputSupplier<EnvJson>> ref) {
		InputSupplier<InputStream> inputSupplier = original.call(instance, resourceType, identifier);
		Identifier envJsonPath = EnvJsonUtils.getEnvJsonPath(identifier);
		if (inputSupplier != null) {
			InputSupplier<EnvJson> envJsonInputSupplier;
			if (bl) {
				envJsonInputSupplier = () -> null;
			}
			else {
				envJsonInputSupplier = () -> {
					InputSupplier<InputStream> supplier = instance.open(resourceType, envJsonPath);
					return supplier != null ? EnvJsonUtils.loadEnvJson(supplier) : EnvJsonUtils.ENV_JSON_NONE;
				};
			}
			ref.set(envJsonInputSupplier);
		}
		return inputSupplier;
	}

	@ModifyArg(method = "getAllResources", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z"))
	private <E> E onResourceCreated(E e, @Share("envJsonInputSupplier") LocalRef<InputSupplier<EnvJson>> ref) {
		((ResourceDuckInterface) e).env_json$initEnvJsonSupplier(ref.get());
		return e;
	}

	@Inject(method = "findResources", at = @At(value = "INVOKE", target = "Ljava/util/HashMap;<init>()V", shift = At.Shift.AFTER, ordinal = 1))
	private void createEnvJsonMap(String startingPath, Predicate<Identifier> allowedPathPredicate, CallbackInfoReturnable<Map<Identifier, Resource>> cir, @Share("envJsonMap") LocalRef<Map<Identifier, ResourceResult>> envJsonMap) {
		envJsonMap.set(new HashMap<>());
	}

	@Inject(method = "findResources", at = @At(value = "INVOKE", target = "Lnet/minecraft/resource/NamespaceResourceManager$FilterablePack;removeFiltered(Ljava/util/Collection;)V", shift = At.Shift.AFTER, ordinal = 1))
	private void removeFilteredEnvJson(String startingPath, Predicate<Identifier> allowedPathPredicate, CallbackInfoReturnable<Map<Identifier, Resource>> cir, @Local NamespaceResourceManager.FilterablePack filterablePack, @Share("envJsonMap") LocalRef<Map<Identifier, ResourceResult>> envJsonMap) {
		filterablePack.removeFiltered(envJsonMap.get().keySet());
	}

	@ModifyArg(method = "findResources", at = @At(value = "INVOKE", target = "Lnet/minecraft/resource/ResourcePack;findResources(Lnet/minecraft/resource/ResourceType;Ljava/lang/String;Ljava/lang/String;Lnet/minecraft/resource/ResourcePack$ResultConsumer;)V"), index = 3)
	private ResourcePack.ResultConsumer mutateBiConsumer(ResourcePack.ResultConsumer consumer, @Local(ordinal = 0) Predicate<Identifier> allowedPathPredicate, @Local(ordinal = 0) ResourcePack resourcePack, @Local(ordinal = 2) int k, @Share("envJsonMap") LocalRef<Map<Identifier, ResourceResult>> envJsonMap) {
		return (identifier, supplier) -> {
			if (EnvJsonUtils.isEnvJson(identifier)) {
				if (allowedPathPredicate.test(EnvJsonUtils.getEnvJsonFileName(identifier))) {
					envJsonMap.get().put(identifier, new ResourceResult(resourcePack, supplier, k));
				}
			}
			else {
				consumer.accept(identifier, supplier);
			}
		};
	}

	@ModifyArg(method = "findResources", at = @At(value = "INVOKE", target = "Ljava/util/Map;forEach(Ljava/util/function/BiConsumer;)V"))
	private <K extends Identifier, V extends ResultAccess> BiConsumer<? super K, ? super V> mutateForEach(BiConsumer<? super K, ? super V> action, @Local(ordinal = 1) Map<Identifier, ?> results, @Local(ordinal = 2) Map<Identifier, Resource> resources, @Share("envJsonMap") LocalRef<Map<Identifier, ResourceResult>> envJsonMap) {
		return (identifier, result) -> {
			Identifier envJsonIdentifier = EnvJsonUtils.getEnvJsonPath(identifier);
			ResourceResult resourceResult = envJsonMap.get().get(envJsonIdentifier);
			if (resourceResult != null && resourceResult.packIndex() >= result.invokePackIndex()) {
				Identifier metadataIdentifier = NamespaceResourceManagerMixin.getMetadataPath(identifier);
				ResultAccess access = (ResultAccess) results.get(identifier);
				InputSupplier<ResourceMetadata> metadataSupplier;
				if (access != null && access.invokePackIndex() >= result.invokePackIndex()) {
					metadataSupplier = NamespaceResourceManagerMixin.getMetadataSupplier(access.invokeSupplier());
				} else {
					metadataSupplier = ResourceMetadata.NONE_SUPPLIER;
				}
				ResourceDuckInterface ducked = (ResourceDuckInterface) NamespaceResourceManagerMixin.createResource(result.invokePack(), identifier, result.invokeSupplier(), metadataSupplier);
				ducked.env_json$initEnvJsonSupplier(EnvJsonUtils.getEnvJsonSupplier(resourceResult.supplier()));
				resources.put(metadataIdentifier, (Resource) ducked);
			}
			else {
				action.accept(identifier, result);
			}
		};
	}

	@ModifyExpressionValue(method = "applyFilter", at = @At(value = "INVOKE", target = "Ljava/util/Collection;iterator()Ljava/util/Iterator;"))
	private static <E> Iterator<E> mutateIterator(Iterator<E> original, NamespaceResourceManager.FilterablePack pack) {
		Iterator<E> iterator = Iterators.filter(
			original,
			object -> !pack.isFiltered(((NamespaceResourceManager.EntryList) object).id()) && !pack.isFiltered(((NamespaceResourceManager.EntryList) object).metadataId())
		);
		iterator.forEachRemaining(object -> {
			EntryListDuckInterface ducked = (EntryListDuckInterface) object;
			if (pack.isFiltered(ducked.env_json$getEnvJsonIdentifier())) {
				ducked.env_json$getEnvJsonResources().clear();
			}
		});
		return Iterators.filter(
			original,
			object -> pack.isFiltered(((NamespaceResourceManager.EntryList) object).id()) || pack.isFiltered(((NamespaceResourceManager.EntryList) object).metadataId())
		);
	}

	@ModifyArg(method = "findAndAdd", at = @At(value = "INVOKE", target = "Lnet/minecraft/resource/ResourcePack;findResources(Lnet/minecraft/resource/ResourceType;Ljava/lang/String;Ljava/lang/String;Lnet/minecraft/resource/ResourcePack$ResultConsumer;)V"), index = 3)
	private ResourcePack.ResultConsumer mutateBiConsumer(ResourcePack.ResultConsumer consumer, @Local(ordinal = 0) ResourcePack resourcePack, @Local(ordinal = 0) Predicate<Identifier> allowedPathPredicate, @Local(ordinal = 0) Map<Identifier, NamespaceResourceManager.EntryList> idToEntryList) {
		return (identifier, supplier) -> {
			if (EnvJsonUtils.isEnvJson(identifier)) {
				Identifier envJsonIdentifier = EnvJsonUtils.getEnvJsonFileName(identifier);
				if (allowedPathPredicate.test(envJsonIdentifier)) {
					((EntryListDuckInterface) (Object) idToEntryList.computeIfAbsent(identifier, NamespaceResourceManager.EntryList::new))
						.env_json$putEnvJsonSource(resourcePack, supplier);
				}
			}
			else {
				consumer.accept(identifier, supplier);
			}
		};
	}

	@ModifyArg(method = "findAllResources", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z"))
	private <E> E mutateResource(E e, @Local(ordinal = 0) InputSupplier<InputStream> inputSupplier) {
		InputSupplier<EnvJson> envJsonSupplier = inputSupplier != null ? EnvJsonUtils.getEnvJsonSupplier(inputSupplier) : EnvJsonUtils.ENV_JSON_NONE_SUPPLIER;
		((ResourceDuckInterface) e).env_json$initEnvJsonSupplier(envJsonSupplier);
		return e;
	}

	@Unique
	private InputSupplier<EnvJson> createEnvJsonSupplier(Identifier identifier, int index) {
		return () -> {
			Identifier envJsonPath = EnvJsonUtils.getEnvJsonPath(identifier);
			// noinspection OverflowingLoopIndex
			for (int i = this.packList.size() - 1; i >= index; i++) {
				NamespaceResourceManager.FilterablePack filterablePack = this.packList.get(i);
				ResourcePack resourcePack = filterablePack.underlying();
				if (resourcePack != null) {
					InputSupplier<InputStream> inputSupplier = resourcePack.open(this.type, envJsonPath);
					if (inputSupplier != null) {
						return EnvJsonUtils.loadEnvJson(inputSupplier);
					}
				}
				if (filterablePack.isFiltered(envJsonPath)) {
					break;
				}
			}
			return EnvJsonUtils.ENV_JSON_NONE;
		};
	}
}
