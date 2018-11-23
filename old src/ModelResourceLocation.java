package net.teamfps.savage.renderer.model;

import org.apache.commons.lang3.StringUtils;

import net.teamfps.savage.resources.ResourceLocation;

/**
 * 
 * @author Zekye
 *
 */
public class ModelResourceLocation extends ResourceLocation {
	private final String variant;

	public ModelResourceLocation(String... resourceName) {
		super(new String[] { resourceName[0], resourceName[1] });
		this.variant = StringUtils.isEmpty(resourceName[2]) ? "" : resourceName[2].toLowerCase();
	}

	public ModelResourceLocation(String resourceName) {
		this(parsePathString(resourceName));
	}

	public ModelResourceLocation(String resourceName, String resourceVariant) {
		this(parsePathString(resourceName + "#" + (resourceVariant == null ? "" : resourceVariant)));
	}

	protected static String[] parsePathString(String str) {
		String[] result = new String[] { null, str, null };
		int i = str.indexOf('#');
		String s = str;
		if (i >= 0) {
			result[2] = str.substring(i + 1, str.length());
			if (i > 1) s = str.substring(0, i);
		}
		System.arraycopy(ResourceLocation.splitObjectName(s), 0, result, 0, 2);
		return result;
	}

	public String getVariant() {
		return variant;
	}

	@Override
	public String toString() {
		return super.toString() + "#" + this.variant;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ModelResourceLocation && super.equals(obj)) {
			ModelResourceLocation mrl = (ModelResourceLocation) obj;
			return this.variant.equals(mrl.variant);
		}
		return this == obj;
	}

	@Override
	public int hashCode() {
		return 31 * super.hashCode() + this.variant.hashCode();
	}
}
