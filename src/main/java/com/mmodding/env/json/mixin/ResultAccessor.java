package com.mmodding.env.json.mixin;

import com.mmodding.env.json.impl.resource.ResultAccess;
import net.minecraft.resource.InputSupplier;
import net.minecraft.resource.ResourcePack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.io.InputStream;

@Mixin(targets = "net.minecraft.resource.NamespaceResourceManager$Result")
public interface ResultAccessor extends ResultAccess {

	@Invoker("pack")
	ResourcePack invokePack();

	@Invoker("supplier")
	InputSupplier<InputStream> invokeSupplier();

	@Invoker("packIndex")
	int invokePackIndex();
}
