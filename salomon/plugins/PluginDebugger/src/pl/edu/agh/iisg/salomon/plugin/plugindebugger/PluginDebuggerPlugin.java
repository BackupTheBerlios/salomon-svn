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

package pl.edu.agh.iisg.salomon.plugin.plugindebugger;

import java.io.ByteArrayOutputStream;

import org.apache.log4j.Logger;

import pl.edu.agh.iisg.salomon.plugin.plugindebugger.result.DebuggerResult;
import pl.edu.agh.iisg.salomon.plugin.plugindebugger.result.DebuggerResultComponent;
import pl.edu.agh.iisg.salomon.plugin.plugindebugger.settings.DebuggerSettings;
import pl.edu.agh.iisg.salomon.plugin.plugindebugger.settings.DebuggerSettingsComponent;

import salomon.util.scripting.JavaRunner;

import salomon.platform.IDataEngine;
import salomon.platform.IEnvironment;

import salomon.plugin.IPlugin;
import salomon.plugin.IResult;
import salomon.plugin.IResultComponent;
import salomon.plugin.ISettingComponent;
import salomon.plugin.ISettings;

public final class PluginDebuggerPlugin implements IPlugin
{

    public IResult doJob(IDataEngine engine, IEnvironment environment,
            ISettings settings)
    {
        JavaRunner javaRunner = new JavaRunner();
        javaRunner.defineVariable(DATA_ENGINE_VARIABLE, engine);
        javaRunner.defineVariable(ENVIRONMENT_VARIABLE, environment);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        String script = ((DebuggerSettings) settings).getScript();

        LOGGER.debug("Script: " + script);
        javaRunner.run(script, outputStream);

        IResult result = new DebuggerResult(outputStream.toString());
        LOGGER.debug("Output: " + outputStream.toString());

        return result;
    }

    public IResultComponent getResultComponent()
    {
        return new DebuggerResultComponent();
    }

    public ISettingComponent getSettingComponent()
    {
        return new DebuggerSettingsComponent();
    }

    private static final String DATA_ENGINE_VARIABLE = "dataEngine";

    private static final String ENVIRONMENT_VARIABLE = "environment";

    private static final Logger LOGGER = Logger.getLogger(PluginDebuggerPlugin.class);
}
