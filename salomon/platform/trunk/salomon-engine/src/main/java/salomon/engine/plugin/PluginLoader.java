/*
 * Copyright (C) 2004 Salomon Team
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

package salomon.engine.plugin;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import salomon.engine.Config;
import salomon.plugin.IPlugin;

/**
 * Class is responsible for dynamic plugin loading. It loads plugin from remote
 * location using PluginClassLoader.
 */
public final class PluginLoader
{

    public static URL getPluginLocation(ILocalPlugin plugin)
    {
        return _pluginsLoaded.get(plugin);
    }

    /**
     * Loads plugin from remote location. If plugin exists in cache, then it is
     * not downloaded, but local instance is returned.
     * 
     * @param url plugin location
     * @return an instance of given plugin
     * @throws Exception
     */
    public static IPlugin loadPlugin(URL url) throws Exception
    {
        IPlugin plugin = null;
        File pluginFile = getFromCache(url);
        if (pluginFile == null) {
            pluginFile = downloadFile(url);
        } else {
            url = new URL("file:///" + pluginFile.getAbsolutePath());
        }
        LOGGER.debug("plugin url: " + url.toString());
        // creating class loader
        PluginClassLoader classLoader = PluginClassLoader.getInstance();
        classLoader.addUrl(url);
        // loading appropriate plugin (it has to be in *.jar file
        plugin = (IPlugin) classLoader.findMainClass(pluginFile).newInstance();
        _pluginsLoaded.put(plugin, url);

        return plugin;
    }

    private static File downloadFile(URL url) throws IOException
    {
        String fileName = Config.PLUGINS_DIR + Config.FILE_SEPARATOR;
        String tmpFileName = url.getFile();
        LOGGER.debug("trying to download: " + tmpFileName);
        fileName += tmpFileName.substring(tmpFileName.lastIndexOf("/") + 1);
        File pluginFile = new File(fileName);
        DataInputStream input = new DataInputStream(new BufferedInputStream(
                url.openStream()));
        DataOutputStream output = new DataOutputStream(
                new BufferedOutputStream(new FileOutputStream(pluginFile)));
        byte buffer[] = new byte[1024];
        int bytesRead = -1;
        while ((bytesRead = input.read(buffer)) > 0) {
            output.write(buffer, 0, bytesRead);
        }
        input.close();
        output.close();
        LOGGER.info("downloaded file: " + pluginFile);

        return pluginFile;
    }

    /**
     * Checks if given plugin exists in local cache.
     * 
     * @param url plugin location
     * @return The file
     */
    private static File getFromCache(URL url)
    {
        File pluginFile = null;
        String tmpFileName = url.getFile();
        String fileName = tmpFileName.substring(tmpFileName.lastIndexOf("/") + 1);
        File dir = new File(Config.PLUGINS_DIR);
        LOGGER.info("looking for plugins in: " + dir.getAbsolutePath());
        //FIXME:
        File[] plugins = dir.listFiles(); //new SearchFileFilter("jar", ""));
        for (int i = 0; i < plugins.length; i++) {
            if (plugins[i].getName().equals(fileName)) {
                LOGGER.info("found in cache: " + plugins[i]);
                pluginFile = plugins[i];
                break;
            }
        }

        return pluginFile;
    }

    private static Map<IPlugin, URL> _pluginsLoaded = new HashMap<IPlugin, URL>();

    private static final Logger LOGGER = Logger.getLogger(PluginLoader.class);
}