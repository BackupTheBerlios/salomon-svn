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

import java.awt.Component;

import salomon.util.gui.editor.SyntaxEditTextArea;

import salomon.platform.IDataEngine;

import salomon.plugin.IResult;
import salomon.plugin.IResultComponent;

public final class DebuggerResultComponent implements IResultComponent
{
    
    public Component getComponent(IResult result, IDataEngine dataEngine)
    {
        SyntaxEditTextArea syntaxEditTextArea = new SyntaxEditTextArea();
        syntaxEditTextArea.setEditable(false);
        
        syntaxEditTextArea.setText(result.resultToString());
        
        return syntaxEditTextArea;
    }

    public IResult getDefaultResult()
    {
        return new DebuggerResult();
    }

}
