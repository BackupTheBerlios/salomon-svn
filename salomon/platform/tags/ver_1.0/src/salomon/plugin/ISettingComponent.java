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

package salomon.plugin;

import java.awt.Component;

import salomon.platform.IDataEngine;

/**
 * Interface represents object, which is responsible for showing plugin
 * settings.
 */
public interface ISettingComponent
{
	/**
	 * Returns settings object. It is set basing on user selections in setting
	 * panel
	 * 
	 * @return plugin settings
	 */
	ISettings getSettings();

	/**
	 * Method create default settings for plugin.
	 * 
	 * @return default settings for plugin
	 */
	ISettings getDefaultSettings();

	/**
	 * Fills settings component basing on given settings.
	 * 
	 * @param settings settings object
	 * @return component showing given settings
	 */
	Component getComponent(ISettings settings, IDataEngine dataEngine);
}