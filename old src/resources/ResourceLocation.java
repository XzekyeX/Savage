package net.teamfps.savage.resources;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

/**
 * 
 * @author Zekye
 *
 */
public class ResourceLocation {
	protected final String resourceDomain;
	protected final String resourcePath;

	private static final String engine = "savage";

	public ResourceLocation(String... resourceName) {
		this.resourceDomain = StringUtils.isEmpty(resourceName[0]) ? engine : resourceName[0].toLowerCase();
		this.resourcePath = resourceName[1];
		Validate.notNull(this.resourcePath);
	}

	public ResourceLocation(String resourceName) {
		this(splitObjectName(resourceName));
	}

	public ResourceLocation(String resourceDomain, String resourcePath) {
		this(new String[] { resourceDomain, resourcePath });
	}

	protected static String[] splitObjectName(String str) {
		String[] result = new String[] { engine, str };
		int i = str.indexOf(':');
		if (i >= 0) {
			result[1] = str.substring(i + 1, str.length());
			if (i > 1) result[0] = str.substring(0, i);
		}
		return result;
	}

	public String getResourceDomain() {
		return this.resourceDomain;
	}

	public String getResourcePath() {
		return this.resourcePath;
	}

	@Override
	public String toString() {
		return this.resourceDomain + ":" + this.resourcePath;
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj instanceof ResourceLocation)) {
			ResourceLocation rl = (ResourceLocation) obj;
			return this.resourceDomain.equals(rl.resourceDomain) && this.resourcePath.equals(rl.resourcePath);
		}
		return this == obj;
	}

	@Override
	public int hashCode() {
		return 31 * this.resourceDomain.hashCode() + this.resourcePath.hashCode();
	}
}
