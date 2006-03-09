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
import salomon.platform.exception.PlatformException;

/**
 * Implementacja Ÿród³a danych oparty na tablicy w zewnetrznej bazie danych
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
	private int firstRowIndex = 1;
	private int lastRowIndex = -1;
	private Date createDate;
	
	
	/**
	 * Konstruktor ustawiaj¹cy wszystkie pola Ÿród³a danych 
	 * 
	 */
	public DataSource(Date date, String[] columns, String column, int firstRowIndex, int lastRowIndex, int id, String info, String name, ShortSolutionInfo solution) throws PlatformException{
		if ((firstRowIndex < 1 || lastRowIndex < 1) || (firstRowIndex > lastRowIndex)) throw new PlatformException("Indexy wierszy data sourca musza byc liczbami dodatnimi oraz index pierwszego wiersza nie moze byc wiekszy od indexu wiersza ostatniego");
		createDate = date;
		decioningColumns = columns;
		decisionedColumn = column;
		this.id = id;
		this.info = info;
		this.name = name;
		this.solution = solution;
		this.firstRowIndex = firstRowIndex;
		this.lastRowIndex = lastRowIndex;
	}
	
	
	
	
	/**
	 * Konstruktor wymagaj¹cy informacji o aktualnym rozwi¹zaniu
	 * 
	 * @param solution aktualne rozwi¹zanie
	 */
	public DataSource(ShortSolutionInfo solution) {
		this.solution = solution;
	}




	/**
	 * @return Returns the tableName.
	 */
	/* (non-Javadoc)
	 * @see salomon.platform.data.tree.IDataSource#getTableName()
	 */
	public String getTableName() {
		return tableName;
	}
	/**
	 * @param tableName The tableName to set.
	 */
	/* (non-Javadoc)
	 * @see salomon.platform.data.tree.IDataSource#setTableName(java.lang.String)
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	/**
	 * @param id The id to set.
	 */
	/**
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return Returns the createDate.
	 */
	/* (non-Javadoc)
	 * @see salomon.platform.data.tree.IDataSource#getCreateDate()
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
	/* (non-Javadoc)
	 * @see salomon.platform.data.tree.IDataSource#getDecioningColumns()
	 */
	public String[] getDecioningColumns() {
		return decioningColumns;
	}
	/**
	 * @param decioningColumns The decioningColumns to set.
	 */
	/* (non-Javadoc)
	 * @see salomon.platform.data.tree.IDataSource#setDecioningColumns(java.lang.String[])
	 */
	public void setDecioningColumns(String[] decioningColumns) {
		this.decioningColumns = decioningColumns;
	}
	/**
	 * @return Returns the decisionedColumn.
	 */
	/* (non-Javadoc)
	 * @see salomon.platform.data.tree.IDataSource#getDecisionedColumn()
	 */
	public String getDecisionedColumn() {
		return decisionedColumn;
	}
	/**
	 * @param decisionedColumn The decisionedColumn to set.
	 */
	/* (non-Javadoc)
	 * @see salomon.platform.data.tree.IDataSource#setDecisionedColumn(java.lang.String)
	 */
	public void setDecisionedColumn(String decisionedColumn) {
		this.decisionedColumn = decisionedColumn;
	}
	/**
	 * @return Returns the id.
	 */
	/* (non-Javadoc)
	 * @see salomon.platform.data.tree.IDataSource#getId()
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return Returns the info.
	 */
	/* (non-Javadoc)
	 * @see salomon.platform.data.tree.IDataSource#getInfo()
	 */
	public String getInfo() {
		return info;
	}
	/**
	 * @param info The info to set.
	 */
	/* (non-Javadoc)
	 * @see salomon.platform.data.tree.IDataSource#setInfo(java.lang.String)
	 */
	public void setInfo(String info) {
		this.info = info;
	}
	/**
	 * @return Returns the name.
	 */
	/* (non-Javadoc)
	 * @see salomon.platform.data.tree.IDataSource#getName()
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name The name to set.
	 */
	/* (non-Javadoc)
	 * @see salomon.platform.data.tree.IDataSource#setName(java.lang.String)
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return Returns the solution.
	 */
	/* (non-Javadoc)
	 * @see salomon.platform.data.tree.IDataSource#getSolution()
	 */
	public ShortSolutionInfo getSolution() {
		return solution;
	}
	/**
	 * @param solution The solution to set.
	 */
	/**
	 * @param solution
	 */
	public void setSolution(ShortSolutionInfo solution) {
		this.solution = solution;
	}




	/**
	 * @return Returns the firstRowIndex.
	 */
	/* (non-Javadoc)
	 * @see salomon.platform.data.tree.IDataSource#getFirstRowIndex()
	 */
	public int getFirstRowIndex() {
		return firstRowIndex;
	}




	/**
	 * @param firstRowIndex The firstRowIndex to set.
	 */
	/* (non-Javadoc)
	 * @see salomon.platform.data.tree.IDataSource#setFirstRowIndex(int)
	 */
	public void setFirstRowIndex(int firstRowIndex) {
		this.firstRowIndex = firstRowIndex;
	}




	/**
	 * @return Returns the lastRowIndex.
	 */
	/* (non-Javadoc)
	 * @see salomon.platform.data.tree.IDataSource#getLastRowIndex()
	 */
	public int getLastRowIndex() {
		return lastRowIndex;
	}




	/**
	 * @param lastRowIndex The lastRowIndex to set.
	 */
	/* (non-Javadoc)
	 * @see salomon.platform.data.tree.IDataSource#setLastRowIndex(int)
	 */
	public void setLastRowIndex(int lastRowIndex) {
		this.lastRowIndex = lastRowIndex;
	}
	


}
