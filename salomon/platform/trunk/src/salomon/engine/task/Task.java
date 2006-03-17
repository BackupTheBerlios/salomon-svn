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

import java.io.ByteArrayOutputStream;

import org.apache.log4j.Logger;

import salomon.engine.database.DBManager;
import salomon.engine.platform.serialization.XMLSerializer;
import salomon.engine.plugin.ILocalPlugin;
import salomon.platform.exception.PlatformException;
import salomon.plugin.IResult;
import salomon.plugin.ISettings;
import salomon.util.serialization.SimpleStruct;

/**
 * Represents task which may be executed. It is an implementation of ITask
 * interface.
 */
public final class Task implements ITask
{

    /**
     * 
     * @uml.property name="_plugin"
     * @uml.associationEnd multiplicity="(0 1)"
     */
    private ILocalPlugin _plugin;

    /**
     * 
     * @uml.property name="_result"
     * @uml.associationEnd multiplicity="(0 1)"
     */
    private IResult _result;

    /**
     * 
     * @uml.property name="_settings"
     * @uml.associationEnd multiplicity="(0 1)"
     */
    private ISettings _settings;

    /**
     * 
     * @uml.property name="_taskInfo"
     * @uml.associationEnd multiplicity="(0 1)"
     */
    private TaskInfo _taskInfo;

    protected Task(DBManager manager)
    {
        _taskInfo = new TaskInfo(manager);
        _taskInfo.setStatus(TaskInfo.ACTIVE);
    }

    public TaskInfo getInfo() throws PlatformException
    {
        return _taskInfo;
    }

    /**
     * @return Returns the _plugin.
     */
    public ILocalPlugin getPlugin() throws PlatformException
    {
        return _plugin;
    }

    /**
     * @return Returns the _result.
     */
    public IResult getResult() throws PlatformException
    {
        IResult result = _result;
        if (_result == null) {
            result = _plugin.getResultComponent().getDefaultResult();
        }
        return result;
    }

    /**
     * @return Returns the _settings.
     */
    public ISettings getSettings() throws PlatformException
    {
        return _settings;
    }

    /**
     * @param plugin The plugin to set.
     */
    public void setPlugin(ILocalPlugin plugin) throws PlatformException
    {
        _plugin = plugin;
        _taskInfo.setPluginID(plugin.getInfo().getId());
    }

    /**
     * @param result The result to set.
     * @pre result != null
     */
    public void setResult(IResult result) throws PlatformException
    {
        _result = result;
        _taskInfo.setResult(_result.resultToString());
        if (_result.isSuccessful()) {
            _taskInfo.setStatus(TaskInfo.FINISHED);
        } else {
            _taskInfo.setStatus(TaskInfo.ERROR);
        }
    }

    /**
     * @param settings The settings to set.
     * @pre settings != null
     */
    public void setSettings(ISettings settings) throws PlatformException
    {
        _settings = settings;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        XMLSerializer.serialize((SimpleStruct) _settings, bos);
        _taskInfo.setSettings(bos.toString());
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return _taskInfo.getName() + " [" + _plugin + "," + _settings + ","
                + _result + "]";
    }

    private static final Logger LOGGER = Logger.getLogger(Task.class);
} // end Task
