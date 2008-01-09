/*
 * Copyright (C) 2008 Salomon Team
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
/**
 * 
 */
package salomon.plugin;

import org.apache.log4j.Logger;

import salomon.platform.IDataEngine;
import salomon.platform.IEnvironment;
import salomon.platform.IVariable;
import salomon.plugin.settings.DummySettingComponent;
import salomon.plugin.settings.DummySettings;

/**
 * @author Nikodem.Jura
 * 
 */
public class DummyPlugin implements IPlugin {

	private static final Logger LOGGER = Logger.getLogger(DummyPlugin.class);

	private DummySettingComponent _settingComponent;

	public DummyPlugin() {
		_settingComponent = new DummySettingComponent();
	}

	/**
	 * Plugin searches for a word in a given text. Returns the number of
	 * occurrences.
	 * 
	 * TODO: If a text contains given word generate an event (a message should
	 * be added to the storage then)
	 */
	public IResult doJob(IDataEngine dataEngine, IEnvironment env,
			ISettings settings) {		
		LOGGER.info("DummyPlugin.doJob()");

		DummySettings ds = (DummySettings) settings;
		String word = ds.getWord();
		// TODO define the constant identifying the current message
		LOGGER.debug("Variables:");
		for (IVariable var : env.getAll()) {
			LOGGER.debug(var);
		}
		IVariable textVar = env.getVariable("message");
		String text = (String) textVar.getValue().getValue();
		
		LOGGER.debug("Searching for " + word + " in:");
		LOGGER.debug(text);
		// TODO:
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.plugin.IGraphicPlugin#getResultComponent()
	 */
	public IResultComponent getResultComponent() {
		throw new UnsupportedOperationException(
				"Method DummyPlugin.getResultComponent() is not implemented yet!");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.plugin.IGraphicPlugin#getSettingComponent(salomon.plugin.IPlatformUtil)
	 */
	public ISettingComponent getSettingComponent(IPlatformUtil arg0) {
		return _settingComponent;
	}

}
