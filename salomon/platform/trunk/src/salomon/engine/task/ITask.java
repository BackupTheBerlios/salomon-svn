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

package salomon.engine.task;

import salomon.engine.plugin.ILocalPlugin;

import salomon.platform.IInfo;
import salomon.platform.exception.PlatformException;

import salomon.plugin.IPlugin;
import salomon.plugin.IResult;
import salomon.plugin.ISettings;

/** * Interface for tasks. */
public interface ITask
{
	/**
	 * @return Returns the info.
	 */
	IInfo getInfo() throws PlatformException;

	/**
	 * @return Returns the _plugin.
	 * 
	 * @uml.property name="plugin"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	ILocalPlugin getPlugin() throws PlatformException;

	/**
	 * @return Returns the _result.
	 * 
	 * @uml.property name="result"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	IResult getResult() throws PlatformException;

	/**
	 * @return Returns the _settings.
	 * 
	 * @uml.property name="settings"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	ISettings getSettings() throws PlatformException;

	/**
	 * @param plugin The plugin to set.
	 * 
	 * @uml.property name="plugin"
	 */
	void setPlugin(ILocalPlugin plugin) throws PlatformException;

	/**
	 * @param result The result to set.
	 * 
	 * @uml.property name="result"
	 */
	void setResult(IResult result) throws PlatformException;

	/**
	 * @param settings The settings to set.
	 * 
	 * @uml.property name="settings"
	 */
	void setSettings(ISettings settings) throws PlatformException;

}