/*
 * Copyright (C) 2005 Salomon Team
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

package salomon.engine.controller.gui.viewer;

import salomon.engine.database.DBManager;
import salomon.engine.task.TaskInfo;

public class TaskViewer extends ObjectViewer
{
	private static final long serialVersionUID = 1L;

	public TaskViewer(DBManager dbManager)
	{
		super(dbManager);
	}

	@Override
	public void initList()
	{
		_select.addTable(TaskInfo.TABLE_NAME);
		addColumn("ProjectId", "project_id");
		addColumn("PluginId", "plugin_id");
		addColumn("TaskId", "task_id");
		addColumn("Name", "task_name");
		addColumn("Info", "task_info");
		addColumn("PluginSettings", "plugin_settings");
		addColumn("PluginResult", "plugin_result");
		addColumn("StartTime", "start_time");
		addColumn("StopTime", "stop_time");
		addColumn("Status", "status");
		addColumn("LmDate", "lm_date");

		addFilteredField("Task id", "task_id");
		addFilteredField("ProjectId", "project_id");
		addFilteredField("PluginId", "plugin_id");
		addFilteredField("Name", "task_name");
		addFilteredField("Status", "status");
	}
}
