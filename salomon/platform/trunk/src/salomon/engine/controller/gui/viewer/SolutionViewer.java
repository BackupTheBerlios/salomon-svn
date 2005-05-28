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
import salomon.engine.solution.SolutionInfo;

public class SolutionViewer extends ObjectViewer
{
	private static final long serialVersionUID = 1L;

	public SolutionViewer(DBManager dbManager)
	{
		super(dbManager);
	}

	@Override
	public void initList()
	{
		_select.addTable(SolutionInfo.TABLE_NAME);
		addColumn("solutionId", "SOLUTION_ID");
		addColumn("name", "SOLUTION_NAME");
		addColumn("info", "SOLUTION_INFO");
		addColumn("", "LM_DATE");

		addFilteredField("id", "SOLUTION_ID");
		addFilteredField("name", "SOLUTION_NAME");
		addFilteredField("info", "SOLUTION_INFO");
		addFilteredField("Date", "LM_DATE");
	}
}
