package net.teamfps.savage.resources;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * 
 * @author Zekye
 *
 */
public interface IResourceManager {
	Set<String> getResourceDomains();

	IResource getResource(ResourceLocation location) throws IOException;

	List<IResource> getAllResources(ResourceLocation location) throws IOException;
}
