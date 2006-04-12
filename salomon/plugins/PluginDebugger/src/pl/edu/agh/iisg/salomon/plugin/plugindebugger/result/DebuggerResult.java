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

package pl.edu.agh.iisg.salomon.plugin.plugindebugger.result;

import salomon.util.serialization.SimpleString;
import salomon.util.serialization.SimpleStruct;

import salomon.platform.serialization.IObject;
import salomon.platform.serialization.IString;

import salomon.plugin.IResult;
/**
 * FIXME
 *
 */
public final class DebuggerResult extends SimpleStruct implements IResult
{
    public static final String OUTPUT_FIELD = "output";

    private boolean _isSuccessful = false;

    public DebuggerResult()
    {
        // do nothing
    }
    
    public DebuggerResult(String ouput)
    {
        setField(OUTPUT_FIELD, new SimpleString(ouput));
    }
    

    public boolean isSuccessful()
    {
        return _isSuccessful;
    }

    /**
     * @param isSuccessful the isSuccessful to set
     */
    public void setSuccessful(boolean isSuccessful)
    {
        _isSuccessful = isSuccessful;
    }

    public void init(IObject object)
    {
        throw new UnsupportedOperationException("Method DebuggerResult.init() not implemented yet!");
    }
    
    public String getOutput()
    {
        IString result = (IString) getField(OUTPUT_FIELD);
        
        return (result == null ? "" : result.getValue());
    }
}
