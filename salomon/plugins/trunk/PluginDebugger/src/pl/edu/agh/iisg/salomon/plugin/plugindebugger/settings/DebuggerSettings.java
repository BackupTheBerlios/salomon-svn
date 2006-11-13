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

import salomon.util.serialization.SimpleString;
import salomon.util.serialization.SimpleStruct;

import salomon.platform.serialization.IObject;
import salomon.platform.serialization.IString;
import salomon.platform.serialization.IStruct;

import salomon.plugin.ISettings;

public final class DebuggerSettings extends SimpleStruct implements ISettings
{
    public static final String SCRIPT_FIELD = "script";

    public DebuggerSettings()
    {
        // do nothing
    }
    
    public DebuggerSettings(String script)
    {
        setField(SCRIPT_FIELD, new SimpleString(script));
    }
    
    
    public void init(IObject o)
    {
        IStruct struct = (IStruct) o;
        for (String field : struct.getFieldNames()) {
            this.setField(field, struct.getField(field));
        }
    }
    
    public String getScript()
    {
        IString result = (IString) getField(SCRIPT_FIELD);
        
        return (result == null ? "" : result.getValue());
    }
}
