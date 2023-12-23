package fr.firstmegagame4.env.json.impl.resource;

import fr.firstmegagame4.env.json.api.EnvJson;
import net.minecraft.resource.InputSupplier;
import org.jetbrains.annotations.ApiStatus;

import java.io.IOException;

@ApiStatus.Internal
public interface ResourceDuckInterface {

	void env_json$initEnvJsonSupplier(InputSupplier<EnvJson> envJsonSupplier);

	void env_json$initEnvJson(EnvJson envJson);

	EnvJson en_json$applyEnvJson() throws IOException;

	InputSupplier<EnvJson> env_json$getEnvJsonSupplier();

	EnvJson env_json$getEnvJson();
}
