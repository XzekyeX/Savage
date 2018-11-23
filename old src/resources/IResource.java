package net.teamfps.savage.resources;

import java.io.Closeable;
import java.io.InputStream;

/**
 * 
 * @author Zekye
 *
 */
public interface IResource extends Closeable {
	ResourceLocation getResourceLocation();

	InputStream getInputStream();

	boolean hasMetadata();

	<T extends IMetadataSection> T getMetadata(String sectionName);

	String getResourcePackName();
}
