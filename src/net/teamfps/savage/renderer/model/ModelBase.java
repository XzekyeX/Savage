package net.teamfps.savage.renderer.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.teamfps.java.serialization.math.Vec2f;
import net.teamfps.java.serialization.math.Vec3f;
import net.teamfps.savage.StateManager;
import net.teamfps.savage.renderer.Mesh;
import net.teamfps.savage.renderer.MeshBuffer;

/**
 * 
 * @author Zekye
 *
 */
public class ModelBase {
	protected String file;
	protected int count, vao, vbo, ibo, tbo, nbo;
	protected Mesh mesh;

	public ModelBase(String file) {
		this.file = file;
		load();
	}

	public ModelBase(Mesh mesh) {
		this.mesh = mesh;
	}

	private void load() {
		List<Vec3f> vertices = new ArrayList<Vec3f>();
		List<Vec2f> textures = new ArrayList<Vec2f>();
		List<Vec3f> normals = new ArrayList<Vec3f>();
		List<Face> faces = new ArrayList<Face>();
		try {
			File f = new File(file);
			BufferedReader br = new BufferedReader(new FileReader(f));
			System.out.println("Loading Model: " + f.getAbsolutePath());
			String line = "";
			while ((line = br.readLine()) != null) {
				String[] tokens = StateManager.RemoveEmptyStrings(line.split(" "));
				if (tokens.length == 0 || tokens[0].equals("#")) {
					continue;
				} else {
					switch (tokens[0].toLowerCase()) {
						case "v":
							vertices.add(new Vec3f(Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2]), Float.parseFloat(tokens[3])));
							break;
						case "vt":
							textures.add(new Vec2f(Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2])));
							break;
						case "vn":
							normals.add(new Vec3f(Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2]), Float.parseFloat(tokens[3])));
							break;
						case "f":
							Face face = new Face(tokens[1], tokens[2], tokens[3]);
							faces.add(face);
							if (tokens.length > 4) {
								Face face1 = new Face(tokens[1], tokens[3], tokens[4]);
								faces.add(face1);
							}
							break;
					}
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// System.out.println("Model has " + vertices.size() + " vertices, " + tcoords.size() + " texture coordinates, " + normals.size() + " normals and " + indices.size() + " indices.");
		mesh = new Mesh(MeshBuffer.reorder(vertices, textures, normals, faces));
	}

	protected void render() {
		if (mesh != null) mesh.render();
	}
	
	public Mesh getMesh() {
		return mesh;
	}
}
