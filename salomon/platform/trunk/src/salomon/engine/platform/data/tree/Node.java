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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import salomon.platform.data.tree.INode;

/**
 * 
 * @author Mateusz Nowakowski
 *
 */
public class Node implements INode
{

	private Type type;
	private String value;
	private String parentEdge;
	private INode parent;
	private INode[] children;
	
	
	
	/**
	 * @param parent
	 * @param edge
	 * @param type
	 * @param value
	 */
	public Node(INode parent, String edge, Type type, String value){
		this.type = type;
		this.value = value;
		this.children = new INode[0];
		if (this.parent != null) {
			this.parentEdge = edge;
			parent.addChild(this);
		}
	}

	public void setParent(INode parent) {
		this.parent = parent;
		parent.addChild(this);
	}
	
	
	public void addChild(INode child){
		List<INode> childs = Arrays.asList(children);
		childs.add(child);
		child.setParent(this);
		this.children = childs.toArray(new INode[childs.size()]);
	}
	
	public void addChildren(INode[] ch){
		List<INode> childs = Arrays.asList(children);
		childs.addAll(Arrays.asList(ch));
		for(INode child : ch){
			child.setParent(this);
		}
		this.children = childs.toArray(new INode[childs.size()]);
	}

	public void setChildren(INode[] children) {
		this.children = children;
		for(INode child : children){
			child.setParent(this);
		}
	}
	
	
	/* (non-Javadoc)
	 * @see salomon.platform.data.tree.INode#getLeafs()
	 */
	public INode[] getLeafs() {
		if (this.isLeaf()) return new INode[]{this};
		List<INode> leafs = new ArrayList<INode>();
		for (INode child : children) leafs.addAll(Arrays.asList(child.getLeafs()));
		return leafs.toArray(new INode[leafs.size()]);
	}
	
	/* (non-Javadoc)
	 * @see salomon.platform.data.tree.INode#getRoot()
	 */
	public INode getRoot() {
		if (this.isRoot()) return this;
		else return getParent().getRoot();
	}
	
	/* (non-Javadoc)
	 * @see salomon.platform.data.tree.INode#isLeaf()
	 */
	public boolean isLeaf() {
		return ((children == null)||(children.length == 0));
	}
	/* (non-Javadoc)
	 * @see salomon.platform.data.tree.INode#isRoot()
	 */
	public boolean isRoot() {
		return (parent == null);
	}
	/**
	 * @return Returns the children.
	 */
	public INode[] getChildren() {
		return children;
	}

	/**
	 * @return Returns the parent.
	 */
	public INode getParent() {
		return parent;
	}
	/**
	 * @return Returns the parentEdge.
	 */
	public String getParentEdge() {
		return parentEdge;
	}
	/**
	 * @param parentEdge The parentEdge to set.
	 */
	public void setParentEdge(String parentEdge) {
		this.parentEdge = parentEdge;
	}
	/**
	 * @return Returns the type.
	 */
	public Type getType() {
		return type;
	}
	/**
	 * @param type The type to set.
	 */
	public void setType(Type type) {
		this.type = type;
	}
	/**
	 * @return Returns the value.
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value The value to set.
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	


}
