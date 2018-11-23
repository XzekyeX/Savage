package net.teamfps.savage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JarLoader {
	private JarFile jar;
	private static final String appdata = System.getenv("APPDATA");
	private String engine = ".savage";
	private String directory = "";
	private List<String> natives = new ArrayList<String>();

	public JarLoader(String path, String file) throws IOException {
		this.jar = new JarFile(new File(path + "/" + file));
		this.directory = appdata + "/" + engine + "/" + file;
	}

	public boolean extract() {
		try {
			natives.clear();
			Enumeration<JarEntry> entries = jar.entries();
			while (entries.hasMoreElements()) {
				JarEntry e = entries.nextElement();
				File dir = new File(directory);
				if (!dir.exists()) {
					System.out.println("Directory was created: " + dir.mkdirs() + ", path: " + dir.getAbsolutePath());
				}
				File f = new File(dir, e.getName());
				if (f.exists() && f.isFile()) {
					if (f.getName().endsWith(".dll")) {
						natives.add(f.getName());
						continue;
					}
					System.out.println("Skipped: " + e + ", path: " + f.getAbsolutePath());
					continue;
				} else {
					if (f.createNewFile()) {
						System.out.println("New file has been created: " + f.getAbsolutePath());
						InputStream is = jar.getInputStream(e);
						BufferedWriter bw = new BufferedWriter(new FileWriter(f));
						System.out.println("Writing file...");
						while (is.available() > 0) {
							bw.write(is.read());
						}
						System.out.println("File has been successfully writed!");
						if (f.getName().endsWith(".dll")) {
							natives.add(f.getName());
						}
						bw.close();
						is.close();
					}
				}
			}
			return true;
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return false;
	}

	public boolean loadNativeFiles() {
		System.setProperty("java.library.path", directory);
		System.out.println("Path: " + System.getProperty("java.library.path"));

		for (String n : natives) {
			String file = n.substring(0, n.length() - 4);
			System.out.println("Trying load library: " + file);
			System.loadLibrary(file);
		}
		return false;
	}

}
