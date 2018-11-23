package net.teamfps.savage.renderer.model;

import java.util.List;

import net.teamfps.savage.util.EnumFacing;

/**
 * 
 * @author Zekye
 *
 */
public interface IBakedModel {
	List<BakedQuad> getQuads(EnumFacing side, long rand);
}
