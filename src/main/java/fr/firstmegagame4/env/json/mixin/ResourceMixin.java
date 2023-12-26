package fr.firstmegagame4.env.json.mixin;

import fr.firstmegagame4.env.json.api.EnvJson;
import fr.firstmegagame4.env.json.impl.EnvJsonUtils;
import fr.firstmegagame4.env.json.impl.resource.ResourceDuckInterface;
import net.minecraft.resource.InputSupplier;
import net.minecraft.resource.Resource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.io.IOException;

@Mixin(Resource.class)
public class ResourceMixin implements ResourceDuckInterface {

	@Unique
	private InputSupplier<EnvJson> envJsonSupplier = EnvJsonUtils.ENV_JSON_NONE_SUPPLIER;

	@Unique
	private EnvJson envJson = EnvJsonUtils.ENV_JSON_NONE;

	@Override
	public void env_json$initEnvJsonSupplier(InputSupplier<EnvJson> envJsonSupplier) {
		this.envJsonSupplier = envJsonSupplier;
	}

	@Override
	public void env_json$initEnvJson(EnvJson envJson) {
		this.envJson = envJson;
	}

	@Override
	public EnvJson en_json$applyEnvJson() throws IOException {
		return this.envJson = this.envJsonSupplier.get();
	}

	@Override
	public InputSupplier<EnvJson> env_json$getEnvJsonSupplier() {
		return this.envJsonSupplier;
	}

	@Override
	public EnvJson env_json$getEnvJson() {
		return this.envJson;
	}
}
