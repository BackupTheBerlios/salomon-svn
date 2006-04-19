/*
 * Copyright (C) 2006 Salomon Team
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

package pl.edu.agh.iisg.salomon.plugin.datasetvis.settings;

import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JTextField;

import salomon.platform.IDataEngine;
import salomon.plugin.ISettingComponent;
import salomon.plugin.ISettings;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;

/**
 * @author nico
 */
public final class VisSettingComponent implements ISettingComponent
{
	private JComponent _settingComponent;

	private JTextField _txtDataSetName;

	public VisSettingComponent()
	{
		_txtDataSetName = new JTextField();
	}

	/**
	 * 
	 */
	public Component getComponent(ISettings settings, IDataEngine dataEngine)
	{
		if (_settingComponent == null) {
			_settingComponent = createSettingComponent();
		}
		initSettingsComponent(settings);
		return _settingComponent;
	}

	/**
	 * 
	 */
	public ISettings getDefaultSettings()
	{
		return new VisSettings();
	}

	/**
	 * 
	 */
	public ISettings getSettings()
	{
		VisSettings settings = new VisSettings();
		// setting data seta name
		settings.setDataSetName(_txtDataSetName.getText());
		return settings;
	}

	private JComponent createSettingComponent()
	{
        FormLayout layout = new FormLayout(
                "fill:default:grow, 3dlu, fill:100dlu:grow", "");
        DefaultFormBuilder builder = new DefaultFormBuilder(layout);
        builder.append("Dataset name", _txtDataSetName);
		return builder.getPanel();
	}

	private void initSettingsComponent(ISettings settings)
	{
		VisSettings vSettings = (VisSettings) settings;
		String dataSetName = vSettings.getDataSetName();
		_txtDataSetName.setText(dataSetName == null
				? ""
				: dataSetName);
	}

}
