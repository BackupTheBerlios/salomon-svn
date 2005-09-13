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

import salomon.engine.solution.ShortSolutionInfo;
import salomon.platform.data.tree.IDataSource;

/**
 * 
 * @author Mateusz Nowakowski
 *
 */
public class DataSource implements IDataSource
{
	private int id;
	private String name;
	private String info;
	private ShortSolutionInfo solution;
	private String tableName;
	private String decisionedColumn;
	private String[] decioningColumns;
	private Date createDate;
	
	
	/**
	 * @param date
	 * @param columns
	 * @param column
	 * @param id
	 * @param info
	 * @param name
	 * @param solution
	 */
	public DataSource(Date date, String[] columns, String column, int id, String info, String name, ShortSolutionInfo solution) {
		createDate = date;
		decioningColumns = columns;
		decisionedColumn = column;
		this.id = id;
		this.info = info;
		this.name = name;
		this.solution = solution;
	}
	
	
	
	
	/**
	 * @param solution
	 */
	public DataSource(ShortSolutionInfo solution) {
		this.solution = solution;
	}




	/**
	 * @return Returns the tableName.
	 */
	public String getTableName() {
		return tableName;
	}
	/**
	 * @param tableName The tableName to set.
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return Returns the createDate.
	 */
	public Date getCreateDate() {
		return createDate;
	}
	/**
	 * @param createDate The createDate to set.
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * @return Returns the decioningColumns.
	 */
	public String[] getDecioningColumns() {
		return decioningColumns;
	}
	/**
	 * @param decioningColumns The decioningColumns to set.
	 */
	public void setDecioningColumns(String[] decioningColumns) {
		this.decioningColumns = decioningColumns;
	}
	/**
	 * @return Returns the decisionedColumn.
	 */
	public String getDecisionedColumn() {
		return decisionedColumn;
	}
	/**
	 * @param decisionedColumn The decisionedColumn to set.
	 */
	public void setDecisionedColumn(String decisionedColumn) {
		this.decisionedColumn = decisionedColumn;
	}
	/**
	 * @return Returns the id.
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return Returns the info.
	 */
	public String getInfo() {
		return info;
	}
	/**
	 * @param info The info to set.
	 */
	public void setInfo(String info) {
		this.info = info;
	}
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return Returns the solution.
	 */
	public ShortSolutionInfo getSolution() {
		return solution;
	}
	/**
	 * @param solution The solution to set.
	 */
	public void setSolution(ShortSolutionInfo solution) {
		this.solution = solution;
	}
	


}
