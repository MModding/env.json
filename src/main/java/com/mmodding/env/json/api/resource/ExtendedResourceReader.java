package com.mmodding.env.json.api.resource;

import java.io.BufferedReader;

public interface ExtendedResourceReader {

	ExtendedResource getExtendedResource();

	BufferedReader asBufferedReader();
}
