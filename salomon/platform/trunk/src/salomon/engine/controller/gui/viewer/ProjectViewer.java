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

import salomon.engine.controller.gui.viewer.spread.AbstractSearchSpread;
import salomon.engine.database.DBManager;
import salomon.engine.project.ProjectInfo;

/**
 * 
 */
public final class ProjectViewer extends AbstractSearchSpread {

	public ProjectViewer(DBManager dbManager) {
		super(dbManager);
	}

	@Override
	public void initColumns() {
		_select.addTable(ProjectInfo.VIEW_NAME);
	}

	@Override
	public void initFilters() {
		addFilteredField("Id", "project_id");
		addFilteredField("Name", "project_name");
		addFilteredField("Info", "project_info");
	}
}
