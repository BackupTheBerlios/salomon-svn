/*
 * Copyright (C) 2007 salomon-engine Team
 *
 * This file is part of salomon-engine.
 *
 * salomon-engine is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * salomon-engine is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with salomon-engine; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * 
 */

package salomon.engine;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

import org.apache.log4j.Logger;

/**
 * Class bases on ClassPathHacker which can be found:
 * http://forum.java.sun.com/thread.jspa?threadID=300557&start=0&tstart=0
 * 
 */
public class ComponentLoader {
	static final Logger LOGGER = Logger.getLogger(ComponentLoader.class);
	
	private static final Class[] parameters = new Class[] { URL.class };

	private File _componentDir;	
	
	public static void addFile(File f) throws IOException {
		addURL(f.toURL());
	}

	public static void addFile(String s) throws IOException {
		File f = new File(s);
		addFile(f);
	}

	public static void addURL(URL u) throws IOException {
		URLClassLoader sysloader = (URLClassLoader) ClassLoader
				.getSystemClassLoader();
		Class sysclass = URLClassLoader.class;
		try {
			Method method = sysclass.getDeclaredMethod("addURL", parameters);
			method.setAccessible(true);
			method.invoke(sysloader, new Object[] { u });
		} catch (Throwable t) {
			t.printStackTrace();
			throw new IOException(
					"Error, could not add URL to system classloader");
		}
	}

	public File getComponentDir() {
		return _componentDir;
	}

	public String getComponentDirName() {
		return _componentDir == null ? "" : _componentDir.getName();
	}

	public void initialize() {
		LOGGER.info("Loading components from dir: " + _componentDir.getAbsolutePath());
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
	public Object loadComponent(String className) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		// FIXME: doesn't work with maven -- ClassNotFoundException is thrown
//		Class clazz = ClassLoader.getSystemClassLoader().loadClass(className);
		Class clazz = Class.forName(className);
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
