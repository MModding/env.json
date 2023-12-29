package fr.firstmegagame4.env.json.api.resource;

import java.io.BufferedReader;

public interface ExtendedResourceReader {

	ExtendedResource getExtendedResource();

	BufferedReader asBufferedReader();
}
