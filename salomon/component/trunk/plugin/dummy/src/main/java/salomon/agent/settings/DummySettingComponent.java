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
package salomon.plugin.settings;

import java.awt.Component;

import salomon.platform.IDataEngine;
import salomon.platform.exception.PlatformException;
import salomon.plugin.ISettingComponent;
import salomon.plugin.ISettings;

/**
 * @author Nikodem.Jura
 *
 */
public class DummySettingComponent implements ISettingComponent {

	/* (non-Javadoc)
	 * @see salomon.plugin.ISettingComponent#getComponent(salomon.plugin.ISettings, salomon.platform.IDataEngine)
	 */
	public Component getComponent(ISettings settings, IDataEngine engine)
			throws PlatformException {
		throw new UnsupportedOperationException(
				"Method DummySettingComponent.getComponent() is not implemented yet!");
	}

	/* (non-Javadoc)
	 * @see salomon.plugin.ISettingComponent#getDefaultSettings()
	 */
	public ISettings getDefaultSettings() {
		return new DummySettings();
	}

	/* (non-Javadoc)
	 * @see salomon.plugin.ISettingComponent#getSettings()
	 */
	public ISettings getSettings() {
		throw new UnsupportedOperationException(
				"Method DummySettingComponent.getSettings() is not implemented yet!");
	}

}
