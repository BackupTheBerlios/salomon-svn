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

package salomon.platform.data.tree;

import java.util.Date;

import salomon.engine.solution.ShortSolutionInfo;


/**
 * 
 * @author Mateusz Nowakowski
 *
 */
public interface IDataSource
{
	int getId();

	String getName();

	String getInfo();

	ShortSolutionInfo getSolution();

	String getTableName();
	
	String getDecisionedColumn();

	String[] getDecioningColumns();
	
	int getFirstRowIndex();
	
	int getLastRowIndex();

	Date getCreateDate();
	
	
	void setName(String name);

	void setInfo(String info);

	void setTableName(String tableName);
	
	void setDecisionedColumn(String decisionedColumn);

	void setDecioningColumns(String[] decisioningColumns);

	void setFirstRowIndex(int index);
	
	void setLastRowIndex(int index);
	
}
