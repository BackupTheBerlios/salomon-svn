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

package salomon.engine.platform;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

/**
 * Class is responsible for managing base configuration of application. It holds
 * resource bundle for config.properties file.
 */
public final class Config
{

	/**
	 */
	public static String getString(String key)
	{
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			LOGGER.fatal("", e);
			return '!' + key + '!';
		}
	}

	public static String CURR_DIR = null;

	public static String FILE_SEPARATOR = null;

	private static final String BUNDLE_NAME = "config";//$NON-NLS-1$

	private static final Logger LOGGER = Logger.getLogger(Config.class);

	private static ResourceBundle RESOURCE_BUNDLE = null;

	static {
		try {
			RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
			FILE_SEPARATOR = System.getProperty("file.separator");
			CURR_DIR = System.getProperty("user.dir");
		} catch (Exception e) {
			LOGGER.fatal("", e);
		}
	}
}