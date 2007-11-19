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
 * Represents task that may be executed. It is an implementation of ITask
 * interface.
 */
public final class Task implements ITask
{
    private static final Logger LOGGER = Logger.getLogger(Task.class);

    private ILocalPlugin _plugin;

    private IResult _result;

    private ISettings _settings;

    private int _taskNr;

    private String _taskName;

    private Long _taskId;

    // FIXME: remove it;
    private TaskInfo _taskInfo;

    protected Task()
    {

    }

    protected Task(DBManager manager)
    {
        _taskInfo = new TaskInfo(manager);
        _taskInfo.setStatus(TaskInfo.ACTIVE);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Task) {
            Task task = (Task) obj;
            return _taskId.equals(task.getTaskId());
        } else {
            return false;
        }
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
     * Returns the taskId.
     * @return The taskId
     */
    public final Long getTaskId()
    {
        return _taskId;
    }

    @Override
    public int hashCode()
    {
        return _taskId.hashCode();
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
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        XMLSerializer.serialize((SimpleStruct) _result, bos);
        _taskInfo.setResult(bos.toString());
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
     * Set the value of taskId field.
     * @param taskId The taskId to set
     */
    public final void setTaskId(Long taskId)
    {
        _taskId = taskId;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return " [" + _plugin + "," + _settings + "," + _result + "]";
    }

    /**
     * Returns the taskNr.
     * @return The taskNr
     */
    public final int getTaskNr()
    {
        return _taskNr;
    }

    /**
     * Set the value of taskNr field.
     * @param taskNr The taskNr to set
     */
    public final void setTaskNr(int taskNr)
    {
        _taskNr = taskNr;
    }

    /**
     * Returns the name.
     * @return The name
     */
    public final String getTaskName()
    {
        return _taskName;
    }

    /**
     * Set the value of name field.
     * @param name The name to set
     */
    public final void setTaskName(String name)
    {
        _taskName = name;
    }
} // end Task
