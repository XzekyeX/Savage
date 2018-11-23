package net.teamfps.savage.renderer.model;

import net.teamfps.savage.registry.IRegistry;
import net.teamfps.savage.resources.IResourceManager;
import net.teamfps.savage.resources.IResourceManagerReloadListener;

/**
 * 
 * @author Zekye
 *
 */
public class ModelManager implements IResourceManagerReloadListener {
	private IRegistry<ModelResourceLocation, IBakedModel> modelRegistry;

	@Override
	public void onResourceManagerReload(IResourceManager manager) {

	}

}
