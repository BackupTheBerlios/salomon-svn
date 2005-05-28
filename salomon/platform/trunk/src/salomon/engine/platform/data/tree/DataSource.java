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

package salomon.engine.platform.data.tree;

import java.util.Date;

import salomon.engine.solution.ISolution;

import salomon.platform.data.tree.IDataSource;

public class DataSource implements IDataSource
{

	public int getId()
	{
		throw new UnsupportedOperationException(
				"Method getId() not implemented yet!");
	}

	public String getName()
	{
		throw new UnsupportedOperationException(
				"Method getName() not implemented yet!");
	}

	public String getInfo()
	{
		throw new UnsupportedOperationException(
				"Method getInfo() not implemented yet!");
	}

	public ISolution getSolution()
	{
		throw new UnsupportedOperationException(
				"Method getSolution() not implemented yet!");
	}

	public String getDecisionedColumn()
	{
		throw new UnsupportedOperationException(
				"Method getDecisionedColumn() not implemented yet!");
	}

	public String[] getDecioningColumns()
	{
		throw new UnsupportedOperationException(
				"Method getDecioningColumns() not implemented yet!");
	}

	public Date getCreateDate()
	{
		throw new UnsupportedOperationException(
				"Method getCreateDate() not implemented yet!");
	}

}
