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

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

/**
 * 
 */
public class Version
{
    private Version()
    {
        // prevent creating an instance
    }

	/**
	 * reads specified property
	 * @param key
	 * @return String matching the given key
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

    private static final String BUNDLE_NAME = "version";//$NON-NLS-1$

    private static final Logger LOGGER = Logger.getLogger(Version.class);

    private static ResourceBundle RESOURCE_BUNDLE;
    
    static {
        try {
            RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
        } catch (Exception e) {
            LOGGER.fatal("", e);
        }
    }
}
