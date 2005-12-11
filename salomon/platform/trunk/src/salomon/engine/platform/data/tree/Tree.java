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

package salomon.engine.platform.data.tree;

import java.util.Date;

import salomon.platform.data.tree.IDataSource;
import salomon.platform.data.tree.INode;
import salomon.platform.data.tree.ITree;

/**
 * 
 * @author Mateusz Nowakowski
 *
 */
public class Tree implements ITree
{

	private int id;
	private String name;
	private String info;
	private IDataSource dataSource;
	private INode root;
	private Date createDate;
	
	
	
	
	
	@Override
	public String toString() {
		return name;
	}


	/**
	 * Empty constructor 
	 */
	public Tree() {
	}
	
			
	/**
	 * @param source
	 */
	public Tree(IDataSource source) {
		dataSource = source;
	}

	

	/**
	 * @param source
	 * @param info
	 * @param name
	 * @param root
	 */
	public Tree(IDataSource source, String info, String name, INode root) {
		dataSource = source;
		this.info = info;
		this.name = name;
		this.root = root;
	}

	
	

	public Tree(int id, Date date, IDataSource source, String info, String name, INode root) {
		createDate = date;
		dataSource = source;
		this.id = id;
		this.info = info;
		this.name = name;
		this.root = root;
	}


	/**
	 * @return Returns the dataSource.
	 */
	public IDataSource getDataSource() {
		return dataSource;
	}
	/**
	 * @param dataSource The dataSource to set.
	 */
	public void setDataSource(IDataSource dataSource) {
		this.dataSource = dataSource;
	}
	/**
	 * @return Returns the id.
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(int id) {
		this.id = id;
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
	 * @return Returns the root.
	 */
	public INode getRoot() {
		return root;
	}
	/**
	 * @param root The root to set.
	 */
	public void setRoot(INode root) {
		this.root = root;
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

	


}
