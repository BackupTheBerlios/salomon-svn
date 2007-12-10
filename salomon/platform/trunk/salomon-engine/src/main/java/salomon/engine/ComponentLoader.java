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


/**
 * Class bases on ClassPathHacker which can be found:
 * http://forum.java.sun.com/thread.jspa?threadID=300557&start=0&tstart=0
 * 
 */
public class ComponentLoader
{
    private static final Class[] parameters = new Class[]{URL.class};
    
    
    private File _componentDir;
    /**
     * @param _componentDir
     */
    public ComponentLoader(File componentDir)
    {
        _componentDir = componentDir;
        if (_componentDir.isDirectory()) {
            // TODO: add file filter
            File[] files = _componentDir.listFiles();
            for (File file: files) {
                try {
                    addFile(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            throw new IllegalArgumentException(_componentDir + " must be a directory");
        }
    }
    
    public static void addFile(File f) throws IOException {
        addURL(f.toURL());
    }//end method
 
    public static void addFile(String s) throws IOException {
        File f = new File(s);
        addFile(f);
    }//end method
 
    public static void addURL(URL u) throws IOException {
        URLClassLoader sysloader = (URLClassLoader)ClassLoader.getSystemClassLoader();
        Class sysclass = URLClassLoader.class;
        try {
            Method method = sysclass.getDeclaredMethod("addURL",parameters);
            method.setAccessible(true);
            method.invoke(sysloader,new Object[]{ u });
        } catch (Throwable t) {
            t.printStackTrace();
            throw new IOException("Error, could not add URL to system classloader");
        }//end try catch
    }//end method
    
    public Object loadComponent(String className, Class targetClass) throws ClassNotFoundException, InstantiationException, IllegalAccessException
    {
        Class clazz = Class.forName(className);
        Object object = clazz.newInstance();
        if (clazz.isInstance(object)) {
            return object;
        }
        // TODO: object may be null?
        throw new ClassCastException(object.getClass() + " is not an instance of " + targetClass);
    }
}
