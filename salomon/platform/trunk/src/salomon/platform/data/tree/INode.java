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

/**
 * Element drzewa. Komentarze nie potrzebne, chyba ze ktos nie wie co to drzewo...
 * @author Mateusz Nowakowski
 *
 */
public interface INode
{
	enum Type {
		COLUMN, VALUE
	};

	Type getType();
	void setType(Type type);
	
	String getValue();
	void setValue(String value);
	
	String getParentEdge();
	void setParentEdge(String parentEdge);
	
	
	boolean isRoot();
	INode getRoot();
	INode getParent();
	void setParent(INode parent);
	

	INode[] getChildren();
	void setChildren(INode[] children);
	void addChild(INode child);
	void addChildren(INode[] child);
	INode[] getLeafs();
	boolean isLeaf();
	

}
