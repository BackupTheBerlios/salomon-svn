/*
 * Copyright (C) 2006 PluginDebugger Team
 *
 * This file is part of PluginDebugger.
 *
 * PluginDebugger is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * PluginDebugger is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with PluginDebugger; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * 
 */

package pl.edu.agh.iisg.salomon.plugin.plugindebugger.settings;

import java.awt.Component;

import salomon.util.gui.editor.JavaEditorComponent;

import salomon.platform.IDataEngine;

import salomon.plugin.ISettingComponent;
import salomon.plugin.ISettings;

public final class DebuggerSettingsComponent implements ISettingComponent
{
    private JavaEditorComponent _component;

    public DebuggerSettingsComponent()
    {
        _component = new JavaEditorComponent();
    }

    public Component getComponent(ISettings settings, IDataEngine dataEngine)
    {
        _component.setText(((DebuggerSettings) settings).getScript());
        _component.requestFocus();

        return _component;
    }

    public ISettings getDefaultSettings()
    {
        return new DebuggerSettings();
    }

    public ISettings getSettings()
    {
        DebuggerSettings settings = new DebuggerSettings(_component.getText());

        return settings;
    }

}
