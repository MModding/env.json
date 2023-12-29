package fr.firstmegagame4.env.json.impl.resource;

import fr.firstmegagame4.env.json.api.resource.ExtendedResource;
import fr.firstmegagame4.env.json.api.resource.ExtendedResourceReader;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.Reader;

@ApiStatus.Internal
public class ExtendedResourceReaderImpl extends BufferedReader implements ExtendedResourceReader {

	private final ExtendedResource extendedResource;

	public ExtendedResourceReaderImpl(ExtendedResource extendedResource, @NotNull Reader in) {
		super(in);
		this.extendedResource = extendedResource;
	}

	@Override
	public ExtendedResource getExtendedResource() {
		return this.extendedResource;
	}

	@Override
	public BufferedReader asBufferedReader() {
		return this;
	}
}
