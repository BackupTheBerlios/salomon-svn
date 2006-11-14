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

package salomon.engine;

import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import salomon.platform.exception.ConfigurationException;

/**
 * Class is responsible for managing base configuration of application. It holds
 * resource bundle for config.properties file.
 */
public final class Config
{
    /**
     * Current working directory.
     */
    public static String CURR_DIR;

    /**
     * Sample databases directory.
     */
    public static String DB_DIR;

    /**
     * Main Salomon DB file.
     */
    public static String DB_PATH;

    /**
     * System defined file separator.
     */
    public static String FILE_SEPARATOR;

    /**
     * Hostname where DB is located.
     */
    public static String HOSTNAME;

    /**
     * User password.
     */
    public static String PASSWORD;

    /**
     * Plugins directory.
     */
    public static String PLUGIN_DIR;

    /**
     * Resources directory.
     */
    public static String RESOURCES_DIR;

    /**
     * User name.
     */
    public static String USER;

    private static final String BUNDLE_NAME = "config";//$NON-NLS-1$

    private static ResourceBundle RESOURCE_BUNDLE;

    public static int SPLASH_TIME;

    public static void readConfiguration() throws ConfigurationException
    {
        try {
            RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME); //$NON-NLS-1$
            FILE_SEPARATOR = System.getProperty("file.separator");
            CURR_DIR = System.getProperty("user.dir");

            DB_DIR = RESOURCE_BUNDLE.getString("DB_DIR"); //$NON-NLS-1$
            PLUGIN_DIR = RESOURCE_BUNDLE.getString("PLUGIN_DIR"); //$NON-NLS-1$
            RESOURCES_DIR = RESOURCE_BUNDLE.getString("RESOURCES_DIR"); //$NON-NLS-1$
            HOSTNAME = RESOURCE_BUNDLE.getString("HOSTNAME"); //$NON-NLS-1$
            DB_PATH = RESOURCE_BUNDLE.getString("DB_PATH"); //$NON-NLS-1$
            USER = RESOURCE_BUNDLE.getString("USER"); //$NON-NLS-1$
            PASSWORD = RESOURCE_BUNDLE.getString("PASSWD"); //$NON-NLS-1$   
            SPLASH_TIME = Integer.parseInt(RESOURCE_BUNDLE.getString("SPLASH_TIME")); //$NON-NLS-1$
        } catch (Exception e) {
            LOGGER.fatal("", e);
            throw new ConfigurationException(e);
        }
    }

    private static final Logger LOGGER = Logger.getLogger(Config.class);
}