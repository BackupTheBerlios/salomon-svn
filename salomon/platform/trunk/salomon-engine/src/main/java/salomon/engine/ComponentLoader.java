/*
 * Copyright (C) 2007 Salomon Team
 *
 * This file is part of Salomon.
 *
 * Salomon is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * Salomon is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Salomon; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * 
 */

package salomon.engine;

import java.io.File;
import java.io.IOException;
import java.net.URLClassLoader;

import org.apache.log4j.Logger;

/**
 * Class loader is used to load classed from dynamically changing locations.
 */
public class ComponentLoader extends URLClassLoader {
	static final Logger LOGGER = Logger.getLogger(ComponentLoader.class);

	private File _componentDir;

	public void addFile(File f) throws IOException {
		addURL(f.toURL());
	}

	public void addFile(String s) throws IOException {
		File f = new File(s);
		addFile(f);
	}

	public ComponentLoader() {
		// adds the URL[] from the classLoader which loaded this class
		// sets the parent classLoader properly
		super(((URLClassLoader) ComponentLoader.class.getClassLoader())
				.getURLs(), ComponentLoader.class.getClassLoader());
	}

	public File getComponentDir() {
		return _componentDir;
	}

	public String getComponentDirName() {
		return _componentDir == null ? "" : _componentDir.getName();
	}

	public void initialize() {
		LOGGER.info("Loading components from dir: "
				+ _componentDir.getAbsolutePath());
		if (_componentDir.isDirectory()) {
			// TODO: add file filter
			File[] files = _componentDir.listFiles();
			for (File file : files) {
				try {
					LOGGER.info("Adding file: " + file.getName());
					addFile(file);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			throw new IllegalArgumentException(_componentDir
					+ " must be a directory");
		}
	}

	@SuppressWarnings("unchecked")
	public Object loadComponent(String className)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException {
		Class clazz = this.loadClass(className);
		Object object = clazz.newInstance();
		return object;
	}

	public void setComponentDir(File componentDir) {
		_componentDir = componentDir;
	}

	public void setComponentDirName(String compDirName) {
		_componentDir = new File(compDirName);
	}
}
